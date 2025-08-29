package net.appledog;

import net.appledog.custom.CoirBedBlockEntity;
import net.appledog.entity.ApplepupEntity;
import net.appledog.registry.*;
import net.appledog.entity.AppledogEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appledog implements ModInitializer {
	public static final String MOD_ID = "appledog";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final BlockEntityType<CoirBedBlockEntity> COIR_BED_BLOCK_ENTITY = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, "coir_bed"), FabricBlockEntityTypeBuilder.create(CoirBedBlockEntity::new, ADBlocks.COIR_BED).build());

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
