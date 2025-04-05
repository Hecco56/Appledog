package net.appledog.registry;

import net.appledog.Appledog;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ADItemGroups {
    public static final ItemGroup APPLEDOG = FabricItemGroupBuilder.build(new Identifier(Appledog.MOD_ID, "appledog"), () -> new ItemStack(ADItems.DOGAPPLE));
    public static void loadItemGroups() {
    }
}
