package net.appledog.entity;

import net.appledog.Appledog;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ApplepupEntityRenderer extends MobRenderer<ApplepupEntity, ApplepupEntityModel<ApplepupEntity>> {

    private static final ResourceLocation RED_DELICIOUS_TEXTURE = ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "textures/entity/applepup/red_delicious.png");

    public ApplepupEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new ApplepupEntityModel<>(context.bakeLayer(ADModelLayers.APPLEPUP)), 0.2F);
    }
    @Override
    public ResourceLocation getTexture(ApplepupEntity entity) {
        return getTexture(entity.getVariant());
    }

    public static ResourceLocation getTexture(AppledogEntity.Variant entity) {
        AppledogEntity.Variant[] variants = AppledogEntity.Variant.values();
        for (AppledogEntity.Variant variant : variants) {
            if (variant == entity) {
                return ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "textures/entity/applepup/" + variant.getName() + ".png");
            }
        }
        return RED_DELICIOUS_TEXTURE;
    }

    @Nullable
    @Override
    protected RenderType getRenderLayer(ApplepupEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderType.entityCutout(getTexture(entity));
    }
}
