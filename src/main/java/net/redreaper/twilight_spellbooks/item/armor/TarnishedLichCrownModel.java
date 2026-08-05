package net.redreaper.twilight_spellbooks.item.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import software.bernie.geckolib.model.GeoModel;

public class TarnishedLichCrownModel extends GeoModel<TarnishedLichCrownItem> {

    public TarnishedLichCrownModel() {
        super();
    }

    @Override
    public ResourceLocation getModelResource(TarnishedLichCrownItem object) {
        return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "geo/tarnished_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TarnishedLichCrownItem object) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "textures/armor/tarnished_lich_crown.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TarnishedLichCrownItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
