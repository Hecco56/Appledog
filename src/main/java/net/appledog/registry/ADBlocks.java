package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ADBlocks {

    public static final Block APPLOG = registerBlock("applog", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG)));
    public static final Block APPLOG_SLAB = registerBlock("applog_slab", new SlabBlock(AbstractBlock.Settings.copy(Blocks.OAK_LOG).mapColor(MapColor.OAK_TAN)));
    public static final Block APPLOOD = registerBlock("applood", new PillarBlock(AbstractBlock.Settings.copy(Blocks.OAK_WOOD)));
    public static final Block APPLEAVES = registerBlock("appleaves", new LeavesBlock(AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)));
    public static final Block APPLECOG = registerBlock("applecog", new ApplecogBlock(AbstractBlock.Settings.create().nonOpaque()));

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Appledog.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    public static void loadBlocks() {};
}
