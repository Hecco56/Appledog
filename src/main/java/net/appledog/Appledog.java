package net.appledog;

import net.appledog.registry.ADEntities;
import net.appledog.entity.AppledogEntity;
import net.appledog.registry.ADItems;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Appledog implements ModInitializer {
	public static final String MOD_ID = "appledog";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ADEntities.loadEntities();
		ADItems.loadItems();
		FabricDefaultAttributeRegistry.register(ADEntities.APPLEDOG, AppledogEntity.createAppledogAttributes());
	}
}