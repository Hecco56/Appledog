package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ADItems {
    public static final Item APPLEDOG_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "appledog_spawn_egg"), new SpawnEggItem(ADEntities.APPLEDOG, 0xfb192c, 0xdd1725, new Item.Settings()));
    public static final Item DOGAPPLE = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "dogapple"), new DogappleItem(new Item.Settings().rarity(Rarity.RARE)));

    public static void loadItems() {
    }
}
