package net.redreaper.twilight_spellbooks;

import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import mod.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.acetheeldritchking.aces_spell_utils.entity.render.items.SheathCurioRenderer;
import net.acetheeldritchking.aces_spell_utils.items.curios.SheathCurioItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.redreaper.twilight_spellbooks.init.*;
import net.redreaper.twilight_spellbooks.item.curios.spellbooks.fierySpellbook.FierySpellbookRenderer;
import net.redreaper.twilight_spellbooks.item.staffs.ultimateScepter.UltimateScepterRenderer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(TwilightSpellbooks.MOD_ID)
public class TwilightSpellbooks {
    public static final String MOD_ID = "twilight_spellbooks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TwilightSpellbooks(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModItems.register(modEventBus);
        ModTabs.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSpells.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModParticles.register(modEventBus);
        ModLootModifiers.register(modEventBus);
        ModSpellSubSchool.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Animation Registry
        AzIdentityRegistry.register(
                ModItems.ULTIMATE_SCEPTER.get()
        );
     }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    @EventBusSubscriber({Dist.CLIENT})
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

            event.enqueueWork(() -> {
                ModItems.getSOTItems().stream().filter(item -> item.get() instanceof SheathCurioItem).forEach((item) -> CuriosRendererRegistry.register(item.get(), SheathCurioRenderer::new));
            });

            event.enqueueWork(() -> {
                CuriosRendererRegistry.register(ModItems.IRONWOOD_SPELLBOOK.get(), SpellBookCurioRenderer::new);
                CuriosRendererRegistry.register(ModItems.KNIGHTMETAL_SPELLBOOK.get(), SpellBookCurioRenderer::new);
                CuriosRendererRegistry.register(ModItems.CARMINITE_SPELL_BOOK.get(), SpellBookCurioRenderer::new);
                CuriosRendererRegistry.register(ModItems.FIERY_SPELL_BOOK.get(), SpellBookCurioRenderer::new);
                CuriosRendererRegistry.register(ModItems.SNOW_QUEEN_SPELL_BOOK.get(), SpellBookCurioRenderer::new);
            });

            AzItemRendererRegistry.register(UltimateScepterRenderer::new, ModItems.ULTIMATE_SCEPTER.get());
            AzItemRendererRegistry.register(FierySpellbookRenderer::new, ModItems.FIERY_SPELL_BOOK.get());

            // Animation Registry
            AzIdentityRegistry.register(
                    ModItems.ULTIMATE_SCEPTER.get()
            );
        }
    }

    public static ResourceLocation id(@NotNull String path)
    {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, path);
    }
}
