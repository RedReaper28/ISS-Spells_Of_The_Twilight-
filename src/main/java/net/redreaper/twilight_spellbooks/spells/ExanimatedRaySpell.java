package net.redreaper.twilight_spellbooks.spells;


import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.RaycastBuilder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.spells.exanimated_ray.ExanimatedRayVisualEntity;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import net.redreaper.twilight_spellbooks.init.ModSpellSubSchool;
import net.redreaper.twilight_spellbooks.particle.ModParticleHelper;

import java.util.List;
import java.util.Optional;

public class ExanimatedRaySpell  extends ExanimatedAbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "exanimated_ray");
    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(ModSpellSubSchool.EXANIMATED_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(25)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.stringTruncation(getDuration(spellLevel, caster), 1)),
                Component.translatable("ui.irons_spellbooks.distance", Utils.stringTruncation(getRange(spellLevel, caster), 1))
        );
    }

    public ExanimatedRaySpell() {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 6;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 25;
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
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.empty();
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.FIRE_ERUPTION_SLAM.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        var hitResult = RaycastBuilder.begin(level, entity)
                .range(getRange(spellLevel, entity))
                .checkForBlocks(true)
                .bbInflation(.15f)
                .build();
        level.addFreshEntity(new ExanimatedRayVisualEntity(level, entity.getEyePosition(), hitResult.getLocation(), entity));
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            Entity target = ((EntityHitResult) hitResult).getEntity();
            int i = getDuration(spellLevel, entity);
            //Set freeze time right here because it scales off of level and power
            DamageSources.applyDamage(target, getDamage(spellLevel, entity), getDamageSource(entity).indirect());
            if (target instanceof LivingEntity livingTarget) {
                (livingTarget).addEffect(new MobEffectInstance(ModMobEffects.OMINOUS_BURN,i,0, false, true, true));

            }
                MagicManager.spawnParticles(level, ParticleHelper.COMET_FOG, hitResult.getLocation().x, target.getY(), hitResult.getLocation().z, 4, 0, 0, 0, .3, true);
        } else if (hitResult.getType() == HitResult.Type.BLOCK) {
            MagicManager.spawnParticles(level, ParticleHelper.COMET_FOG, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 4, 0, 0, 0, .3, true);
        }
        MagicManager.spawnParticles(level, ModParticleHelper.EXANIMATED_FIRE, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z, 50, 0, 0, 0, .3, false);
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public float getRange(int spellLevel, LivingEntity caster) {
        return 4f + getSpellPower(spellLevel, caster) * 1.5f;
    }

    public float getDamage(int spellLevel, LivingEntity caster) {
        if (caster == null) {
            return this.getSpellPower(spellLevel, null) * 2;
        } else {
            double firePower = caster.getAttributeValue(AttributeRegistry.FIRE_SPELL_POWER);
            double bloodPower = caster.getAttributeValue(AttributeRegistry.BLOOD_SPELL_POWER);
            double enderPower = caster.getAttributeValue(AttributeRegistry.ENDER_SPELL_POWER);
            if (firePower == bloodPower && bloodPower == enderPower) {
                return (float)((double)3  * ((double)1.5F * firePower + (double)1.5F * enderPower + (double)1.5F * bloodPower));
            }
            else {
                return (float)((double)3 * (firePower + enderPower + bloodPower));
            }
        }
    }

    public int getDuration(int spellLevel, LivingEntity caster) {
        return (int) (5 + (getSpellPower(spellLevel, caster) * 20));
    }
}
