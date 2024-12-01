package net.appledog.entity;

import net.appledog.Appledog;
import net.appledog.registry.ADEntities;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
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
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.IntFunction;

public class AppledogEntity extends AnimalEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(AppledogEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public AppledogEntity(EntityType<? extends AppledogEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0F);
    }

    public static boolean canSpawn(EntityType<AppledogEntity> appledogEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return blockPos.getX() < -205 && blockPos.getX() > -305 && blockPos.getZ() > 19 && blockPos.getZ() < 119;
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(7, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.2, (stack) -> stack.isOf(Items.APPLE), false));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAppledogAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 128.0);
    }
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.APPLE);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    public MobEntity createAppleChild(ServerWorld world, PassiveEntity entity) {
        ApplepupEntity childEntity = ADEntities.APPLEPUP.create(world);
        if (childEntity != null && entity instanceof AppledogEntity entity2) {
            if (random.nextBoolean()) {
                if (this.random.nextBoolean()) {
                    childEntity.setVariant(this.getVariant());
                } else {
                    switch (this.getVariant()) {
                        case RED_DELICIOUS -> childEntity.setVariant(random.nextBoolean() ? Variant.MACOUN : Variant.PINK_LADY);
                        case MACOUN -> childEntity.setVariant(random.nextBoolean() ? Variant.RED_DELICIOUS : Variant.GRANNY_SMITH);
                        case PINK_LADY -> childEntity.setVariant(random.nextBoolean() ? Variant.RED_DELICIOUS : Variant.GOLDEN_DELICIOUS);
                        case GRANNY_SMITH -> childEntity.setVariant(random.nextBoolean() ? Variant.MACOUN : Variant.GOLDEN_DELICIOUS);
                        case GOLDEN_DELICIOUS -> childEntity.setVariant(random.nextBoolean() ? Variant.PINK_LADY : Variant.GRANNY_SMITH);
                    }
                }
            } else {
                if (this.random.nextBoolean()) {
                    childEntity.setVariant(entity2.getVariant());
                } else {
                    switch (entity2.getVariant()) {
                        case RED_DELICIOUS -> childEntity.setVariant(random.nextBoolean() ? Variant.MACOUN : Variant.PINK_LADY);
                        case MACOUN -> childEntity.setVariant(random.nextBoolean() ? Variant.RED_DELICIOUS : Variant.GRANNY_SMITH);
                        case PINK_LADY -> childEntity.setVariant(random.nextBoolean() ? Variant.RED_DELICIOUS : Variant.GOLDEN_DELICIOUS);
                        case GRANNY_SMITH -> childEntity.setVariant(random.nextBoolean() ? Variant.MACOUN : Variant.GOLDEN_DELICIOUS);
                        case GOLDEN_DELICIOUS -> childEntity.setVariant(random.nextBoolean() ? Variant.PINK_LADY : Variant.GRANNY_SMITH);
                    }
                }
            }
        }

        return childEntity;
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        MobEntity entity = this.createAppleChild(world, other);
        if (entity != null) {
            entity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.appleBreed(world, (AppledogEntity) other, entity);
            world.spawnEntityAndPassengers(entity);
        }
    }

    public void appleBreed(ServerWorld world, AppledogEntity other, @Nullable MobEntity baby) {
        Optional.ofNullable(this.getLovingPlayer()).or(() -> Optional.ofNullable(other.getLovingPlayer())).ifPresent((player) -> {
            player.incrementStat(Stats.ANIMALS_BRED);
            Criteria.BRED_ANIMALS.trigger(player, this, other, (PassiveEntity) baby);
        });
        this.setBreedingAge(6000);
        other.setBreedingAge(6000);
        this.resetLoveTicks();
        other.resetLoveTicks();
        world.sendEntityStatus(this, (byte)18);
        if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }

    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(VARIANT, 0);
        super.initDataTracker(builder);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant().getId());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(AppledogEntity.Variant.byId(nbt.getInt("Variant")));
    }

    public void setVariant(AppledogEntity.Variant variant) {
        this.dataTracker.set(VARIANT, variant.getId());
    }

    public AppledogEntity.Variant getVariant() {
        return AppledogEntity.Variant.byId(this.dataTracker.get(VARIANT));
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (spawnReason == SpawnReason.SPAWN_EGG) {
            Random random = world.getRandom();
            if (entityData instanceof AppledogEntity.AppledogData) {
            } else {
                entityData = new AppledogEntity.AppledogData(Variant.getRandom(random), Variant.getRandom(random));
            }

            this.setVariant(((AppledogEntity.AppledogData)entityData).getRandomVariant(random));

            return super.initialize(world, difficulty, spawnReason, entityData);
        } else {
            return entityData;
        }
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    public static enum Variant implements StringIdentifiable {
        RED_DELICIOUS(0, "red_delicious"),
        GRANNY_SMITH(1, "granny_smith"),
        GOLDEN_DELICIOUS(2, "golden_delicious"),
        MACOUN(3, "macoun"),
        PINK_LADY(4, "pink_lady");

        private static final IntFunction<AppledogEntity.Variant> BY_ID = ValueLists.createIdToValueFunction(AppledogEntity.Variant::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
        private final int id;
        private final String name;

        private Variant(final int id, final String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public String asString() {
            return this.name;
        }

        public static AppledogEntity.Variant byId(int id) {
            return BY_ID.apply(id);
        }

        static AppledogEntity.Variant getRandom(Random random) {
            AppledogEntity.Variant[] variants = Arrays.stream(values()).toArray(Variant[]::new);
            return Util.getRandom(variants, random);
        }
    }

    public static class AppledogData extends PassiveEntity.PassiveData {
        public final AppledogEntity.Variant[] variants;

        public AppledogData(AppledogEntity.Variant... variants) {
            super(false);
            this.variants = variants;
        }

        public AppledogEntity.Variant getRandomVariant(Random random) {
            return this.variants[random.nextInt(this.variants.length)];
        }
    }

}
