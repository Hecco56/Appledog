package net.appledog;

import net.appledog.entity.AppledogEntityModel;
import net.appledog.entity.AppledogEntityRenderer;
import net.appledog.registry.ADEntities;
import net.appledog.registry.ADItems;
import net.appledog.registry.ADModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AppledogClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ADModelLayers.APPLEDOG, AppledogEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ADEntities.APPLEDOG, AppledogEntityRenderer::new);
        ModelPredicateProviderRegistry.register(ADItems.DOGAPPLE, new Identifier("animation"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.getNbt() != null && itemStack.getNbt().contains("dogapple_animation")) {
                int animation = itemStack.getNbt().getInt("dogapple_animation");
                if (animation > 13) {
                    return 0.5f;
                } else if (animation > 11) {
                    return 0.75f;
                } else if (animation > 3) {
                    return 1;
                } else {
                    return 0;
                }
            }
            return 0;
        });
    }
}
