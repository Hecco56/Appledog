package net.appledog.registry;

import net.appledog.entity.AppledogEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

public class ADMobSpawns {
    public static void loadSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.all(),
                SpawnGroup.CREATURE, ADEntities.APPLEDOG, 4096, 1, 1);
        SpawnRestriction.register(ADEntities.APPLEDOG, SpawnRestriction.Location.NO_RESTRICTIONS, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AppledogEntity::canSpawn);
    }
}
