package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.custom.ApplerockItem;
import net.appledog.custom.CandiedDogappleItem;
import net.appledog.custom.DogappleItem;
import net.appledog.custom.SaltedDogappleItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;

public class ADItems {
    public static final Item APPLEDOG_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "appledog_spawn_egg"), new SpawnEggItem(ADEntities.APPLEDOG, 0xfb192c, 0xdd1725, new Item.Properties().rarity(Rarity.RARE)));
    public static final Item APPLEPUP_SPAWN_EGG = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "applepup_spawn_egg"), new SpawnEggItem(ADEntities.APPLEPUP, 0x8b0811, 0xfb192c, new Item.Properties().rarity(Rarity.RARE)));
    public static final Item DOGAPPLE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "dogapple"), new DogappleItem(new Item.Properties().rarity(Rarity.EPIC).component(DogappleItem.DOGAPPLE_ANIMATION, 0).stacksTo(1)));
    public static final Item CANDIED_DOGAPPLE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "candied_dogapple"), new CandiedDogappleItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).component(DogappleItem.DOGAPPLE_ANIMATION, 0).food(new FoodProperties.Builder().nutrition(12).saturationModifier(0.5F).build())));
    public static final Item SALTED_DOGAPPLE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "salted_dogapple"), new SaltedDogappleItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(1).component(DogappleItem.DOGAPPLE_ANIMATION, 0)));
    public static final Item APPLESAUCE = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "applesauce"), new Item(new Item.Properties().rarity(Rarity.RARE).stacksTo(1).food(new FoodProperties.Builder().nutrition(8).saturationModifier(1.0F).usingConvertsTo(Items.BOWL).build())));
    public static final Item APPLEDOGLLAR = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "appledogllar"), new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final Item APPLEROCK = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "applerock"), new ApplerockItem(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static void loadItems() {
    }
}
