package net.redreaper.twilight_spellbooks.events;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.spells.twilight_bolt.TwilightBoltRenderer;
import net.redreaper.twilight_spellbooks.init.ModEntities;

@EventBusSubscriber(modid = TwilightSpellbooks.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    @SubscribeEvent
    public static void registerRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.TWILIGHT_BOLT.get(), (context) -> new TwilightBoltRenderer(context, 0.75f));
    }
}
