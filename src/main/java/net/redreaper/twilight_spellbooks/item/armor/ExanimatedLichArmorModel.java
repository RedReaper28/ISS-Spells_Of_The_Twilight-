package net.redreaper.twilight_spellbooks.item.armor;

import net.minecraft.resources.ResourceLocation;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class ExanimatedLichArmorModel extends DefaultedItemGeoModel<ExanimatedLichArmorItem> {
    public ExanimatedLichArmorModel() {
        super(ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", ""));
    }

    public ResourceLocation getModelResource(ExanimatedLichArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "geo/armor/exanimated_lich_armor.geo.json");
    }

    public ResourceLocation getTextureResource(ExanimatedLichArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "textures/armor/exanimated_lich_armor.png");
    }

    public ResourceLocation getAnimationResource(ExanimatedLichArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "animations/wizard_armor_animation.json");
    }
}
