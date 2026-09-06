package net.redreaper.twilight_spellbooks.entity.spells.hydra_morter_shot;

import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.effect.ImmolateEffect;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.redreaper.twilight_spellbooks.effect.HydraFireEffect;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import net.redreaper.twilight_spellbooks.particle.ModParticleHelper;

import java.util.Optional;

public class HydraPoisonField extends AoeEntity {
    private DamageSource damageSource;

    public HydraPoisonField(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public HydraPoisonField(Level level) {
        this(ModEntities.HYDRA_FIRE_FIELD.get(), level);
    }

    @Override
    public void applyEffect(LivingEntity target) {
        if (damageSource == null) {
            damageSource = new DamageSource(DamageSources.getHolderFromResource(target, ISSDamageTypes.FIRE_FIELD), this, getOwner());
        }
        if (!DamageSources.isFriendlyFireBetween(this.getOwner(), target)) {
            DamageSources.ignoreNextKnockback(target);
        }
        HydraFireEffect.addFireStack(target);
    }

    @Override
    public float getParticleCount() {
        return 1.5f * getRadius();
    }

    @Override
    protected float particleYOffset() {
        return .25f;
    }

    @Override
    protected float getParticleSpeedModifier() {
        return 1.4f;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.of(ModParticleHelper.HYDRA_FIRE_PARTICLE);
    }
}