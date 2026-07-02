package net.redreaper.twilight_spellbooks.init;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.entity.mobs.necromancer.NecromancerEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.living.advanced_druids.AdvancedDruidEntity;
import net.redreaper.twilight_spellbooks.entity.spells.twilight_bolt.TwilightBoltProjectile;

import static net.minecraft.core.registries.Registries.ENTITY_TYPE;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ENTITY_TYPE, TwilightSpellbooks.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<AdvancedDruidEntity>> ADVANCED_DRUID =
            ENTITIES.register("advanced_druid", () -> EntityType.Builder.of(AdvancedDruidEntity::new, MobCategory.MONSTER)
                    .sized(.6f, 1.8f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "advanced_druid").toString()));


    public static final DeferredHolder<EntityType<?>, EntityType<TwilightBoltProjectile>> TWILIGHT_BOLT =
            ENTITIES.register("twilight_bolt", () -> EntityType.Builder.<TwilightBoltProjectile>of(TwilightBoltProjectile::new, MobCategory.MISC)
                    .sized(.5f, .5f)
                    .clientTrackingRange(64)
                    .build(ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "twilight_bolt").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
