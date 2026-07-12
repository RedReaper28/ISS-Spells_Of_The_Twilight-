package net.redreaper.twilight_spellbooks.events;

import io.redspace.ironsspellbooks.api.util.Utils;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.living.advanced_druids.AdvancedDruidEntity;
import net.redreaper.twilight_spellbooks.entity.living.summon.SummonedWinterWolf;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import twilightforest.entity.monster.WinterWolf;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = TwilightSpellbooks.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonSetup {

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ADVANCED_DRUID.get(), AdvancedDruidEntity.createAttributes().build());

        event.put(ModEntities.SUMMONED_WINTER_WOLF.get(), WinterWolf.registerAttributes().build());
    }

    @SubscribeEvent
    public static void spawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.ADVANCED_DRUID.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, serverLevelAccessor, spawnType, blockPos, random) -> Utils.checkMonsterSpawnRules(serverLevelAccessor, spawnType, blockPos, random), RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
