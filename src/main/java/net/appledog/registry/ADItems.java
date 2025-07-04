package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.custom.ApplerockItem;
import net.appledog.custom.CandiedDogappleItem;
import net.appledog.custom.DogappleItem;
import net.appledog.custom.SaltedDogappleItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ADItems {
    public static final Item APPLEDOG_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "appledog_spawn_egg"), new SpawnEggItem(ADEntities.APPLEDOG, 0xfb192c, 0xdd1725, new Item.Settings().rarity(Rarity.RARE)));
    public static final Item APPLEPUP_SPAWN_EGG = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "applepup_spawn_egg"), new SpawnEggItem(ADEntities.APPLEPUP, 0x8b0811, 0xfb192c, new Item.Settings().rarity(Rarity.RARE)));
    public static final Item DOGAPPLE = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "dogapple"), new DogappleItem(new Item.Settings().rarity(Rarity.EPIC).component(DogappleItem.DOGAPPLE_ANIMATION, 0).maxCount(1)));
    public static final Item CANDIED_DOGAPPLE = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "candied_dogapple"), new CandiedDogappleItem(new Item.Settings().rarity(Rarity.EPIC).maxCount(1).component(DogappleItem.DOGAPPLE_ANIMATION, 0).food(new FoodComponent.Builder().nutrition(12).saturationModifier(0.5F).build())));
    public static final Item SALTED_DOGAPPLE = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "salted_dogapple"), new SaltedDogappleItem(new Item.Settings().rarity(Rarity.EPIC).maxCount(1).component(DogappleItem.DOGAPPLE_ANIMATION, 0)));
    public static final Item APPLESAUCE = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "applesauce"), new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(1).food(new FoodComponent.Builder().nutrition(8).saturationModifier(1.0F).usingConvertsTo(Items.BOWL).build())));
    public static final Item APPLEDOGLLAR = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "appledogllar"), new Item(new Item.Settings().rarity(Rarity.UNCOMMON)));
    public static final Item APPLEROCK = Registry.register(Registries.ITEM, Identifier.of(Appledog.MOD_ID, "applerock"), new ApplerockItem(new Item.Settings().rarity(Rarity.UNCOMMON)));

    public static void loadItems() {
    }
}
