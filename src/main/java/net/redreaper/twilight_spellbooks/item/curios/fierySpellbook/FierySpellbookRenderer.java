package net.redreaper.twilight_spellbooks.item.curios.fierySpellbook;

import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import mod.azure.azurelib.common.render.layer.AzAutoGlowingLayer;
import net.minecraft.resources.ResourceLocation;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;

public class FierySpellbookRenderer extends AzItemRenderer {
    public static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            TwilightSpellbooks.MOD_ID,
            "geo/item/fiery_spell_book.geo.json"
    );

    public static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            TwilightSpellbooks.MOD_ID,
            "textures/item/spell_book_models/fiery_spell_book.png"
    );

    public FierySpellbookRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                            .addRenderLayer(new AzAutoGlowingLayer<>())
                        .build()
        );
    }
}
