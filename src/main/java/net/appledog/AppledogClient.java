package net.appledog;

import net.appledog.registry.ADEntities;
import net.appledog.registry.ADModelLayers;
import net.appledog.entity.AppledogEntityModel;
import net.appledog.entity.AppledogEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class AppledogClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ADModelLayers.APPLEDOG, AppledogEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ADEntities.APPLEDOG, AppledogEntityRenderer::new);
    }
}
