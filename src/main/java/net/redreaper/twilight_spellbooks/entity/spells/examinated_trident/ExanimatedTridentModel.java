package net.redreaper.twilight_spellbooks.entity.spells.examinated_trident;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ExanimatedTridentModel extends GeoModel<ExanimatedTrident> {
    public ResourceLocation getModelResource(ExanimatedTrident animatable) {
        return ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", "geo/entity/spells/exanimated_trident.geo.json");
    }

    public ResourceLocation getTextureResource(ExanimatedTrident animatable) {
        return ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", "textures/entity/exanimated_trident.png");
    }

    public ResourceLocation getAnimationResource(ExanimatedTrident animatable) {
        return ResourceLocation.fromNamespaceAndPath("twilight_spellbooks", "animations/entity/spells/exanimated_trident.animation.json");
    }
}
