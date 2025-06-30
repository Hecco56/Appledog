package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.entity.AppledogEntity;
import net.appledog.entity.ApplepupEntity;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ADEntities {
    public static final EntityType<AppledogEntity> APPLEDOG = register("appledog", EntityType.Builder.create(AppledogEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.6F, 0.85F).maxTrackingRange(10).build("appledog")); //.eyeHeight(0.68F).passengerAttachments(new Vec3d(0.0, 0.81875, -0.0625))

    public static final EntityType<ApplepupEntity> APPLEPUP = register("applepup", EntityType.Builder.create(ApplepupEntity::new, SpawnGroup.CREATURE)
            .setDimensions(0.5F, 0.5F).build("applepup")); //.eyeHeight(0.68F).maxTrackingRange(10)

    private static <T extends Entity, E extends EntityType<T>> EntityType<T> register(String id, E entity) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(Appledog.MOD_ID, id), entity);
    }

    public static final DefaultParticleType APPLESAUCE = registerParticle("applesauce", FabricParticleTypes.simple());
    private static DefaultParticleType registerParticle(String name, DefaultParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Appledog.MOD_ID, name), particleType);
    }

    public static void loadEntities() {
    }
}
