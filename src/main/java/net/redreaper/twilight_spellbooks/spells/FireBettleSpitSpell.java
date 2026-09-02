package net.redreaper.twilight_spellbooks.spells;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;

import java.util.List;

public class FireBettleSpitSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "fire_bettle_spit");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.twilight_spellbooks.burn_time", Utils.stringTruncation(getFreezeTime(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.distance", Utils.stringTruncation(getRange(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(1)
            .build();

    public FireBettleSpitSpell() {
        this.manaCostPerLevel = 2;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 10;
    }

    @Override
    public CastType getCastType() {
        return CastType.INSTANT;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        Vec3 forward = entity.getForward().normalize();
        var hitResult = Utils.raycastForEntity(level, entity, getRange(spellLevel, entity), true, .15f);
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            int i = getFreezeTime(spellLevel, entity);
            Entity target = ((EntityHitResult) hitResult).getEntity();
            if (target instanceof LivingEntity livingTarget) {
                DamageSources.applyDamage(target, getDamage(spellLevel, entity), getDamageSource(entity).indirect().setFireTicks(getFreezeTime(spellLevel, entity)));
                MagicManager.spawnParticles(level, ParticleHelper.FIERY_SMOKE, hitResult.getLocation().x, target.getY(), hitResult.getLocation().z, 4, 0, 0, 0, .3, true);
            }
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            MagicManager.spawnParticles(level, ParticleHelper.FIERY_SMOKE, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 4, 0, 0, 0, .3, true);
        }
        MagicManager.spawnParticles(level, ParticleHelper.FIERY_SMOKE, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 50, 0, 0, 0, .3, false);

        double distance = entity.position().distanceTo(hitResult.getLocation());
        for (float i = 1; i < distance; i += .5f) {
            Vec3 pos = entity.getEyePosition().add(forward.scale(i));
            MagicManager.spawnParticles(level, ParticleHelper.FIRE, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0, false);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel, caster);
    }

    public float getRange(int spellLevel, LivingEntity caster) {
        return 4f + getSpellPower(spellLevel, caster) * 1.5f;
    }

    private int getFreezeTime(int spellLevel, LivingEntity caster) {
        return (int) (getSpellPower(spellLevel, caster) * 15);
    }

}

