package net.redreaper.twilight_spellbooks.entity.spells.examinated_trident;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import javax.annotation.Nullable;

public class ExanimatedTridentRenderer extends GeoEntityRenderer<ExanimatedTrident> {
    public ExanimatedTridentRenderer(EntityRendererProvider.Context context) {
        super(context, new ExanimatedTridentModel());
        this.shadowRadius = 0.0F;
    }

    public void preRender(PoseStack poseStack, ExanimatedTrident animatable, BakedGeoModel model, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        Vec3 motion = animatable.getDeltaMovement();
        float xRot = (float)(-(Mth.atan2(motion.y, motion.horizontalDistance()) * (180D / Math.PI)));
        float yRot = -((float)(Mth.atan2(motion.z, motion.x) * (double)(180F / (float)Math.PI)) + 90.0F) + 180.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));
        poseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }
}
