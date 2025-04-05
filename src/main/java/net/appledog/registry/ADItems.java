package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ADItems {
    public static final Item APPLEDOG_SPAWN_EGG = Registry.register(Registry.ITEM, Identifier.of(Appledog.MOD_ID, "appledog_spawn_egg"), new SpawnEggItem(ADEntities.APPLEDOG, 0xfb192c, 0xdd1725, new Item.Settings().group(ADItemGroups.APPLEDOG)));
    public static final Item APPLEPUP_SPAWN_EGG = Registry.register(Registry.ITEM, Identifier.of(Appledog.MOD_ID, "applepup_spawn_egg"), new SpawnEggItem(ADEntities.APPLEPUP, 0xdd1725, 0xfb192c, new Item.Settings().group(ADItemGroups.APPLEDOG)));
    public static final Item DOGAPPLE = Registry.register(Registry.ITEM, Identifier.of(Appledog.MOD_ID, "dogapple"), new DogappleItem(new Item.Settings().rarity(Rarity.RARE).maxCount(1).group(ADItemGroups.APPLEDOG)));

    public static void loadItems() {

    }
}
