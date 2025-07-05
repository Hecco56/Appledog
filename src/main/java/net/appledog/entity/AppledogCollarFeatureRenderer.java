package net.appledog.entity;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AppledogCollarFeatureRenderer extends FeatureRenderer<AppledogEntity, AppledogEntityModel<AppledogEntity>> {
    private static final Identifier SKIN = Identifier.tryParse("textures/entity/wolf/wolf_collar.png");

    public AppledogCollarFeatureRenderer(FeatureRendererContext<AppledogEntity, AppledogEntityModel<AppledogEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AppledogEntity appledogEntity, float f, float g, float h, float j, float k, float l) {
        if (appledogEntity.isTamed() && !appledogEntity.isInvisible()) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(SKIN));
            (this.getContextModel()).render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, appledogEntity.getCollarColor().getColorComponents()[0], appledogEntity.getCollarColor().getColorComponents()[1], appledogEntity.getCollarColor().getColorComponents()[2], 255);
        }
    }
}
