package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.entity.AppledogEntity;
import net.appledog.entity.ApplepupEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ADEntities {
    public static final EntityType<AppledogEntity> APPLEDOG = register("appledog", EntityType.Builder.create(AppledogEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.6F, 0.85F).maxTrackingRange(10).build("appledog"));

    public static final EntityType<ApplepupEntity> APPLEPUP = register("applepup", EntityType.Builder.create(ApplepupEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.5F, 0.5F).maxTrackingRange(10).build("applepup"));

    private static <T extends Entity, E extends EntityType<T>> EntityType<T> register(String id, E entity) {
        return Registry.register(Registry.ENTITY_TYPE, Identifier.of(Appledog.MOD_ID, id), entity);
    }
    public static void loadEntities() {
    }
}
