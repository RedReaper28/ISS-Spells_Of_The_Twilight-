package net.redreaper.twilight_spellbooks.entity.living.ominous_lich;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.BossbarManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.mobs.IAnimatedAttacker;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.abstract_spell_casting_mob.AbstractSpellCastingMob;
import io.redspace.ironsspellbooks.entity.mobs.dead_king_boss.DeadKingBoss;
import io.redspace.ironsspellbooks.entity.mobs.goals.PatrolNearLocationGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.SpellBarrageGoal;
import io.redspace.ironsspellbooks.entity.mobs.goals.WizardAttackGoal;
import io.redspace.ironsspellbooks.entity.mobs.keeper.KeeperEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.cultist.CultistEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.fire_boss.ExtendedServerBossEvent;
import io.redspace.ironsspellbooks.entity.mobs.wizards.fire_boss.FireBossEntity;
import io.redspace.ironsspellbooks.entity.mobs.wizards.fire_boss.NotIdioticNavigation;
import io.redspace.ironsspellbooks.entity.mobs.wizards.priest.PriestEntity;
import io.redspace.ironsspellbooks.network.EntityEventPacket;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.NBT;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.acetheeldritchking.aces_spell_utils.entity.mobs.GenericBossEntity;
import net.acetheeldritchking.aces_spell_utils.entity.mobs.goals.WizardSpellComboGoal;
import net.acetheeldritchking.aces_spell_utils.registries.ASAttributeRegistry;
import net.acetheeldritchking.aces_spell_utils.utils.boss_music.BossMusicManager;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.living.lich_soul.LichSoulEntity;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModItems;
import net.redreaper.twilight_spellbooks.init.ModSpells;
import software.bernie.geckolib.animation.RawAnimation;
import twilightforest.entity.ai.goal.AlwaysWatchTargetGoal;
import twilightforest.init.TFItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class OminousLichEntity extends GenericBossEntity implements IAnimatedAttacker {
    public OminousLichEntity(Level level) {
        this(ModEntities.OMINOUS_LICH.get(), level);
        setPersistenceRequired();
    }

    @Nullable
    private Vec3 spawnPos;

    // Constructor for the boss
    public OminousLichEntity(EntityType<? extends AbstractSpellCastingMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setPersistenceRequired();
        xpReward = 60;
        this.lookControl = createLookControl();
        this.moveControl = createMoveControl();
        createBossEvent();
    }

    // Boss Bar
    private static final BossbarManager.BossbarSprite BOSSBAR_SPRITE = new BossbarManager.BossbarSprite(TwilightSpellbooks.id("boss_bars/lich_boss_bar"), 192, 18, 3, -1);

    // These are used for doing boss bars, setting up the phase serializer for NBT, and stopping and starting music
    private ExtendedServerBossEvent bossEvent;
    private final static EntityDataAccessor<Integer> PHASE = SynchedEntityData.defineId(OminousLichEntity.class, EntityDataSerializers.INT);
    public static final byte CLIENT_STOP_TRACKING = 0;
    public static final byte CLIENT_START_TRACKING = 1;


    // Boss music
    public static SoundEvent bossMusic = SoundRegistry.DEAD_KING_SECOND_PHASE_MELODY_ALT.get();

    // Loot
    SimpleContainer deathLoot = null;

    // Music
    @Override
    public boolean hasCustomMusic() {
        return true;
    }

    @Override
    public SoundEvent getBossMusic() {
        return bossMusic;
    }

    // Helps handle the starting and stopping of boss music
    @Override
    public void handleClientEvent(byte eventId) {
        switch (eventId) {
            case CLIENT_STOP_TRACKING -> {
                BossbarManager.stopTracking(this.uuid);
                BossMusicManager.stop(this);
            }
            case CLIENT_START_TRACKING -> {
                BossbarManager.startTracking(this.uuid, BOSSBAR_SPRITE);
                BossMusicManager.createOrResumeInstance(this);
            }
            //case START_MUSIC -> BossMusicManager.createOrResumeInstance(this);
            //case STOP_MUSIC -> BossMusicManager.stop(this);
        }
    }

    @Override
    public void startSeenByPlayer(ServerPlayer serverPlayer) {
        super.startSeenByPlayer(serverPlayer);
        this.bossEvent.addPlayer(serverPlayer);
        PacketDistributor.sendToPlayer(serverPlayer, new EntityEventPacket<OminousLichEntity>(this, CLIENT_START_TRACKING));
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer serverPlayer) {
        super.stopSeenByPlayer(serverPlayer);
        this.bossEvent.removePlayer(serverPlayer);
        PacketDistributor.sendToPlayer(serverPlayer, new EntityEventPacket<OminousLichEntity>(this, CLIENT_STOP_TRACKING));
    }

    // For updating the boss health
    @Override
    public void aiStep() {
        super.aiStep();
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    // These are for movement and looking controls for smoother movement (from Iron himself)
    protected LookControl createLookControl() {
        return new LookControl(this) {
            @Override
            protected float rotateTowards(float from, float to, float maxDelta) {
                return super.rotateTowards(from, to, maxDelta * 2.5F);
            }

            @Override
            protected boolean resetXRotOnTick() {
                return getTarget() == null;
            }
        };
    }

    protected MoveControl createMoveControl() {
        return new MoveControl(this) {
            @Override
            protected float rotlerp(float sourceAngle, float targetAngle, float maximumChange) {
                double x = this.wantedX - this.mob.getX();
                double z = this.wantedZ - this.mob.getZ();

                if (x * x + z * z < 0.5F) {
                    return sourceAngle;
                } else {
                    return super.rotlerp(sourceAngle, targetAngle, maximumChange * 0.25F);
                }
            }
        };
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new NotIdioticNavigation(this, level);
    }

    // Register the basic goals for the boss
    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

        firstPhaseGoals();
        //this.goalSelector.addGoal(10, new WizardRecoverGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new AlwaysWatchTargetGoal(this));

        // They HATE these guys
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, KeeperEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PriestEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, CultistEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, FireBossEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, DeadKingBoss.class, true));
    }

    // First phase spells
    private void firstPhaseGoals() {
        this.goalSelector.getAvailableGoals().forEach(WrappedGoal::stop);
        this.goalSelector.removeAllGoals((x) -> true);

        this.goalSelector.addGoal(1, new FloatGoal(this));
        // Magic Spells
        this.goalSelector.addGoal(2, new SpellBarrageGoal(this, SpellRegistry.ELDRITCH_BLAST_SPELL.get(), 1, 3, 80, 150, 1));
        this.goalSelector.addGoal(3, new WizardAttackGoal(this, 1.25f, 50, 75)
                .setSpells(
                        // Attack
                        List.of(
                                SpellRegistry.SONIC_BOOM_SPELL.get(),
                                SpellRegistry.FIRE_ARROW_SPELL.get(),
                                SpellRegistry.BLOOD_SLASH_SPELL.get(),
                                SpellRegistry.FLAMING_BARRAGE_SPELL.get(),
                                SpellRegistry.BLAZE_STORM_SPELL.get(),
                                SpellRegistry.RAY_OF_SIPHONING_SPELL.get(),
                                SpellRegistry.ACUPUNCTURE_SPELL.get(),
                                ModSpells.TWILIGHT_BOLT.get()
                        ),
                        // Defense
                        List.of(
                                SpellRegistry.COUNTERSPELL_SPELL.get(),
                                SpellRegistry.SHIELD_SPELL.get(),
                                SpellRegistry.GUST_SPELL.get(),
                                SpellRegistry.HEARTSTOP_SPELL.get(),
                                SpellRegistry.FORTIFY_SPELL.get(),
                                SpellRegistry.OAKSKIN_SPELL.get(),
                                SpellRegistry.HEAT_SURGE_SPELL.get()
                        ),
                        // Movement
                        List.of(
                                SpellRegistry.BLOOD_STEP_SPELL.get()
                        ),
                        // Support
                        List.of(
                                ModSpells.SUMMON_LOYAL_ZOMBIE.get(),
                                ModSpells.SUMMON_DEATH_TOME.get(),
                                SpellRegistry.COUNTERSPELL_SPELL.get(),
                                SpellRegistry.SACRIFICE_SPELL.get()
                        )
                ).setSingleUseSpell(SpellRegistry.FIREBALL_SPELL.get(), 70, 100, 3, 5)
                .setSpellQuality(1.0f, 1.0f));
        this.goalSelector.addGoal(5, new PatrolNearLocationGoal(this, 32.0F, 0.9));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }

    // Second phase spells
    private void secondPhaseGoals() {
        this.goalSelector.getAvailableGoals().forEach(WrappedGoal::stop);
        this.goalSelector.removeAllGoals((x) -> true);

        this.goalSelector.addGoal(1, new FloatGoal(this));
        // Magic Spells
        this.goalSelector.addGoal(2, new SpellBarrageGoal(this, SpellRegistry.ELDRITCH_BLAST_SPELL.get(), 1, 3, 50, 80, 3));
        this.goalSelector.addGoal(3, new WizardSpellComboGoal(this,
                List.of(
                        SpellRegistry.COUNTERSPELL_SPELL.get(),
                        SpellRegistry.ROOT_SPELL.get(),
                        SpellRegistry.RAY_OF_SIPHONING_SPELL.get()
                ), 1.3f, 1.3f, 100, 250));
        this.goalSelector.addGoal(3, new WizardSpellComboGoal(this,
                List.of(
                        SpellRegistry.COUNTERSPELL_SPELL.get(),
                        SpellRegistry.SLOW_SPELL.get(),
                        SpellRegistry.FLAMING_STRIKE_SPELL.get()
                ), 1.3f, 1.3f, 100, 250));
        this.goalSelector.addGoal(3, new WizardAttackGoal(this, 1.25f, 35, 50)
                .setSpells(
                        // Attack
                        List.of(
                                SpellRegistry.SONIC_BOOM_SPELL.get(),
                                SpellRegistry.FIRE_ARROW_SPELL.get(),
                                SpellRegistry.BLOOD_SLASH_SPELL.get(),
                                SpellRegistry.FLAMING_BARRAGE_SPELL.get(),
                                SpellRegistry.BLAZE_STORM_SPELL.get(),
                                SpellRegistry.RAY_OF_SIPHONING_SPELL.get(),
                                SpellRegistry.ACUPUNCTURE_SPELL.get(),
                                SpellRegistry.FIREBALL_SPELL.get(),
                                SpellRegistry.WITHER_SKULL_SPELL.get(),
                                ModSpells.TWILIGHT_BOLT.get()
                        ),
                        // Defense
                        List.of(
                                SpellRegistry.COUNTERSPELL_SPELL.get(),
                                SpellRegistry.SHIELD_SPELL.get(),
                                SpellRegistry.GUST_SPELL.get(),
                                SpellRegistry.HEARTSTOP_SPELL.get(),
                                SpellRegistry.FORTIFY_SPELL.get(),
                                SpellRegistry.OAKSKIN_SPELL.get(),
                                SpellRegistry.ABYSSAL_SHROUD_SPELL.get(),
                                SpellRegistry.HEAT_SURGE_SPELL.get()
                        ),
                        // Movement
                        List.of(
                                SpellRegistry.BLOOD_STEP_SPELL.get()
                        ),
                        // Support
                        List.of(
                                ModSpells.SUMMON_LOYAL_ZOMBIE.get(),
                                ModSpells.SUMMON_DEATH_TOME.get(),
                                SpellRegistry.COUNTERSPELL_SPELL.get(),
                                SpellRegistry.SACRIFICE_SPELL.get()
                        )
                ).setSingleUseSpell(ModSpells.SUMMON_LOYAL_ZOMBIE.get(), 100, 100, 8, 9)
                .setSpellQuality(1.2f, 1.2f));
        this.goalSelector.addGoal(5, new PatrolNearLocationGoal(this, 32.0F, 0.9));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }



    @Override
    public void tick() {
        super.tick();

        // These are used for getting health; very handy for doing phases based on health
        float health = this.getHealth();
        float MAX_HEALTH = this.getMaxHealth();

        float halfHealth = MAX_HEALTH/2;
        float almostDead = MAX_HEALTH/4;

        // Once the boss is at half health or less, it will set the boss to its second phase
        // This will increase its spell power attribute, set its second goals
        // And set its health to its half health
        if (isPhase(Phase.FirstPhase))
        {
            if (this.getHealth() <= halfHealth)
            {
                int radius = 15;

                List<LivingEntity> entitiesNearby = level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(radius));

                // Used for displaying the taunt message to all players nearby who are fighting the boss
                for (LivingEntity targets : entitiesNearby)
                {
                    if (targets instanceof ServerPlayer player)
                    {
                        playSound(SoundEvents.WITHER_SPAWN, 1.5F, 1);
                        player.connection.send(new ClientboundSetTitleTextPacket(Component.translatable("display.twilight_spellbooks.phase_2")
                                .withStyle(s -> s.withColor(TextColor.fromRgb(0xC71B8B)))));
                    }
                }

                setPhase(Phase.SecondPhase);

                if (!isDeadOrDying())
                {
                    setHealth(halfHealth);
                }

                secondPhaseGoals();

                this.getAttributes().getInstance(AttributeRegistry.SPELL_POWER).setBaseValue(1.1F);
                this.getAttributes().getInstance(AttributeRegistry.SPELL_RESIST).setBaseValue(1.5F);

                var player = level().getNearestPlayer(this, 16);
            }
        }
        // Second
        else if (isPhase(Phase.SecondPhase))
        {
            if (this.getHealth() <= almostDead)
            {
                //setInvulnerable(true);

                setPhase(Phase.ThirdPhase);

                if (!isDeadOrDying())
                {
                    setHealth(almostDead);
                }

                var player = level().getNearestPlayer(this, 16);
                if (player != null)
                {
                    // Just stare at the nearest player, aura farm this shit
                    lookAt(player, 360, 360);

                    //jumpBackwards(this, player);
                }
            }
        }
    }


    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == this.level().damageSources().drown())
        {
            return false;
        }else {
            return super.hurt(source, amount);
        }

    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }


    @Override
    public void kill() {
        if (this.isDeadOrDying())
        {
            discard();
        }
        else {
            super.kill();
        }
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);

        if (this.isDeadOrDying() && !this.level().isClientSide)
        {
            this.castComplete();
            this.serverTriggerEvent(CLIENT_STOP_TRACKING);
        }
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel level, DamageSource damageSource) {
        // Looking at how ISS does it for Tyros
        this.dropEquipment();
        this.dropExperience(damageSource.getEntity());

        boolean deathByPlayer = this.lastHurtByPlayerTime > 0;

        this.dropCustomDeathLoot(level, damageSource, deathByPlayer);

        ResourceKey<LootTable> lootTable = this.getLootTable();
        LootTable mainLoot = Objects.requireNonNull(this.level().getServer()).reloadableRegistries().getLootTable(lootTable);

        LootParams.Builder builder = new LootParams.Builder(level)
                .withParameter(LootContextParams.THIS_ENTITY, this)
                .withParameter(LootContextParams.ORIGIN, this.position())
                .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                .withOptionalParameter(LootContextParams.ATTACKING_ENTITY, damageSource.getEntity())
                .withOptionalParameter(LootContextParams.DIRECT_ATTACKING_ENTITY, damageSource.getDirectEntity());

        if (deathByPlayer && this.lastHurtByPlayer != null)
        {
            builder = builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer)
                    .withLuck(this.lastHurtByPlayer.getLuck());
        }

        LootParams lootParams = builder.create(LootContextParamSets.ENTITY);
        ObjectArrayList<ItemStack> objectArrayList = new ObjectArrayList<>();
        mainLoot.getRandomItems(lootParams, this.getLootTableSeed(), objectArrayList::add);

        this.deathLoot = new SimpleContainer(objectArrayList.size());
        objectArrayList.forEach(deathLoot::addItem);
    }

    // Used for determining which mobs the boss is allied to
    @Override
    public boolean isAlliedTo(Entity entityIn) {
        return entityIn instanceof IMagicSummon summon && summon.getSummoner() == this;
    }

    @Override
    protected void tickDeath() {
        this.deathTime++;
        if (this.deathTime >= 20 && !this.level().isClientSide() && !this.isRemoved()) {
            this.level().broadcastEntityEvent(this, (byte) 60);
            this.remove(Entity.RemovalReason.KILLED);
            Vec3 spawnPos = getSpawnPos();
            if (spawnPos != null) {
                deathLoot.getItems().forEach(this::spawnAtLocation);
                var soul = new LichSoulEntity(level(), Vec3.ZERO, spawnPos);
                soul.setRespawnPos(spawnPos);
                soul.moveTo(this.getBoundingBox().getCenter());
                level().addFreshEntity(soul);
            }
        }
    }

    // Puts items on the boss like armors and weapons
    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ModItems.TARNISHED_LICH_CROWN.get()));
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(ModItems.LICH_GREATSWORD.get()));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(ModItems.KNIGHTMETAL_STAFF.get()));
        this.setDropChance(EquipmentSlot.HEAD, 0.0F);
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = Utils.random;
        this.populateDefaultEquipmentSlots(randomsource, difficulty);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    // Creates the entity attributes for the boss
    public static AttributeSupplier.Builder createAttributes()
    {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.MAX_HEALTH, 500.0)
                .add(Attributes.ARMOR, 15)
                .add(Attributes.ARMOR_TOUGHNESS, 10)
                .add(Attributes.FOLLOW_RANGE, 80.0)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 4.0)
                .add(Attributes.MOVEMENT_SPEED, .10)
                .add(AttributeRegistry.SPELL_POWER, 1.5)
                .add(AttributeRegistry.SPELL_RESIST, 2)
                .add(AttributeRegistry.MAX_MANA, 1000)
                .add(ASAttributeRegistry.SPELL_RES_PENETRATION, 0.1)
                .add(ASAttributeRegistry.MANA_REND, 0.1)
                .add(ASAttributeRegistry.MANA_STEAL, 0.1)
                ;
    }

    @Override
    public void setPhase(int phase) {
        this.entityData.set(PHASE, phase);
    }

    @Override
    public int getPhase() {
        return this.entityData.get(PHASE);
    }

    @Nullable
    public Vec3 getSpawnPos() {
        return spawnPos;
    }

    public void setSpawnPos(@Nullable Vec3 spawnPos) {
        this.spawnPos = spawnPos;
    }

    // NBT
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.hasCustomName())
        {
            this.bossEvent.setName(this.getDisplayName());
        }

        if (spawnPos != null) {
            pCompound.put("SpawnPos", NBT.writeVec3Pos(spawnPos));
        }
        // Phases
        pCompound.putInt("phase", getPhase());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        // Phases
        setPhase(pCompound.getInt("phase"));
        if (isPhase(Phase.SecondPhase))
        {
            secondPhaseGoals();
        }
        // Loot
        if (deathLoot != null)
        {
            pCompound.put("deathLootItems", deathLoot.createTag(this.registryAccess()));
        }
        // Loot
        if (pCompound.contains("deathLootItems", 9))
        {
            var tag = pCompound.getList("deathLootItems", 10);
            this.deathLoot = new SimpleContainer(tag.size());
            this.deathLoot.fromTag(tag, this.registryAccess());
        }

        // Boss Bar
        if (this.hasCustomName())
        {
            this.bossEvent.setName(this.getDisplayName());
        }

        if (pCompound.contains("SpawnPos", Tag.TAG_COMPOUND)) {
            this.spawnPos = NBT.readVec3(pCompound.getCompound("SpawnPos"));
        } else {
            this.spawnPos = null;
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(PHASE, 0);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.level().isClientSide)
        {
            createBossEvent();
        }
    }

    /***
     * Geckolib anims
     */

    RawAnimation animationToPlay = null;


    protected void createBossEvent()
    {
        this.bossEvent = (ExtendedServerBossEvent) (new ExtendedServerBossEvent(this.getUUID(), this.getDisplayName().copy().withStyle(ChatFormatting.DARK_PURPLE/*, ChatFormatting.BOLD*/), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS)).setCreateWorldFog(false);
    }

    @Override
    public void playAnimation(String animationId) {
        animationToPlay = RawAnimation.begin().thenPlay(animationId);
    }
}

