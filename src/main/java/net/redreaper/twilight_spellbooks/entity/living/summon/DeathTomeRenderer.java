package net.redreaper.twilight_spellbooks.entity.living.summon;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.DeathTomeModel;
import twilightforest.entity.monster.DeathTome;

public class DeathTomeRenderer extends MobRenderer<DeathTome, DeathTomeModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/entity/enchanting_table_book.png");

    public DeathTomeRenderer(EntityRendererProvider.Context context) {
        super(context, new DeathTomeModel(context.bakeLayer(TFModelLayers.DEATH_TOME)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(DeathTome deathTome) {
        return TEXTURE;
    }

}