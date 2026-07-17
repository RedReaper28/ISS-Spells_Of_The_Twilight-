package net.redreaper.twilight_spellbooks.entity.spells.ice_bomb;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import twilightforest.enchantment.ApplyFrostedEffect;
import twilightforest.entity.boss.AlphaYeti;
import twilightforest.entity.monster.Yeti;
import twilightforest.entity.projectile.IceBomb;
import twilightforest.init.TFDamageTypes;
import twilightforest.init.TFParticleType;

public class ExtendedIceBomb extends IceBomb {
    protected float damage;
    float aoeDamage;
    private int zoneTimer = 101;
    private boolean hasHit;
    public ExtendedIceBomb(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    public ExtendedIceBomb(EntityType type, double x, double y, double z, Level worldIn) {
        super(type, worldIn);
        this.setPos(x, y, z);
    }

    public ExtendedIceBomb(Level worldIn, LivingEntity shooter) {
        this(ModEntities.EXTENDED_THROWN_ICE.get(), shooter.getX(), shooter.getEyeY(), shooter.getZ(), worldIn);
        this.setOwner(shooter);
    }

    public float getSpeed() {
        return .65f;
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);
        if (!this.level().isClientSide)
        {
            Entity entity = hitResult.getEntity();
            LivingEntity owner = (LivingEntity) this.getOwner();
            if (!entity.getType().is(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
                entity.hurt(TFDamageTypes.getIndirectEntityDamageSource(this.level(), TFDamageTypes.FROZEN, this, this.getOwner(), new EntityType[0]), getDamage());
                ApplyFrostedEffect.doChillAuraEffect((LivingEntity) entity, 5 * 20, 0, true);
            }
        }
    }

    protected void onHitBlock(BlockHitResult result) {
        this.setDeltaMovement((double)0.0F, (double)0.0F, (double)0.0F);
        this.hasHit = true;
    }

    public void tick() {
        super.tick();
        if (this.hasHit) {
            this.getDeltaMovement().multiply(0.1, 0.1, 0.1);
            --this.zoneTimer;
            this.makeIceZone();
            if (!this.level().isClientSide() && this.zoneTimer <= 0) {
                this.level().levelEvent(2001, new BlockPos(this.blockPosition()), Block.getId(Blocks.ICE.defaultBlockState()));
                this.discard();
            }
        } else {
            this.makeTrail((ParticleOptions)TFParticleType.SNOW_GUARDIAN.get(), this.getOwner() instanceof AlphaYeti ? 2 : 5);
        }

    }

    private void makeIceZone() {
        if (this.level().isClientSide()) {
            for(int i = 0; i < 16; ++i) {
                double dx = this.getX() + (double)((this.random.nextFloat() - this.random.nextFloat()) * 3.5F);
                double dy = this.getY() + (double)((this.random.nextFloat() - this.random.nextFloat()) * 3.5F);
                double dz = this.getZ() + (double)((this.random.nextFloat() - this.random.nextFloat()) * 3.5F);
                this.level().addParticle((ParticleOptions) TFParticleType.SNOW_GUARDIAN.get(), dx, dy, dz, (double)0.0F, (double)0.0F, (double)0.0F);
            }
        } else {
            if (this.zoneTimer % 20 == 0) {
                this.hitNearbyEntities();
            }
        }

    }

    public void makeTrail(ParticleOptions particle, double r, double g, double b, int amount) {
        for(int i = 0; i < amount; ++i) {
            double dx = this.getX() + (double)0.5F * (this.random.nextDouble() - this.random.nextDouble());
            double dy = this.getY() + (double)0.5F * (this.random.nextDouble() - this.random.nextDouble()) + (double)0.5F;
            double dz = this.getZ() + (double)0.5F * (this.random.nextDouble() - this.random.nextDouble());
            this.level().addParticle(particle, dx, dy, dz, r, g, b);
        }

    }

    private void hitNearbyEntities() {
        for(LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate((double)3.0F, (double)2.0F, (double)3.0F))) {
            if (entity != this.getOwner()) {
                if (entity instanceof Yeti) {
                    BlockPos pos = BlockPos.containing(entity.xOld, entity.yOld, entity.zOld);
                    this.level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                    this.level().setBlockAndUpdate(pos.above(), Blocks.ICE.defaultBlockState());
                    entity.discard();
                } else {
                    entity.hurt(TFDamageTypes.getIndirectEntityDamageSource(this.level(), TFDamageTypes.FROZEN, this, this.getOwner(), new EntityType[0]), getAoeDamage());
                    ApplyFrostedEffect.doChillAuraEffect((LivingEntity) entity, 5 * 20, 0, true);
                }
            }
        }

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putFloat("Damage", this.getDamage());
        tag.putFloat("AoeDamage", this.getAoeDamage());
        tag.putInt("Age", tickCount);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.damage = tag.getFloat("Damage");
        this.aoeDamage = tag.getFloat("AoeDamage");

    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public float getDamage() {
        return damage;
    }

    public void setAoeDamage(float damage) {
        this.aoeDamage = damage;
    }

    public float getAoeDamage() {
        return aoeDamage;
    }

    public void shoot(Vec3 rotation) {
        setDeltaMovement(rotation.scale(getSpeed()));
    }
}
