package net.appledog.registry;

import net.appledog.Appledog;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ADItemGroups {
    public static ItemGroup APPLEDOG = Registry.register(Registries.ITEM_GROUP, Identifier.of(Appledog.MOD_ID, "appledog"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.appledog"))
                    .icon(() -> new ItemStack(ADItems.DOGAPPLE)).entries((displayContext, entries) -> {
                        entries.add(ADItems.APPLEDOG_SPAWN_EGG);
                        entries.add(ADItems.APPLEPUP_SPAWN_EGG);
                        entries.add(ADItems.DOGAPPLE);
                        entries.add(ADItems.CANDIED_DOGAPPLE);
                        entries.add(ADItems.APPLESAUCE);
                    }).build());
    public static void loadItemGroups() {
    }
}
