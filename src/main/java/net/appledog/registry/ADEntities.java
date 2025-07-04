package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.entity.AppledogEntity;
import net.appledog.entity.ApplepupEntity;
import net.appledog.entity.ApplerockEntity;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class ADEntities {
    public static final EntityType<AppledogEntity> APPLEDOG = register("appledog", EntityType.Builder.create(AppledogEntity::new, SpawnGroup.CREATURE)
            .dimensions(0.6F, 0.85F).eyeHeight(0.68F).passengerAttachments(new Vec3d(0.0, 0.81875, -0.0625)).maxTrackingRange(10).build("appledog"));

    public static final EntityType<ApplepupEntity> APPLEPUP = register("applepup", EntityType.Builder.create(ApplepupEntity::new, SpawnGroup.CREATURE)
            .dimensions(0.4F, 0.4F).eyeHeight(0.68F).maxTrackingRange(10).build("applepup"));

    public static final EntityType<ApplerockEntity> APPLEROCK = register("applerock", EntityType.Builder.<ApplerockEntity>create(ApplerockEntity::new, SpawnGroup.MISC)
            .dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10).build("applerock"));


    private static <T extends Entity, E extends EntityType<T>> EntityType<T> register(String id, E entity) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(Appledog.MOD_ID, id), entity);
    }

    public static final SimpleParticleType APPLESAUCE = registerParticle("applesauce", FabricParticleTypes.simple());
    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Appledog.MOD_ID, name), particleType);
    }

    public static void loadEntities() {
    }
}
