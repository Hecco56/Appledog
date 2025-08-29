package net.appledog.registry;

import net.appledog.entity.AppledogEntity;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class ADMobSpawns {
    public static void loadSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.all(),
                MobCategory.AMBIENT, ADEntities.APPLEDOG, 1, 0, 1);
        SpawnPlacements.register(ADEntities.APPLEDOG, SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AppledogEntity::canSpawn);
    }
}
