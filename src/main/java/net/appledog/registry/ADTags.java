package net.appledog.registry;

import net.appledog.Appledog;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ADTags {
    public static final TagKey<Item> APPLOGS = TagKey.of(RegistryKeys.ITEM, Identifier.of(Appledog.MOD_ID, "applogs"));
}
