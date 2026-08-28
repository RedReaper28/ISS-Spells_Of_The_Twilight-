package net.redreaper.twilight_spellbooks.entity.spells.examinated_trident;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import net.redreaper.twilight_spellbooks.particle.ExanimatedExplosionParticlePacket;
import net.redreaper.twilight_spellbooks.particle.ModParticleHelper;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Optional;

public class ExanimatedTrident extends AbstractMagicProjectile implements GeoEntity {

    private final AnimatableInstanceCache cache;
    private final RawAnimation idle;

    public ExanimatedTrident(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.cache = GeckoLibUtil.createInstanceCache(this);
        this.idle = RawAnimation.begin().thenLoop("idle");
        this.setNoGravity(true);    }

    public ExanimatedTrident(Level levelIn, LivingEntity shooter) {
        this(ModEntities.EXANIMATED_TRIDENT.get(), levelIn);
        setOwner(shooter);
    }


    public int shakeTime;
    protected boolean inGround;

    private boolean shouldFall() {
        return this.inGround && this.level().noCollision((new AABB(this.position(), this.position())).inflate(0.06D));
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = this.position().subtract(getDeltaMovement());
        level().addParticle(ModParticleHelper.EXANIMATED_FIRE, vec3.x, vec3.y, vec3.z, 0, 0, 0);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(level(), ModParticleHelper.EXANIMATED_FIRE, x, y, z, 5, .1, .1, .1, 0.5, true);
        MagicManager.spawnParticles(level(), ParticleHelper.ENDER_SPARKS, x, y, z, 75, .1, .1, .1, .5, false);
    }

    @Override
    public float getSpeed() {
        return 1.5f;
    }

    @Override
    public Optional<Holder<SoundEvent>> getImpactSound() {
        return Optional.empty();
    }

    @Override
    public void tick() {

        if (this.shakeTime > 0) {
            --this.shakeTime;
        }
        if (!inGround) {
            super.tick();
        } else {
            if (tickCount > EXPIRE_TIME) {
                discard();
                return;
            }
            if (shouldFall()) {
                inGround = false;
                this.setDeltaMovement(getDeltaMovement().normalize().scale(0.05f));

            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
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
                    DamageSources.applyDamage(entity, damage, ModSpells.EXANIMATED_TRIDENTS.get().getDamageSource(this, getOwner()));
                }
            }
        }
        PacketDistributor.sendToPlayersTrackingEntity(this, new ExanimatedExplosionParticlePacket(hitResult.getLocation().subtract(getDeltaMovement().scale(0.5)), getExplosionRadius()));
        playSound(SoundEvents.GENERIC_EXPLODE.value(), 4.0F, (1.0F + (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.2F) * 0.7F);
        pierceOrDiscard();
    }



    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.inGround = true;
        this.shakeTime = 7;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("inGround", this.inGround);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.inGround = tag.getBoolean("inGround");
    }

    @Override
    public double getTick(Object object) {
        return tickCount;
    }

    private PlayState predicate(AnimationState event) {
        event.getController().setAnimation(this.idle);
        return PlayState.CONTINUE;
    }

    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController(this, "controller", 0, this::predicate));
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
