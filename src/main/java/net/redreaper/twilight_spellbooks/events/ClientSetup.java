package net.redreaper.twilight_spellbooks.events;

import io.redspace.ironsspellbooks.particle.FireflyParticle;
import io.redspace.ironsspellbooks.registries.EntityRegistry;
import io.redspace.ironsspellbooks.registries.ParticleRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import net.redreaper.twilight_spellbooks.entity.living.advanced_druids.AdvancedDruidRenderer;
import net.redreaper.twilight_spellbooks.entity.living.lich_soul.LichSoulRenderer;
import net.redreaper.twilight_spellbooks.entity.living.snow_queen_soul.SnowQueenSoulRenderer;
import net.redreaper.twilight_spellbooks.entity.living.summon.SummonedCarminiteGolemRenderer;
import net.redreaper.twilight_spellbooks.entity.spells.twilight_bolt.TwilightBoltRenderer;
import net.redreaper.twilight_spellbooks.init.ModEntities;
import net.redreaper.twilight_spellbooks.init.ModParticles;
import net.redreaper.twilight_spellbooks.particle.MosquitoParticle;
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

        event.registerEntityRenderer(ModEntities.CARMINITE_PULL_PROJECTILE.get(), NoopRenderer::new);
        event.registerEntityRenderer(ModEntities.MOSQUITO_SWARM.get(), NoopRenderer::new);

        event.registerEntityRenderer(ModEntities.LICH_SOUL.get(), LichSoulRenderer::new);
        event.registerEntityRenderer(ModEntities.SNOW_QUEEN_SOUL.get(), SnowQueenSoulRenderer::new);

    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.MOSQUITO_PARTICLE.get(), MosquitoParticle.Provider::new);

    }
}
