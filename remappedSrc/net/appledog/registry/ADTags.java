package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ADTags {
    public static final TagKey<Item> APPLOGS = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "applogs"));
}
