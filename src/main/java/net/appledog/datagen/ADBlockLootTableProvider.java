package net.appledog.datagen;

import net.appledog.registry.ADBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
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
        addDrop(ADBlocks.APPLECOG);
        mangroveLeavesDrops(ADBlocks.APPLEAVES);
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        this.dropsWithSilkTouchOrShears(ADBlocks.APPLEAVES, ((LeafEntry.Builder<?>)this.applyExplosionDecay(ADBlocks.APPLEAVES, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))))).conditionally(TableBonusLootCondition.builder(impl.getOrThrow(Enchantments.FORTUNE), 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)));
    }
}
