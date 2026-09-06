package net.redreaper.twilight_spellbooks.entity.living.summon;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.MinotaurModel;
import twilightforest.entity.monster.Minotaur;

public class MinotaurRenderer extends HumanoidMobRenderer<Minotaur, MinotaurModel> {

    private static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("minotaur.png");

    public MinotaurRenderer(EntityRendererProvider.Context context) {
        super(context, new MinotaurModel(context.bakeLayer(TFModelLayers.MINOTAUR)), 0.625F);
    }

    @Override
    public ResourceLocation getTextureLocation(Minotaur deathTome) {
        return TEXTURE;
    }

}
