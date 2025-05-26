package net.appledog;

import net.appledog.entity.ApplepupEntityModel;
import net.appledog.entity.ApplepupEntityRenderer;
import net.appledog.registry.*;
import net.appledog.entity.AppledogEntityModel;
import net.appledog.entity.AppledogEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.biome.FoliageColors;

@Environment(EnvType.CLIENT)
public class AppledogClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ADModelLayers.APPLEDOG, AppledogEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ADModelLayers.APPLEPUP, ApplepupEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ADEntities.APPLEDOG, AppledogEntityRenderer::new);
        EntityRendererRegistry.register(ADEntities.APPLEPUP, ApplepupEntityRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ADEntities.APPLESAUCE, ApplesauceParticle.Factory::new);
        ModelPredicateProviderRegistry.register(ADItems.DOGAPPLE, Identifier.of("animation"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.getComponents().contains(DogappleItem.DOGAPPLE_ANIMATION)) {
                int animation = itemStack.get(DogappleItem.DOGAPPLE_ANIMATION);
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
        ModelPredicateProviderRegistry.register(ADItems.CANDIED_DOGAPPLE, Identifier.of("animation"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.getComponents().contains(DogappleItem.DOGAPPLE_ANIMATION)) {
                int animation = itemStack.get(DogappleItem.DOGAPPLE_ANIMATION);
                return animation == 15 ? 1 : 0;
            }
            return 0;
        });

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getFoliageColor(world, pos)
                        : FoliageColors.getDefaultColor(),
                ADBlocks.APPLEAVES);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ColorHelper.Argb.fullAlpha(FoliageColors.getDefaultColor()), ADBlocks.APPLEAVES);
        BlockRenderLayerMap.INSTANCE.putBlock(ADBlocks.APPLECOG, RenderLayer.getCutout());
    }
}
