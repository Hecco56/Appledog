package net.appledog.registry;

import net.appledog.Appledog;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.registry.*;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import java.util.Comparator;
import java.util.function.Predicate;

public class ADItemGroups {
    private static final Comparator<Holder<PaintingVariant>> PAINTING_VARIANT_COMPARATOR = Comparator.comparing(Holder::value, Comparator.comparingInt((paintingVariant) -> 16 * 16));

    public static CreativeModeTab APPLEDOG = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "appledog"),
            FabricItemGroup.builder().title(Component.translatable("itemgroup.appledog"))
                    .icon(() -> new ItemStack(ADItems.DOGAPPLE)).displayItems((displayContext, entries) -> {
                        entries.accept(ADItems.APPLEDOG_SPAWN_EGG);
                        entries.accept(ADItems.APPLEPUP_SPAWN_EGG);
                        entries.accept(ADItems.DOGAPPLE);
                        entries.accept(ADItems.CANDIED_DOGAPPLE);
                        entries.accept(ADItems.SALTED_DOGAPPLE);
                        entries.accept(ADItems.APPLESAUCE);
                        entries.accept(ADBlocks.APPLOG);
                        entries.accept(ADBlocks.APPLOG_SLAB);
                        entries.accept(ADBlocks.APPLOOD);
                        entries.accept(ADBlocks.APPLEAVES);
                        entries.accept(ADBlocks.APPLECOG);
                        entries.accept(ADItems.APPLEROCK);
                        entries.accept(ADItems.APPLEDOGLLAR);
                        displayContext.holders().lookup(Registries.PAINTING_VARIANT).ifPresent((registryWrapper) -> {
                            addPaintings(entries, displayContext.holders(), registryWrapper, (registryEntry) -> registryEntry.is(TagKey.create(Registries.PAINTING_VARIANT, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "ad_paintings"))), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                        });
                    }).build());

    private static void addPaintings(CreativeModeTab.Output entries, HolderLookup.Provider registryLookup, HolderLookup.RegistryLookup<PaintingVariant> registryWrapper, Predicate<Holder<PaintingVariant>> filter, CreativeModeTab.TabVisibility stackVisibility) {
        RegistryOps<Tag> registryOps = registryLookup.createSerializationContext(NbtOps.INSTANCE);
        registryWrapper.listElements().filter(filter).sorted(PAINTING_VARIANT_COMPARATOR).forEach((paintingVariantEntry) -> {
            CustomData nbtComponent = CustomData.EMPTY.update(registryOps, Painting.VARIANT_MAP_CODEC, paintingVariantEntry).getOrThrow().update((nbt) -> {
                nbt.putString("id", "minecraft:painting");
            });
            ItemStack itemStack = new ItemStack(Items.PAINTING);
            itemStack.set(DataComponents.ENTITY_DATA, nbtComponent);
            entries.accept(itemStack, stackVisibility);
        });
    }
    public static void loadItemGroups() {
    }
}
