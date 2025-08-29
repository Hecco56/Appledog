package net.appledog.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.appledog.Appledog;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class AppledogEntityRenderer extends MobRenderer<AppledogEntity, AppledogEntityModel<AppledogEntity>> {
    private static final ResourceLocation RED_DELICIOUS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "textures/entity/appledog/red_delicious.png");
    public AppledogEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new AppledogEntityModel<>(context.bakeLayer(ADModelLayers.APPLEDOG)), 0.5F);
        this.addLayer(new AppledogCollarFeatureRenderer(this));
    }
    @Override
    public ResourceLocation getTexture(AppledogEntity entity) {
        AppledogEntity.Variant[] variants = AppledogEntity.Variant.values();
        for (AppledogEntity.Variant variant : variants) {
            if (variant == entity.getVariant()) {
                return ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "textures/entity/appledog/" + variant.getName() + ".png");
            }
        }
        return RED_DELICIOUS_TEXTURE;
    }

    @Override
    public void render(AppledogEntity livingEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Nullable
    @Override
    protected RenderType getRenderLayer(AppledogEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderType.entityCutout(getTexture(entity));
    }
}
