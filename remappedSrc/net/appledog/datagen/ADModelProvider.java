package net.appledog.datagen;

import net.appledog.registry.ADBlocks;
import net.appledog.registry.ADItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import java.util.Optional;

public class ADModelProvider extends FabricModelProvider {
    public ADModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.woodProvider(ADBlocks.APPLOG).logWithHorizontal(ADBlocks.APPLOG).wood(ADBlocks.APPLOOD);
        blockStateModelGenerator.createTrivialBlock(ADBlocks.APPLEAVES, TexturedModel.LEAVES);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ADItems.APPLEDOG_SPAWN_EGG, new ModelTemplate(Optional.of(ResourceLocation.parse("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.generateFlatItem(ADItems.APPLEPUP_SPAWN_EGG, new ModelTemplate(Optional.of(ResourceLocation.parse("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.generateFlatItem(ADItems.APPLESAUCE, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ADItems.APPLEDOGLLAR, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ADItems.APPLEROCK, ModelTemplates.FLAT_ITEM);
    }
}
