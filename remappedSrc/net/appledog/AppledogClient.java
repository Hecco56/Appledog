package net.appledog;

import net.appledog.custom.ApplesauceParticle;
import net.appledog.custom.CoirBedBlockEnitityRenderer;
import net.appledog.custom.DogappleItem;
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
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.FoliageColor;

@Environment(EnvType.CLIENT)
public class AppledogClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(ADModelLayers.APPLEDOG, AppledogEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ADModelLayers.APPLEPUP, ApplepupEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ADEntities.APPLEDOG, AppledogEntityRenderer::new);
        EntityRendererRegistry.register(ADEntities.APPLEPUP, ApplepupEntityRenderer::new);
        EntityRendererRegistry.register(ADEntities.APPLEROCK, ThrownItemRenderer::new);
        ParticleFactoryRegistry.getInstance().register(ADEntities.APPLESAUCE, ApplesauceParticle.Factory::new);
        ItemProperties.register(ADItems.DOGAPPLE, ResourceLocation.parse("animation"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.getComponents().has(DogappleItem.DOGAPPLE_ANIMATION)) {
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
        ItemProperties.register(ADItems.CANDIED_DOGAPPLE, ResourceLocation.parse("animation"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.getComponents().has(DogappleItem.DOGAPPLE_ANIMATION)) {
                int animation = itemStack.get(DogappleItem.DOGAPPLE_ANIMATION);
                return animation == 15 ? 1 : 0;
            }
            return 0;
        });

        ItemProperties.register(ADItems.SALTED_DOGAPPLE, ResourceLocation.parse("animation"), (itemStack, clientWorld, livingEntity, seed) -> {
            if (itemStack.getComponents().has(DogappleItem.DOGAPPLE_ANIMATION)) {
                int animation = itemStack.get(DogappleItem.DOGAPPLE_ANIMATION);
                return animation <= 1 ? 0 : 1;
            }
            return 0;
        });

        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColors.getAverageFoliageColor(world, pos)
                        : FoliageColor.getDefaultColor(),
                ADBlocks.APPLEAVES);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FastColor.ARGB32.opaque(FoliageColor.getDefaultColor()), ADBlocks.APPLEAVES);
        BlockRenderLayerMap.INSTANCE.putBlock(ADBlocks.APPLECOG, RenderType.cutout());

        BlockEntityRenderers.register(Appledog.COIR_BED_BLOCK_ENTITY, CoirBedBlockEnitityRenderer::new);
    }
}
