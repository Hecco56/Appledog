package net.appledog.entity;

import net.appledog.registry.ADEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FrogEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.IntFunction;

public class ApplepupEntity extends PassiveEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ApplepupEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> AGE = DataTracker.registerData(ApplepupEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public ApplepupEntity(EntityType<? extends ApplepupEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new LookAroundGoal(this));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
    }

    public static DefaultAttributeContainer.Builder createApplepupAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f).add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 256.0);
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(VARIANT, 0);
        builder.add(AGE, 0);
        super.initDataTracker(builder);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant().getId());
        nbt.putInt("Age", dataTracker.get(AGE));
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(AppledogEntity.Variant.byId(nbt.getInt("Variant")));
        this.setAge(nbt.getInt("Age"));
    }

    @Override
    public void tick() {
        if (dataTracker.get(AGE) >= 2400) {
            World world = this.getWorld();
            if (world instanceof ServerWorld serverWorld) {
                AppledogEntity appledogEntity = ADEntities.APPLEDOG.create(this.getWorld());
                if (appledogEntity != null) {
                    appledogEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
                    appledogEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(appledogEntity.getBlockPos()), SpawnReason.CONVERSION, null);
                    appledogEntity.setAiDisabled(this.isAiDisabled());
                    appledogEntity.setVariant(AppledogEntity.Variant.byId(dataTracker.get(VARIANT)));
                    if (this.hasCustomName()) {
                        appledogEntity.setCustomName(this.getCustomName());
                        appledogEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }

                    appledogEntity.setPersistent();
                    appledogEntity.recalculateDimensions(this.getDimensions(this.getPose()));
                    serverWorld.spawnEntityAndPassengers(appledogEntity);
                    this.discard();
                }
            }
        }
        dataTracker.set(AGE, dataTracker.get(AGE) + 1);
        super.tick();
    }

    public void setVariant(AppledogEntity.Variant variant) {
        this.dataTracker.set(VARIANT, variant.getId());
    }

    public void setAge(int age) {
        this.dataTracker.set(AGE, age);
    }

    public AppledogEntity.Variant getVariant() {
        return AppledogEntity.Variant.byId(this.dataTracker.get(VARIANT));
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (spawnReason == SpawnReason.SPAWN_EGG) {
            Random random = world.getRandom();
            if (entityData instanceof ApplepupEntity.AppledogData) {
            } else {
                entityData = new ApplepupEntity.AppledogData(AppledogEntity.Variant.getRandom(random), AppledogEntity.Variant.getRandom(random));
            }

            this.setVariant(((ApplepupEntity.AppledogData)entityData).getRandomVariant(random));

            return super.initialize(world, difficulty, spawnReason, entityData);
        } else {
            return entityData;
        }
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public static class AppledogData implements EntityData {
        public final AppledogEntity.Variant[] variants;

        public AppledogData(AppledogEntity.Variant... variants) {
            this.variants = variants;
        }

        public AppledogEntity.Variant getRandomVariant(Random random) {
            return this.variants[random.nextInt(this.variants.length)];
        }
    }

}