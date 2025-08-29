package net.appledog.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class AppledogCollarFeatureRenderer extends RenderLayer<AppledogEntity, AppledogEntityModel<AppledogEntity>> {
    private static final ResourceLocation SKIN = ResourceLocation.withDefaultNamespace("textures/entity/wolf/wolf_collar.png");

    public AppledogCollarFeatureRenderer(RenderLayerParent<AppledogEntity, AppledogEntityModel<AppledogEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, AppledogEntity appledogEntity, float f, float g, float h, float j, float k, float l) {
        if (appledogEntity.isTame() && !appledogEntity.isInvisible()) {
            int m = appledogEntity.getCollarColor().getTextureDiffuseColor();
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutoutNoCull(SKIN));
            (this.getParentModel()).renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY, m);
        }
    }
}
