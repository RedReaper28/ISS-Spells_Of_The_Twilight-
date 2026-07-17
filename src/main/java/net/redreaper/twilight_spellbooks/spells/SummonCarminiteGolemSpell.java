package net.redreaper.twilight_spellbooks.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellSummonEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.capabilities.magic.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.living.summon.SummonedCarminiteGolem;

import javax.annotation.Nullable;
import java.util.List;

public class SummonCarminiteGolemSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "summon_carminite_golem");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.summon_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.LIGHTNING_RESOURCE)
            .setMaxLevel(4)
            .setCooldownSeconds(80)
            .build();

    public SummonCarminiteGolemSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 30;
        this.baseManaCost = 75;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 2;
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (SummonManager.recastFinishedHelper(serverPlayer, recastInstance, recastResult, castDataSerializable)) {
            super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
        }
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new SummonedEntitiesCastData();
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        PlayerRecasts recasts = playerMagicData.getPlayerRecasts();

        if (!recasts.hasRecastForSpell(this))
        {
            SummonedEntitiesCastData summonedEntitiesCastData = new SummonedEntitiesCastData();
            int summonTimer = 20 * 60 * 10;

            for (int i = 0; i < spellLevel; i++)
            {
                Vec3 vec = entity.getEyePosition();

                double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
                double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

                spawnKoboleton(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer, spellLevel, summonedEntitiesCastData);
            }

            RecastInstance recastInstance = new RecastInstance(this.getSpellId(), spellLevel, getRecastCount(spellLevel, entity), summonTimer, castSource, summonedEntitiesCastData);
            recasts.addRecast(recastInstance, playerMagicData);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnKoboleton(double x, double y, double z, LivingEntity caster, Level level, int summonTimer, int spellLevel, SummonedEntitiesCastData castData)
    {
        SummonedCarminiteGolem koboleton = new SummonedCarminiteGolem(level, caster);

        var event = NeoForge.EVENT_BUS.post(new SpellSummonEvent<>(caster, koboleton, this.spellId, spellLevel)).getCreature();

        koboleton.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(koboleton.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null);

        koboleton.moveTo(x, y, z);

        level.addFreshEntity(event);

        SummonManager.initSummon(caster, event, summonTimer, castData);
    }
}
