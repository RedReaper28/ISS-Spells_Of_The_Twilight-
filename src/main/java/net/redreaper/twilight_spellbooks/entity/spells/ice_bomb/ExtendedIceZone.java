package net.redreaper.twilight_spellbooks.entity.spells.ice_bomb;

import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import twilightforest.enchantment.ApplyFrostedEffect;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFParticleType;

import java.util.Optional;

public class ExtendedIceZone extends AoeEntity {
    private DamageSource damageSource;

    public ExtendedIceZone(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.reapplicationDelay = 1;
    }

    public ExtendedIceZone(Level level) {
        this(ModEntities.EXTENDED_ICE_ZONE.get(), level);
    }

    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            for (int i = 0; i < 16; ++i) {
                double dx = this.getX() + (double) ((this.random.nextFloat() - this.random.nextFloat()) * 3.5F);
                double dy = this.getY() + (double) ((this.random.nextFloat() - this.random.nextFloat()) * 3.5F);
                double dz = this.getZ() + (double) ((this.random.nextFloat() - this.random.nextFloat()) * 3.5F);
                this.level().addParticle(TFParticleType.SNOW_GUARDIAN.get(), dx, dy, dz, (double) 0.0F, (double) 0.0F, (double) 0.0F);
            }
        }
    }

    @Override
    public void applyEffect(LivingEntity target) {
        if (damageSource == null) {
            damageSource = new DamageSource(DamageSources.getHolderFromResource(target, TFDamageTypes.FROZEN), this, getOwner());
        }
        if (!DamageSources.isFriendlyFireBetween(this.getOwner(), target)) {
            DamageSources.ignoreNextKnockback(target);
            if (target.hurt(damageSource, getDamage())) {
                ApplyFrostedEffect.doChillAuraEffect(target, 5 * 20, 0, true);
            }
        }
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
        return Optional.of(ParticleHelper.SNOWFLAKE);
    }
}
