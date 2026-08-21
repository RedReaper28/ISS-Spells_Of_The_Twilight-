package net.redreaper.twilight_spellbooks.item.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.redreaper.twilight_spellbooks.TwilightSpellbooks;
import software.bernie.geckolib.model.GeoModel;

public class TarnishedQueenCrownModel extends GeoModel<TarnishedQueenCrownItem> {

    public TarnishedQueenCrownModel() {
        super();
    }

    @Override
    public ResourceLocation getModelResource(TarnishedQueenCrownItem object) {
        return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "geo/tarnished_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TarnishedQueenCrownItem object) {
        return ResourceLocation.fromNamespaceAndPath(TwilightSpellbooks.MOD_ID, "textures/armor/tarnished_queen_crown.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TarnishedQueenCrownItem animatable) {
        return ResourceLocation.fromNamespaceAndPath(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
