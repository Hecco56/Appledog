package net.appledog;

import net.appledog.custom.CoirBedBlockEntity;
import net.appledog.entity.ApplepupEntity;
import net.appledog.registry.*;
import net.appledog.entity.AppledogEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.function.Supplier;

public class Appledog implements ModInitializer {
	public static final String MOD_ID = "appledog";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final BlockEntityType<CoirBedBlockEntity> COIR_BED_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(MOD_ID, "coir_bed"), FabricBlockEntityTypeBuilder.create(CoirBedBlockEntity::new, ADBlocks.COIR_BED).build());

	@Override
	public void onInitialize() {
		ADEntities.loadEntities();
		ADItems.loadItems();
		ADBlocks.loadBlocks();
		ADItemGroups.loadItemGroups();
		ADMobSpawns.loadSpawns();
		ADSounds.loadSounds();
		ADRegistries.loadRegistries();
		FabricDefaultAttributeRegistry.register(ADEntities.APPLEDOG, AppledogEntity.createAppledogAttributes());
		FabricDefaultAttributeRegistry.register(ADEntities.APPLEPUP, ApplepupEntity.createApplepupAttributes());

	}
}
