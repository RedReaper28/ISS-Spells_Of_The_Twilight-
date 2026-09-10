package net.redreaper.twilight_spellbooks.entity.spells.jet_eruption;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractShieldEntity;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.entity.spells.ShieldPart;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModSpells;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FireJetEruption extends AoeEntity {

    private static final EntityDataAccessor<Float> DATA_SIZE = SynchedEntityData.defineId(FireJetEruption.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> DATA_WAIT_TIME = SynchedEntityData.defineId(FireJetEruption.class, EntityDataSerializers.INT);
    public static final int RISE_TIME = 6;
    public static final int REST_TIME = 40;
    public static final int LOWER_TIME = 30;
    private final List<Entity> victims;


    public FireJetEruption(EntityType<? extends AoeEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.victims = new ArrayList<>();
    }

    public FireJetEruption(Level level, LivingEntity owner) {
        this(ModEntities.JET_ERUPTION.get(), level);
        setOwner(owner);
    }

    @Override
    public void applyEffect(LivingEntity target) {
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_SIZE, 1f);
        pBuilder.define(DATA_WAIT_TIME, 10);
    }

    public float getSpikeSize() {
        return this.entityData.get(DATA_SIZE);
    }

    public void setSpikeSize(float f) {
        this.entityData.set(DATA_SIZE, f);
        this.refreshDimensions();
    }

    public int getWaitTime() {
        return this.entityData.get(DATA_WAIT_TIME);
    }

    public void setWaitTime(int i) {
        this.entityData.set(DATA_WAIT_TIME, i);
    }

    @Override
    public void tick() {
        this.refreshDimensions();
        int waitTime = getWaitTime();
        if (tickCount == waitTime) {
            if (!level().isClientSide) {
                float f = getSpikeSize();
                if (!this.isSilent()) {
                    level().playSound(null, this.blockPosition(), SoundRegistry.FIRE_ERUPTION_SLAM.get(), SoundSource.NEUTRAL, 1.25f * getSpikeSize(), Mth.randomBetweenInclusive(Utils.random, 6, 12) * .1f);
                }
                MagicManager.spawnParticles(level(), ParticleHelper.FIRE, getX(), level().clip(new ClipContext(position().add(0, 2, 0), position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty())).getLocation().y() + 0.1, getZ(), (int) (10 * f * f), 0.1 * f, 0.1 * f, 0.1f * f, 0.12 * f, false);
                MagicManager.spawnParticles(level(), ParticleHelper.FIERY_SMOKE, getX(), level().clip(new ClipContext(position().add(0, 2, 0), position(), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty())).getLocation().y() + 0.1, getZ(), (int) (15 * f * f), 0.1 * f, 0.1 * f, 0.1f * f, 0.08 * f, false);
            }
        } else if (tickCount > waitTime && tickCount < waitTime + RISE_TIME) {
            AABB damager = this.getBoundingBox();
            damager.setMaxY(this.getY() + (damager.getYsize() * (1 + 1)));
            for (Entity entity : level().getEntities(this, damager).stream().filter(target -> canHitEntity(target) && !victims.contains(target)).collect(Collectors.toSet())) {
                if (DamageSources.applyDamage(entity, damage, ModSpells.JET_ERUPTION.get().getDamageSource(this, getOwner()))) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, this.getSpikeSize() * 0.3, 0));
                    entity.hurtMarked = true;
                    entity.setTicksFrozen(entity.getTicksFrozen() + (int) (40 * getSpikeSize()));
                }
                victims.add(entity);
                if (entity instanceof ShieldPart || entity instanceof AbstractShieldEntity) {
                    discard();
                    return;
                }
            }
        } else if (tickCount > waitTime + RISE_TIME + REST_TIME + LOWER_TIME) {
            discard();
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("waitTime", this.getWaitTime());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setWaitTime(pCompound.getInt("waitTime"));
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.scalable(this.getSpikeSize() * 0.4f, this.getSpikeSize() * 1.25f * (1));
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public void ambientParticles() {
        return;
    }

    @Override
    public float getParticleCount() {
        return 0;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket pPacket) {
        super.recreateFromPacket(pPacket);
        this.xRotO = this.getXRot();
        this.yRotO = this.getYRot();
    }
}
