package net.appledog;

import net.appledog.entity.ApplepupEntity;
import net.appledog.registry.ADEntities;
import net.appledog.entity.AppledogEntity;
import net.appledog.registry.ADItemGroups;
import net.appledog.registry.ADItems;
import net.appledog.registry.ADMobSpawns;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appledog implements ModInitializer {
	public static final String MOD_ID = "appledog";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ADEntities.loadEntities();
		ADItems.loadItems();
		ADItemGroups.loadItemGroups();
		ADMobSpawns.loadSpawns();
		FabricDefaultAttributeRegistry.register(ADEntities.APPLEDOG, AppledogEntity.createAppledogAttributes());
		FabricDefaultAttributeRegistry.register(ADEntities.APPLEPUP, ApplepupEntity.createApplepupAttributes());
	}
}
