package net.redreaper.twilight_spellbooks.events;

import io.redspace.ironsspellbooks.registries.EntityRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.living.advanced_druids.AdvancedDruidRenderer;
import net.redreaper.twilight_spellbooks.entity.living.summon.SummonedCarminiteGolemRenderer;
import net.redreaper.twilight_spellbooks.entity.spells.twilight_bolt.TwilightBoltRenderer;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import twilightforest.client.renderer.entity.ThrownIceRenderer;
import twilightforest.client.renderer.entity.WinterWolfRenderer;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = TwilightSpellbooks.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TWILIGHT_BOLT.get(), (context) -> new TwilightBoltRenderer(context, 0.75f));

        event.registerEntityRenderer(ModEntities.ADVANCED_DRUID.get(), AdvancedDruidRenderer::new);

        event.registerEntityRenderer(ModEntities.SUMMONED_WINTER_WOLF.get(), WinterWolfRenderer::new);
        event.registerEntityRenderer(ModEntities.SUMMONED_CARMINITE_GOLEM.get(), SummonedCarminiteGolemRenderer::new);

        event.registerEntityRenderer(ModEntities.EXTENDED_THROWN_ICE.get(), ThrownIceRenderer::new);

        event.registerEntityRenderer(ModEntities.EXTENDED_NATURE_BOLT.get(), ThrownItemRenderer::new);

    }
}
