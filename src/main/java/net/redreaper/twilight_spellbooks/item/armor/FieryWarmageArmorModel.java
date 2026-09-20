package net.redreaper.twilight_spellbooks.item.armor;

import net.minecraft.resources.ResourceLocation;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class FieryWarmageArmorModel extends DefaultedItemGeoModel<FieryWarmageArmorItem> {
    public FieryWarmageArmorModel() {
        super(ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", ""));
    }

    public ResourceLocation getModelResource(FieryWarmageArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "geo/armor/fiery_mage_armor.geo.json");
    }

    public ResourceLocation getTextureResource(FieryWarmageArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "textures/armor/fiery_mage_armor.png");
    }

    public ResourceLocation getAnimationResource(FieryWarmageArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "animations/wizard_armor_animation.json");
    }
}
