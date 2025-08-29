package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.custom.ApplecogBlock;
import net.appledog.custom.CoirBedBlock;
import net.minecraft.block.*;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ADBlocks {

    public static final Block APPLOG = registerBlock("applog", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final Block APPLOG_SLAB = registerBlock("applog_slab", new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).mapColor(MapColor.WOOD)));
    public static final Block APPLOOD = registerBlock("applood", new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final Block APPLEAVES = registerBlock("appleaves", new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));
    public static final Block APPLECOG = registerBlock("applecog", new ApplecogBlock(BlockBehaviour.Properties.of().noOcclusion()), Rarity.EPIC);

    public static final Block COIR_BED = registerBlock("coir_bed", new CoirBedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BROWN_BED)));

    public static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block, Rarity.COMMON);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, name), block);
    }

    public static Block registerBlock(String name, Block block, Rarity rarity) {
        registerBlockItem(name, block, rarity);
        return Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block, Rarity rarity) {
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, name), new BlockItem(block, new Item.Properties().rarity(rarity)));
    }

    public static void loadBlocks() {};
}
