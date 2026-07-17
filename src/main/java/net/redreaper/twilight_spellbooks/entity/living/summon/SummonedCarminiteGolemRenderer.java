package net.redreaper.twilight_spellbooks.entity.living.summon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import twilightforest.TwilightForestMod;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.CarminiteGolemModel;
import twilightforest.client.model.entity.HostileWolfModel;

public class SummonedCarminiteGolemRenderer <T extends SummonedCarminiteGolem, M extends CarminiteGolemModel<T>> extends MobRenderer<T, M> {
    public static final ResourceLocation TEXTURE = TwilightForestMod.getModelTexture("carminitegolem.png");

    public SummonedCarminiteGolemRenderer(EntityRendererProvider.Context context, M model, float shadowSize) {
        super(context, model, shadowSize);
    }

    public SummonedCarminiteGolemRenderer(EntityRendererProvider.Context context) {
        super(context, (M) new CarminiteGolemModel(context.bakeLayer(TFModelLayers.CARMINITE_GOLEM)), 0.5F);

    }

    protected void setupRotations(T entity, PoseStack ms, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
        super.setupRotations(entity, ms, ageInTicks, rotationYaw, partialTicks, scale);
        if (!(entity.walkAnimation.speed() < 0.01F)) {
            float f1 = entity.walkAnimation.position() - entity.walkAnimation.speed() * (1.0F - partialTicks) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            ms.mulPose(Axis.ZP.rotationDegrees(6.5F * f2));
        }

    }

    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}
