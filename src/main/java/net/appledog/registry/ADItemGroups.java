package net.appledog.registry;

import net.appledog.Appledog;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Comparator;
import java.util.function.Predicate;

public class ADItemGroups {
    private static final Comparator<RegistryEntry<PaintingVariant>> PAINTING_VARIANT_COMPARATOR = Comparator.comparing(RegistryEntry::value, Comparator.comparingInt((paintingVariant) -> 16 * 16));

    public static ItemGroup APPLEDOG = Registry.register(Registries.ITEM_GROUP, Identifier.of(Appledog.MOD_ID, "appledog"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.appledog"))
                    .icon(() -> new ItemStack(ADItems.DOGAPPLE)).entries((displayContext, entries) -> {
                        entries.add(ADItems.APPLEDOG_SPAWN_EGG);
                        entries.add(ADItems.APPLEPUP_SPAWN_EGG);
                        entries.add(ADItems.DOGAPPLE);
                        entries.add(ADItems.CANDIED_DOGAPPLE);
                        entries.add(ADItems.APPLESAUCE);
                        entries.add(ADBlocks.APPLOG);
                        entries.add(ADBlocks.APPLOG_SLAB);
                        entries.add(ADBlocks.APPLOOD);
                        entries.add(ADBlocks.APPLEAVES);
                        entries.add(ADBlocks.APPLECOG);
                        displayContext.lookup().getOptionalWrapper(RegistryKeys.PAINTING_VARIANT).ifPresent((registryWrapper) -> {
                            addPaintings(entries, displayContext.lookup(), registryWrapper, (registryEntry) -> registryEntry.isIn(TagKey.of(RegistryKeys.PAINTING_VARIANT, Identifier.of(Appledog.MOD_ID, "ad_paintings"))), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
                        });
                    }).build());

    private static void addPaintings(ItemGroup.Entries entries, RegistryWrapper.WrapperLookup registryLookup, RegistryWrapper.Impl<PaintingVariant> registryWrapper, Predicate<RegistryEntry<PaintingVariant>> filter, ItemGroup.StackVisibility stackVisibility) {
        RegistryOps<NbtElement> registryOps = registryLookup.getOps(NbtOps.INSTANCE);
        registryWrapper.streamEntries().filter(filter).sorted(PAINTING_VARIANT_COMPARATOR).forEach((paintingVariantEntry) -> {
            NbtComponent nbtComponent = NbtComponent.DEFAULT.with(registryOps, PaintingEntity.VARIANT_MAP_CODEC, paintingVariantEntry).getOrThrow().apply((nbt) -> {
                nbt.putString("id", "minecraft:painting");
            });
            ItemStack itemStack = new ItemStack(Items.PAINTING);
            itemStack.set(DataComponentTypes.ENTITY_DATA, nbtComponent);
            entries.add(itemStack, stackVisibility);
        });
    }
    public static void loadItemGroups() {
    }
}
