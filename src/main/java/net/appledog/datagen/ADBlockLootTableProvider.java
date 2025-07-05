package net.appledog.datagen;

import net.appledog.registry.ADBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class ADBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ADBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ADBlocks.APPLOG);
        addDrop(ADBlocks.APPLOOD);
        addDrop(ADBlocks.APPLOG_SLAB, slabDrops(ADBlocks.APPLOG_SLAB));
        addDrop(ADBlocks.APPLECOG);
        mangroveLeavesDrops(ADBlocks.APPLEAVES);
        this.dropsWithSilkTouchOrShears(ADBlocks.APPLEAVES, ((LeafEntry.Builder<?>)this.applyExplosionDecay(ADBlocks.APPLEAVES, ItemEntry.builder(Items.STICK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F))))).conditionally(TableBonusLootCondition.builder(Enchantments.SILK_TOUCH, 0.02F, 0.022222223F, 0.025F, 0.033333335F, 0.1F)));
    }
}
