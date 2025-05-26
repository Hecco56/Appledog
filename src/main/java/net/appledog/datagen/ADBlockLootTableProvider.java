package net.appledog.datagen;

import net.appledog.registry.ADBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ADBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ADBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ADBlocks.APPLOG);
        addDrop(ADBlocks.APPLOOD);
        addDrop(ADBlocks.APPLOG_SLAB, slabDrops(ADBlocks.APPLOG_SLAB));
        mangroveLeavesDrops(ADBlocks.APPLEAVES);
    }
}
