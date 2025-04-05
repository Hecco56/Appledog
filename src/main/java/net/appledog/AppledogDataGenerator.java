package net.appledog;

import net.appledog.datagen.ADEntityLootTableProvider;
import net.appledog.datagen.ADLangProvider;
import net.appledog.datagen.ADModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AppledogDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		fabricDataGenerator.addProvider(ADLangProvider::new);
		fabricDataGenerator.addProvider(ADModelProvider::new);
	}
}
