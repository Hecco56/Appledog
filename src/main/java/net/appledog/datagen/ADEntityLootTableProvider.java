package net.appledog.datagen;

import net.appledog.registry.ADEntities;
import net.appledog.registry.ADItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextType;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public class ADEntityLootTableProvider extends SimpleFabricLootTableProvider {


    public ADEntityLootTableProvider(FabricDataGenerator dataGenerator, LootContextType lootContextType) {
        super(dataGenerator, lootContextType);
    }

    @Override
    public void accept(BiConsumer<Identifier, LootTable.Builder> identifierBuilderBiConsumer) {
        identifierBuilderBiConsumer.accept(ADEntities.APPLEDOG.getLootTableId(), LootTable.builder()
                .pool(LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1.0F))
                        .with(ItemEntry.builder(Items.APPLE).weight(25))
                        .with(ItemEntry.builder(ADItems.DOGAPPLE).weight(1))
                ));
    }
}
