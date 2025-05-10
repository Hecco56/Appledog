package net.appledog.entity;

import net.appledog.Appledog;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class AppledogEntityRenderer extends MobEntityRenderer<AppledogEntity, AppledogEntityModel<AppledogEntity>> {
    private static final Identifier RED_DELICIOUS_TEXTURE = Identifier.of(Appledog.MOD_ID, "textures/entity/appledog/red_delicious.png");
    public AppledogEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AppledogEntityModel<>(context.getPart(ADModelLayers.APPLEDOG)), 0.5F);
        this.addFeature(new AppledogCollarFeatureRenderer(this));
    }
    @Override
    public Identifier getTexture(AppledogEntity entity) {
        AppledogEntity.Variant[] variants = AppledogEntity.Variant.values();
        for (AppledogEntity.Variant variant : variants) {
            if (variant == entity.getVariant()) {
                return Identifier.of(Appledog.MOD_ID, "textures/entity/appledog/" + variant.getName() + ".png");
            }
        }
        return RED_DELICIOUS_TEXTURE;
    }

    @Nullable
    @Override
    protected RenderLayer getRenderLayer(AppledogEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        return RenderLayer.getEntityCutout(getTexture(entity));
    }
}
