package net.redreaper.twilight_spellbooks.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.living.advanced_loyal_zombie.AdvancedLoyalZombieEntity;
import net.redreaper.twilight_spellbooks.entity.living.lich_soul.LichSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.advanced_druids.AdvancedDruidEntity;
import net.redreaper.twilight_spellbooks.entity.living.ominous_lich.OminousLichEntity;
import net.redreaper.twilight_spellbooks.entity.living.snow_queen_soul.SnowQueenSoulEntity;
import net.redreaper.twilight_spellbooks.entity.living.summon.SummonedCarminiteGolem;
import net.redreaper.twilight_spellbooks.entity.living.summon.SummonedDeathTome;
import net.redreaper.twilight_spellbooks.entity.living.summon.SummonedWinterWolf;
import net.redreaper.twilight_spellbooks.entity.spells.avalanche.IceChunkProjectile;
import net.redreaper.twilight_spellbooks.entity.spells.druid_bolt.ExtendedNatureBolt;
import net.redreaper.twilight_spellbooks.entity.spells.examinated_trident.ExanimatedTrident;
import net.redreaper.twilight_spellbooks.entity.spells.exanimate_fireball.ExanimatedFireballEntity;
import net.redreaper.twilight_spellbooks.entity.spells.exanimated_ray.ExanimatedRayVisualEntity;
import net.redreaper.twilight_spellbooks.entity.spells.ice_bomb.ExtendedIceBomb;
import net.redreaper.twilight_spellbooks.entity.spells.mosquito_swarm.MosquitoSwarmProjectile;
import net.redreaper.twilight_spellbooks.entity.spells.twilight_bolt.TwilightBoltProjectile;
import net.redreaper.twilight_spellbooks.entity.spells.carminite_trap_pull.CarminiteTrapPullProjectile;

import static net.minecraft.core.registries.Registries.ENTITY_TYPE;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ENTITY_TYPE, TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<OminousLichEntity>> OMINOUS_LICH =
            ENTITIES.register("ominous_lich", () -> EntityType.Builder.<OminousLichEntity>of(OminousLichEntity::new, MobCategory.MONSTER)
                    .sized(.6f, 1.8f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "ominous_lich").toString())
            );

    public static final DeferredHolder<EntityType<?>, EntityType<AdvancedDruidEntity>> ADVANCED_DRUID =
            ENTITIES.register("advanced_druid", () -> EntityType.Builder.of(AdvancedDruidEntity::new, MobCategory.MONSTER)
                    .sized(.6f, 1.8f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "advanced_druid").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<AdvancedLoyalZombieEntity>> ADVANCED_LOYAL_ZOMBIE =
            ENTITIES.register("advanced_loyal_zombie", () -> EntityType.Builder.<AdvancedLoyalZombieEntity>of(AdvancedLoyalZombieEntity::new, MobCategory.MONSTER)
                    .sized(.6f, 1.8f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "advanced_loyal_zombie").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SummonedWinterWolf>> SUMMONED_WINTER_WOLF =
            ENTITIES.register("summoned_winter_wolf", () -> EntityType.Builder.<SummonedWinterWolf>of(SummonedWinterWolf::new, MobCategory.MONSTER).
                    sized(1.4F, 1.9F).eyeHeight(1.45F)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "summoned_winter_wolf").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SummonedDeathTome>> SUMMONED_DEATH_TOME =
            ENTITIES.register("summoned_death_tome", () -> EntityType.Builder.<SummonedDeathTome>of(SummonedDeathTome::new, MobCategory.MONSTER).
                    sized(1.4F, 1.9F).eyeHeight(1.45F)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "summoned_death_tome").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SummonedCarminiteGolem>> SUMMONED_CARMINITE_GOLEM =
            ENTITIES.register("summoned_carminite_golem", () -> EntityType.Builder.<SummonedCarminiteGolem>of(SummonedCarminiteGolem::new, MobCategory.MONSTER).
                    sized(1.4F, 2.9F)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "summoned_carminite_golem").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<LichSoulEntity>> LICH_SOUL =
            ENTITIES.register("lich_soul", () -> EntityType.Builder.<LichSoulEntity>of(LichSoulEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "lich_soul").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<SnowQueenSoulEntity>> SNOW_QUEEN_SOUL =
            ENTITIES.register("snow_queen_soul", () -> EntityType.Builder.<SnowQueenSoulEntity>of(SnowQueenSoulEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "snow_queen_soul").toString()));


    public static final DeferredHolder<EntityType<?>, EntityType<TwilightBoltProjectile>> TWILIGHT_BOLT =
            ENTITIES.register("twilight_bolt", () -> EntityType.Builder.<TwilightBoltProjectile>of(TwilightBoltProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "twilight_bolt").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ExtendedNatureBolt>> EXTENDED_NATURE_BOLT =
            ENTITIES.register("extended_nature_bolt", () -> EntityType.Builder.<ExtendedNatureBolt>of(ExtendedNatureBolt::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "extended_nature_bolt").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ExtendedIceBomb>> EXTENDED_THROWN_ICE =
            ENTITIES.register("extended_ice_bomb", () -> EntityType.Builder.<ExtendedIceBomb>of(ExtendedIceBomb::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "extended_ice_bomb").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<CarminiteTrapPullProjectile>> CARMINITE_PULL_PROJECTILE =
            ENTITIES.register("carminite_pull", () -> EntityType.Builder.<CarminiteTrapPullProjectile>of(CarminiteTrapPullProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "carminite_pull").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<MosquitoSwarmProjectile>> MOSQUITO_SWARM =
            ENTITIES.register("mosquito_swarm", () -> EntityType.Builder.<MosquitoSwarmProjectile>of(MosquitoSwarmProjectile::new, MobCategory.MISC)
                    .sized(.9f, .9f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "mosquito_swarm").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ExanimatedFireballEntity>> EXANIMATED_FIREBALL =
            ENTITIES.register("exanimated_fireball", () -> EntityType.Builder.<ExanimatedFireballEntity>of(ExanimatedFireballEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(4)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "exanimated_fireball").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ExanimatedTrident>> EXANIMATED_TRIDENT =
            ENTITIES.register("exanimated_trident", () -> EntityType.Builder.<ExanimatedTrident>of(ExanimatedTrident::new, MobCategory.MISC)
                    .sized(1.25f, 1.25f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "exanimated_trident").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<ExanimatedRayVisualEntity>> EXANIMATED_RAY =
            ENTITIES.register("exanimated_ray", () -> EntityType.Builder.<ExanimatedRayVisualEntity>of(ExanimatedRayVisualEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "exanimated_ray").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<IceChunkProjectile>> ICE_CHUNK =
            ENTITIES.register("ice_chunk", () -> EntityType.Builder.<IceChunkProjectile>of(IceChunkProjectile::new, MobCategory.MISC)
                    .sized(0.75F, 0.75F)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "ice_chunk").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
