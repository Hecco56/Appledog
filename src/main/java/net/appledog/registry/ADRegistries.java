package net.appledog.registry;

import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.block.Blocks;

public class ADRegistries {

    public static void loadRegistries() {
        StrippableBlockRegistry.register(ADBlocks.APPLOG, Blocks.STRIPPED_OAK_LOG);
        StrippableBlockRegistry.register(ADBlocks.APPLOOD, Blocks.STRIPPED_OAK_WOOD);
    }
}
