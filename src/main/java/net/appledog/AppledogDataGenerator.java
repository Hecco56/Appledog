package net.appledog;

import net.appledog.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AppledogDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(ADLangProvider::new);
		pack.addProvider(ADModelProvider::new);
		pack.addProvider(ADEntityLootTableProvider::new);
		pack.addProvider(ADBlockLootTableProvider::new);
		pack.addProvider(ADRecipeProvider::new);
	}
}
