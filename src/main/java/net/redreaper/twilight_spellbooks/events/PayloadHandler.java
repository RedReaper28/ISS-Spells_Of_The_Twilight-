package net.redreaper.twilight_spellbooks.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.particle.ExanimatedExplosionParticlePacket;
import net.redreaper.twilight_spellbooks.particle.ExanimatedStepParticlePacket;

@EventBusSubscriber
public class PayloadHandler {

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar payloadRegistrar = event.registrar(TwilightSpellbooks.MOD_ID).versioned("1.0.0").optional();

        //PARTICLES
        payloadRegistrar.playToClient(ExanimatedExplosionParticlePacket.TYPE, ExanimatedExplosionParticlePacket.STREAM_CODEC, ExanimatedExplosionParticlePacket::handle);

        payloadRegistrar.playToClient(ExanimatedStepParticlePacket.TYPE, ExanimatedStepParticlePacket.STREAM_CODEC, ExanimatedStepParticlePacket::handle);

    }
}
