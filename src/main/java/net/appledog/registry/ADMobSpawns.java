package net.appledog.registry;

import net.appledog.entity.AppledogEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class ADMobSpawns {
    public static void loadSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.all(),
                SpawnGroup.CREATURE, ADEntities.APPLEDOG, 2048, 1, 1);
        SpawnRestriction.register(ADEntities.APPLEDOG, SpawnLocationTypes.UNRESTRICTED, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AppledogEntity::canSpawn);
    }
}
