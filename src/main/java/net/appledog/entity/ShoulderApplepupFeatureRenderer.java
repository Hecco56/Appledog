package net.appledog.entity;

import net.appledog.registry.ADEntities;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ShoulderApplepupFeatureRenderer<T extends PlayerEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
    private final ApplepupEntityModel model;

    public ShoulderApplepupFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context, EntityModelLoader loader) {
        super(context);
        this.model = new ApplepupEntityModel(loader.getModelPart(ADModelLayers.APPLEPUP));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T playerEntity, float f, float g, float h, float j, float k, float l) {
        this.renderShoulderApplepup(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, true);
        this.renderShoulderApplepup(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, false);
    }

    private void renderShoulderApplepup(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T player, float limbAngle, float limbDistance, float headYaw, float headPitch, boolean leftShoulder) {
        NbtCompound nbtCompound = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
        EntityType.get(nbtCompound.getString("id")).filter((type) -> type == ADEntities.APPLEPUP).ifPresent((type) -> {
            matrices.push();
            matrices.translate(leftShoulder ? 0.4F : -0.4F, player.isInSneakingPose() ? -1.3F : -1.5F, 0.0F);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(ApplepupEntityRenderer.getTexture(AppledogEntity.Variant.byId(nbtCompound.getInt("Variant")))));
            this.model.getPart().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
            matrices.pop();
        });
    }
}
