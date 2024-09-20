package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.entity.AppledogEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class ADEntities {
    public static final EntityType<AppledogEntity> APPLEDOG = register("appledog", EntityType.Builder.create(AppledogEntity::new, SpawnGroup.CREATURE)
            .dimensions(0.6F, 0.85F).eyeHeight(0.68F).passengerAttachments(new Vec3d(0.0, 0.81875, -0.0625)).maxTrackingRange(10).build("appledog"));
    private static <T extends Entity, E extends EntityType<T>> EntityType<T> register(String id, E entity) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(Appledog.MOD_ID, id), entity);
    }
    public static void loadEntities() {
    }
}
