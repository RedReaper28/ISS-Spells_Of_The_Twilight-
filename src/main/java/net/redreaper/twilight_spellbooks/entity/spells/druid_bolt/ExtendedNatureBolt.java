package net.redreaper.twilight_spellbooks.entity.spells.druid_bolt;

import io.redspace.ironsspellbooks.damage.DamageSources;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import org.jetbrains.annotations.NotNull;
import twilightforest.entity.projectile.NatureBolt;

public class ExtendedNatureBolt extends NatureBolt {
    protected float damage;

    public ExtendedNatureBolt(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    public ExtendedNatureBolt(EntityType type, double x, double y, double z, Level worldIn) {
        super(type, worldIn);
        this.setPos(x, y, z);
    }

    public ExtendedNatureBolt(Level worldIn, LivingEntity shooter) {
        this(ModEntities.EXTENDED_NATURE_BOLT.get(), shooter.getX(), shooter.getEyeY(), shooter.getZ(), worldIn);
        this.setOwner(shooter);
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        var target = entityHitResult.getEntity();
        if (target instanceof LivingEntity living) {
            DamageSources.applyDamage(target, getDamage(), ModSpells.DRUID_BOLT.get().getDamageSource(this, getOwner()));
            living.addEffect(new MobEffectInstance(MobEffects.POISON, 7 * 20, 1));
        }
    }

    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    public float getSpeed() {
        return 1.75f;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", this.getDamage());
        tag.putInt("Age", tickCount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.damage = tag.getFloat("Damage");
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public void shoot(Vec3 rotation) {
        setDeltaMovement(rotation.scale(getSpeed()));
    }
}
