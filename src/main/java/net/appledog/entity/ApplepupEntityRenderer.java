package net.appledog.entity;

import net.appledog.Appledog;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class ApplepupEntityRenderer extends MobEntityRenderer<ApplepupEntity, ApplepupEntityModel<ApplepupEntity>> {

    private static final Identifier RED_DELICIOUS_TEXTURE = Identifier.of(Appledog.MOD_ID, "textures/entity/applepup/red_delicious.png");

    public ApplepupEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ApplepupEntityModel<>(context.getPart(ADModelLayers.APPLEPUP)), 0.2F);
    }
    @Override
    public Identifier getTexture(ApplepupEntity entity) {
        return getTexture(entity.getVariant());
    }

    public static Identifier getTexture(AppledogEntity.Variant entity) {
        AppledogEntity.Variant[] variants = AppledogEntity.Variant.values();
        for (AppledogEntity.Variant variant : variants) {
            if (variant == entity) {
                return Identifier.of(Appledog.MOD_ID, "textures/entity/applepup/" + variant.getName() + ".png");
            }
        }
        return RED_DELICIOUS_TEXTURE;
    }

    @Nullable
    @Override
    protected RenderLayer getRenderLayer(ApplepupEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderLayer.getEntityCutout(getTexture(entity));
    }
}
