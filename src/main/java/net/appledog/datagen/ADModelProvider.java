package net.appledog.datagen;

import net.appledog.registry.ADBlocks;
import net.appledog.registry.ADItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ADModelProvider extends FabricModelProvider {
    public ADModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerLog(ADBlocks.APPLOG).log(ADBlocks.APPLOG).wood(ADBlocks.APPLOOD);
        blockStateModelGenerator.registerSingleton(ADBlocks.APPLEAVES, TexturedModel.LEAVES);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ADItems.APPLEDOG_SPAWN_EGG, new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ADItems.APPLEPUP_SPAWN_EGG, new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ADItems.APPLESAUCE, Models.GENERATED);
        itemModelGenerator.register(ADItems.APPLEDOGLLAR, Models.GENERATED);
        itemModelGenerator.register(ADItems.APPLEROCK, Models.GENERATED);
    }
}
