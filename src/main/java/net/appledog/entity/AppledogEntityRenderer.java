package net.appledog.entity;

import net.appledog.Appledog;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AppledogEntityRenderer extends MobEntityRenderer<AppledogEntity, AppledogEntityModel<AppledogEntity>> {
    private static final Identifier TEXTURE = Identifier.of(Appledog.MOD_ID, "textures/entity/appledog.png");
    public AppledogEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new AppledogEntityModel<>(context.getPart(ADModelLayers.APPLEDOG)), 0.5F);
    }
    @Override
    public Identifier getTexture(AppledogEntity entity) {
        return TEXTURE;
    }
}
