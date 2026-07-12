package net.redreaper.twilight_spellbooks.entity.spells.twilight_bolt;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import org.jetbrains.annotations.NotNull;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFParticleType;
import twilightforest.init.TFSounds;

import java.util.Optional;

public class TwilightBoltProjectile extends AbstractMagicProjectile {
    private DamageSource damageSource;

    public TwilightBoltProjectile(EntityType<? extends TwilightBoltProjectile> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    public TwilightBoltProjectile(Level levelIn, LivingEntity shooter) {
        this(ModEntities.TWILIGHT_BOLT.get(), levelIn);
        setOwner(shooter);
    }

    @Override
    public float getSpeed() {
        return 1.75f;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.of(BuiltInRegistries.SOUND_EVENT.wrapAsHolder(TFSounds.TWILIGHT_SCEPTER_HIT.get()));
    }

    @Override
    protected void doImpactSound(Holder<SoundEvent> sound) {
        level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 2, 1.2f + Utils.random.nextFloat() * .2f);
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        discard();
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        var target = entityHitResult.getEntity();
        var source = new DamageSource(level().damageSources().damageTypes.getHolderOrThrow(TFDamageTypes.TWILIGHT_SCEPTER));

        if (damageSource == null) {
            damageSource = new DamageSource(DamageSources.getHolderFromResource(target, TFDamageTypes.TWILIGHT_SCEPTER), this, getOwner());
        }

        target.hurt(TFDamageTypes.getIndirectEntityDamageSource(this.level(), TFDamageTypes.TWILIGHT_SCEPTER, this, this.getOwner(), new EntityType[0]), getDamage());
        consumeEntityImpact(entityHitResult, true);


    }

    @Override
    public void impactParticles(double x, double y, double z) {
        this.level().addParticle((ParticleOptions)TFParticleType.TWILIGHT_ORB.get(), false, this.getX(), this.getY(), this.getZ(), this.random.nextGaussian() * 0.05, this.random.nextDouble() * 0.2, this.random.nextGaussian() * 0.05);
    }

    @Override
    public void trailParticles() {
        if (tickCount < 2) {
            return;
        }
        Vec3 vel = getDeltaMovement();
        if (vel.lengthSqr() < 1.0E-6) {
            return;
        }

        float radius = 0.25f;
        int steps = 4;
        Vec3 forward = vel.normalize();
        Vec3 worldUp = new Vec3(0, 1, 0);
        Vec3 axis1 = forward.cross(worldUp);
        if (axis1.lengthSqr() < 1.0E-6) {
            axis1 = forward.cross(new Vec3(1, 0, 0));
        }
        axis1 = axis1.normalize();
        Vec3 axis2 = forward.cross(axis1);

        double x2 = getX();
        double y2 = getY();
        double z2 = getZ();
        double x1 = x2 - vel.x;
        double y1 = y2 - vel.y;
        double z1 = z2 - vel.z;

        for (int i = 0; i < 5; i++) {
            double dx = this.getX() + 0.25D * (this.random.nextDouble() - this.random.nextDouble());
            double dy = this.getY() + 0.25D * (this.random.nextDouble() - this.random.nextDouble());
            double dz = this.getZ() + 0.25D * (this.random.nextDouble() - this.random.nextDouble());

            float s1 = ((this.random.nextFloat() * 0.5F) + 0.5F) * 0.17F;  // color
            float s2 = ((this.random.nextFloat() * 0.5F) + 0.5F) * 0.80F;  // color
            float s3 = ((this.random.nextFloat() * 0.5F) + 0.5F) * 0.69F;  // color
            Vec3 jitter = Utils.getRandomVec3(0.05f);
            level().addParticle(ColorParticleOption.create(TFParticleType.MAGIC_EFFECT.get(), s1, s2, s3), dx, dy, dz, 0.0D, 0.0D, 0.0D);
        }
    }
}
