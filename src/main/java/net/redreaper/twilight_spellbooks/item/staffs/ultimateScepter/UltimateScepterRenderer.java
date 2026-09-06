package net.redreaper.twilight_spellbooks.item.staffs.ultimateScepter;

import mod.azure.azurelib.common.render.item.AzItemRenderer;
import mod.azure.azurelib.common.render.item.AzItemRendererConfig;
import mod.azure.azurelib.common.render.layer.AzAutoGlowingLayer;
import net.minecraft.resources.ResourceLocation;

public class UltimateScepterRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", "geo/item/staffs/ultimate_scepter.geo.json");
    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", "textures/item/staffs/ultimate_scepter.png");

    public UltimateScepterRenderer() {
        super(AzItemRendererConfig.builder(GEO, TEX).setAnimatorProvider(UltimateScepterAnimator::new).addRenderLayer(new AzAutoGlowingLayer<>()).build());
    }
}
