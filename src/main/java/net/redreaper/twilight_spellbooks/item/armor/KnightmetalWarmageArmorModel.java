package net.redreaper.twilight_spellbooks.item.armor;

import net.minecraft.resources.ResourceLocation;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import software.bernie.geckolib.model.DefaultedItemGeoModel;

public class KnightmetalWarmageArmorModel extends DefaultedItemGeoModel<KnightmetalWarmageArmorItem> {
    public KnightmetalWarmageArmorModel() {
        super(ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", ""));
    }

    public ResourceLocation getModelResource(KnightmetalWarmageArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "geo/armor/knightmetal_mage_armor.geo.json");
    }

    public ResourceLocation getTextureResource(KnightmetalWarmageArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "textures/armor/knightmetal_mage_armor.png");
    }

    public ResourceLocation getAnimationResource(KnightmetalWarmageArmorItem animatable) {
        return ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "animations/wizard_armor_animation.json");
    }
}
