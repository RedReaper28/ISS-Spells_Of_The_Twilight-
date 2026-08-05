package net.redreaper.twilight_spellbooks.entity.spells.exanimate_essemce;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.network.particles.FieryExplosionParticlesPacket;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModMobEffects;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ExanimatedFireballEntity extends AbstractMagicProjectile {
    public ExanimatedFireballEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public ExanimatedFireballEntity(Level pLevel, LivingEntity pShooter) {
        this(ModEntities.EXANIMATED_FIREBALL.get(), pLevel);
        this.setOwner(pShooter);
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = getDeltaMovement();
        double d0 = this.getX() - vec3.x;
        double d1 = this.getY() - vec3.y;
        double d2 = this.getZ() - vec3.z;
        var count = Mth.clamp((int) (vec3.lengthSqr() * 2), 1, 4);
        for (int i = 0; i < count; i++) {
            Vec3 random = Utils.getRandomVec3(getBbHeight() * .2f);
            var f = i / ((float) count);
            var x = Mth.lerp(f, d0, this.getX() + vec3.x);
            var y = Mth.lerp(f, d1, this.getY() + vec3.y);
            var z = Mth.lerp(f, d2, this.getZ() + vec3.z);
            this.level().addParticle(ParticleHelper.FIERY_SMOKE, true, x - random.x, y + getBbHeight() * .5f - random.y, z - random.z, 0, 0, 0/*motion.x * .5f, motion.y * .5f, motion.z * .5f*/);
        }
    }

    @Override
    public void impactParticles(double x, double y, double z) {
    }

    @Override
    public float getSpeed() {
        return 1.15f;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.of(SoundEvents.GENERIC_EXPLODE);
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        if (!this.level().isClientSide) {
            impactParticles(xOld, yOld, zOld);
            float explosionRadius = getExplosionRadius();
            var explosionRadiusSqr = explosionRadius * explosionRadius;
            var entities = level().getEntities(this, this.getBoundingBox().inflate(explosionRadius));
            Vec3 losPoint = Utils.raycastForBlock(level(), this.position(), this.position().add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();
            for (Entity entity : entities) {
                double distanceSqr = entity.distanceToSqr(hitResult.getLocation());
                if (distanceSqr < explosionRadiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(level(), losPoint, entity.getBoundingBox().getCenter(), true)) {
                    double p = (1 - distanceSqr / explosionRadiusSqr);
                    float damage = (float) (this.damage * p);
                    DamageSources.applyDamage(entity, damage, ModSpells.EXANIMATED_FIREBALL.get().getDamageSource(this, getOwner()));
                    if (entity instanceof LivingEntity entityTarget) {
                        entityTarget.addEffect(new MobEffectInstance(ModMobEffects.OMINOUS_BURN, 5 * 20, 0, true, true, true));
                    }
                }
            }
            PacketDistributor.sendToPlayersTrackingEntity(this, new FieryExplosionParticlesPacket(hitResult.getLocation().subtract(getDeltaMovement().scale(0.5)), getExplosionRadius()));
            playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.2F) * 0.7F);
            this.discardHelper(hitResult);
        }
    }
}
