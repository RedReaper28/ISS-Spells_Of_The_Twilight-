package net.redreaper.twilight_spellbooks;

import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.redreaper.twilight_spellbooks.init.ModItems;
import net.redreaper.twilight_spellbooks.item.curios.fierySpellbook.FierySpellbookRenderer;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(value = TwilightSpellbooks.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = TwilightSpellbooks.MOD_ID, value = Dist.CLIENT)
public class TwilightSpellbooksClient {
    public TwilightSpellbooksClient(ModContainer container) {

    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {


        event.enqueueWork(() -> {
            CuriosRendererRegistry.register(ModItems.FIERY_SPELL_BOOK.get(), SpellBookCurioRenderer::new);
            CuriosRendererRegistry.register(ModItems.IRONWOOD_SPELLBOOK.get(), SpellBookCurioRenderer::new);
        });

        AzItemRendererRegistry.register(FierySpellbookRenderer::new, ModItems.FIERY_SPELL_BOOK.get());

    }
}
