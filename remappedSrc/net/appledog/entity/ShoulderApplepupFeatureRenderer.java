package net.appledog.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.appledog.registry.ADEntities;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

@Environment(EnvType.CLIENT)
public class ShoulderApplepupFeatureRenderer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private final ApplepupEntityModel model;

    public ShoulderApplepupFeatureRenderer(RenderLayerParent<T, PlayerModel<T>> context, EntityModelSet loader) {
        super(context);
        this.model = new ApplepupEntityModel(loader.bakeLayer(ADModelLayers.APPLEPUP));
    }

    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, T playerEntity, float f, float g, float h, float j, float k, float l) {
        this.renderShoulderApplepup(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, true);
        this.renderShoulderApplepup(matrixStack, vertexConsumerProvider, i, playerEntity, f, g, k, l, false);
    }

    private void renderShoulderApplepup(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T player, float limbAngle, float limbDistance, float headYaw, float headPitch, boolean leftShoulder) {
        CompoundTag nbtCompound = leftShoulder ? player.getShoulderEntityLeft() : player.getShoulderEntityRight();
        EntityType.byString(nbtCompound.getString("id")).filter((type) -> type == ADEntities.APPLEPUP).ifPresent((type) -> {
            matrices.pushPose();
            matrices.translate(leftShoulder ? 0.4F : -0.4F, player.isCrouching() ? -1.3F : -1.5F, 0.0F);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutout(ApplepupEntityRenderer.getTexture(AppledogEntity.Variant.byId(nbtCompound.getInt("Variant")))));
            this.model.root().render(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
            matrices.popPose();
        });
    }
}
