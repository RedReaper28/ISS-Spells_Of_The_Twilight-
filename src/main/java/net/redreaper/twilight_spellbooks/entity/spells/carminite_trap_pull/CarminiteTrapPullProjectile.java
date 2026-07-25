package net.redreaper.twilight_spellbooks.entity.spells.carminite_trap_pull;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractConeProjectile;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import net.redreaper.twilight_spellbooks.particle.CarminiteTrapPullParticlePacket;
import twilightforest.init.TFParticleType;

public class CarminiteTrapPullProjectile extends AbstractConeProjectile {
    public CarminiteTrapPullProjectile(EntityType<? extends AbstractConeProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public CarminiteTrapPullProjectile(Level level, LivingEntity entity) {
        super(ModEntities.CARMINITE_PULL_PROJECTILE.get(), level, entity);
    }

    @Override
    public void spawnParticles() {
        var owner = getOwner();
        if (!level().isClientSide || owner == null) {
            return;
        }
        Vec3 rotation = owner.getLookAngle().normalize();
        var pos = owner.position().add(rotation.scale(1.5));

        double x = pos.x;
        double y = pos.y + owner.getEyeHeight() * .9f;
        double z = pos.z;

        for (int i = 0; i < 10; i++) {
            double speed = random.nextDouble() * .7 + 15;
            double offset = .125;
            double ox = Math.random() * 2 * offset - offset;
            double oy = Math.random() * 2 * offset - offset;
            double oz = Math.random() * 2 * offset - offset;

            double angularness = .8;
            Vec3 randomVec = new Vec3(Math.random() * 2 * angularness - angularness, Math.random() * 2 * angularness - angularness, Math.random() * 2 * angularness - angularness).normalize();
            Vec3 result = (rotation.scale(3).add(randomVec)).normalize().scale(speed);
            level().addParticle(TFParticleType.GHAST_TRAP.get(), x + ox, y + oy, z + oz, result.x, result.y, result.z);
        }
    }


    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = getOwner();
        var resultEntity = entityHitResult.getEntity();
        if (entity != null && resultEntity instanceof LivingEntity target) {
            var knockback = new Vec3(entity.getX() - target.getX(), entity.getY() - target.getY(), entity.getZ() - target.getZ()).normalize().scale(-strength);
            if (!DamageSources.isFriendlyFireBetween(entity, target)) {
                target.setDeltaMovement(target.getDeltaMovement().add(knockback));
                target.hurtMarked = true;
            }
        }
    }

    public float strength;

}

