package net.appledog.registry;

import net.appledog.Appledog;
import net.appledog.entity.AppledogEntity;
import net.appledog.entity.ApplepupEntity;
import net.appledog.entity.ApplerockEntity;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.phys.Vec3;

public class ADEntities {
    public static final EntityType<AppledogEntity> APPLEDOG = register("appledog", EntityType.Builder.of(AppledogEntity::new, MobCategory.CREATURE)
            .sized(0.6F, 0.85F).eyeHeight(0.68F).passengerAttachments(new Vec3(0.0, 0.81875, -0.0625)).clientTrackingRange(10).build("appledog"));

    public static final EntityType<ApplepupEntity> APPLEPUP = register("applepup", EntityType.Builder.of(ApplepupEntity::new, MobCategory.CREATURE)
            .sized(0.4F, 0.4F).eyeHeight(0.68F).clientTrackingRange(10).build("applepup"));

    public static final EntityType<ApplerockEntity> APPLEROCK = register("applerock", EntityType.Builder.<ApplerockEntity>of(ApplerockEntity::new, MobCategory.MISC)
            .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("applerock"));


    private static <T extends Entity, E extends EntityType<T>> EntityType<T> register(String id, E entity) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, id), entity);
    }

    public static final SimpleParticleType APPLESAUCE = registerParticle("applesauce", FabricParticleTypes.simple());
    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, name), particleType);
    }

    public static void loadEntities() {
    }
}
