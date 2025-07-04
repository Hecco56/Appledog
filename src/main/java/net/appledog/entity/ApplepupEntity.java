package net.appledog.entity;

import net.appledog.registry.ADEntities;
import net.appledog.registry.ADSounds;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
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
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.ParticleUtil;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.IntProviderType;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

public class ApplepupEntity extends ShoulderEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(ApplepupEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> AGE = DataTracker.registerData(ApplepupEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public final AnimationState joyAnimationState = new AnimationState();
    private int joyAnimationTimeout = 0;
    private int joyCooldown = Random.create().nextBetween(100, 1000);

    public ApplepupEntity(EntityType<? extends ApplepupEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new LookAroundGoal(this));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(3, new SitOnPlayerShoulderGoal(this));
    }

    public static DefaultAttributeContainer.Builder createApplepupAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 256.0).add(EntityAttributes.GENERIC_SCALE, 1);
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
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(hand).isOf(Items.BONE_MEAL)) {
            dataTracker.set(AGE, dataTracker.get(AGE) + random.nextBetween(100, 300));
            for (int i = 0; i < 10; i++) {
                getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, getPos().x + (random.nextFloat()-0.5), getPos().y + (random.nextFloat()-0.5), getPos().z + (random.nextFloat()-0.5), 0, 0, 0);
            }
            playSound(SoundEvents.ITEM_BONE_MEAL_USE, 1.0f, 1.0f);
            if (!player.isCreative()) {
                player.getStackInHand(hand).decrement(1);
            }
            return ActionResult.SUCCESS;
        } else {
            Item item = Items.DIRT;
            if (FabricLoader.getInstance().isModLoaded("bountifulfares")) {
                item = Registries.ITEM.get(Identifier.of("bountifulfares", "walnut_mulch"));
            }
            if (this.getAttributeInstance(EntityAttributes.GENERIC_SCALE) != null && player.getStackInHand(hand).isOf(item)) {
                if (this.getAttributeValue(EntityAttributes.GENERIC_SCALE) < 16) {
                    Objects.requireNonNull(this.getAttributeInstance(EntityAttributes.GENERIC_SCALE))
                            .setBaseValue(this.getAttributeValue(EntityAttributes.GENERIC_SCALE) + 0.1f);
                    playSound(SoundEvents.BLOCK_ROOTED_DIRT_BREAK, 0.5f, 1.0f);
                    playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.6f, 2.0f);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void tick() {
//        if (joyCooldown > 0) {
//            joyCooldown--;
//        }
//        if (this.joyAnimationTimeout == 1 && this.joyAnimationState.isRunning()) {
//            if (random.nextBoolean()) {
//                this.joyAnimationState.start(dataTracker.get(AGE));
//                this.joyAnimationTimeout = 10;
//            } else {
//                this.joyCooldown = Random.create().nextBetween(60, 1000);
//                this.joyAnimationTimeout = 0;
//            }
//        }
//        if (this.joyAnimationTimeout > 0) {
//            this.joyAnimationTimeout--;
//        } else if (this.joyCooldown <= 0) {
//            this.joyAnimationState.start(dataTracker.get(AGE));
//            this.joyAnimationTimeout = 10;
//        }

        this.setAir(getMaxAir());
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
        if (this.getAttributeValue(EntityAttributes.GENERIC_SCALE) <= 1) {
        dataTracker.set(AGE, dataTracker.get(AGE) + 1);
        }
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
            if (entityData instanceof AppledogEntity.AppledogData) {
            } else {
                entityData = new AppledogEntity.AppledogData(AppledogEntity.Variant.getRandom(random), AppledogEntity.Variant.getRandom(random));
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

}
