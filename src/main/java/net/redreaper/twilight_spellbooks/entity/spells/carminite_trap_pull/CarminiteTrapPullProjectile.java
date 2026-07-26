package net.redreaper.twilight_spellbooks.entity.spells.carminite_trap_pull;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractConeProjectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModSpells;
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
            DamageSources.applyDamage(target, damage, ModSpells.CARMINITE_PULL.get().getDamageSource(this, getOwner()));
            if (!DamageSources.isFriendlyFireBetween(entity, target)) {
                target.setDeltaMovement(target.getDeltaMovement().add(knockback));
                target.hurtMarked = true;
            }
        }
    }

    public float strength;

}

