package net.appledog.entity;

import net.appledog.registry.ADEntities;
import net.appledog.registry.ADSounds;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.IntFunction;

public class ApplepupEntity extends ShoulderEntity {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(ApplepupEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(ApplepupEntity.class, EntityDataSerializers.INT);

    public final AnimationState joyAnimationState = new AnimationState();
    private int joyAnimationTimeout = 0;
    private int joyCooldown = RandomSource.create().nextIntBetweenInclusive(100, 1000);

    public ApplepupEntity(EntityType<? extends ApplepupEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new SitOnPlayerShoulderGoal(this));
    }

    public static AttributeSupplier.Builder createApplepupAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 12.0).add(Attributes.ATTACK_DAMAGE, 256.0).add(Attributes.SCALE, 1);
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.WOLF_DEATH;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(VARIANT, 0);
        builder.define(AGE, 0);
        super.defineSynchedData(builder);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getVariant().getId());
        nbt.putInt("Age", entityData.get(AGE));
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setVariant(AppledogEntity.Variant.byId(nbt.getInt("Variant")));
        this.setAge(nbt.getInt("Age"));
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (player.getItemInHand(hand).is(Items.BONE_MEAL)) {
            entityData.set(AGE, entityData.get(AGE) + random.nextIntBetweenInclusive(100, 300));
            for (int i = 0; i < 10; i++) {
                level().addParticle(ParticleTypes.HAPPY_VILLAGER, position().x + (random.nextFloat()-0.5), position().y + (random.nextFloat()-0.5), position().z + (random.nextFloat()-0.5), 0, 0, 0);
            }
            playSound(SoundEvents.BONE_MEAL_USE, 1.0f, 1.0f);
            if (!player.isCreative()) {
                player.getItemInHand(hand).shrink(1);
            }
            return InteractionResult.SUCCESS;
        } else {
            Item item = Items.DIRT;
            if (FabricLoader.getInstance().isModLoaded("bountifulfares")) {
                item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath("bountifulfares", "walnut_mulch"));
            }
            if (this.getAttribute(Attributes.SCALE) != null && player.getItemInHand(hand).is(item)) {
                if (this.getAttributeValue(Attributes.SCALE) < 16) {
                    Objects.requireNonNull(this.getAttribute(Attributes.SCALE))
                            .setBaseValue(this.getAttributeValue(Attributes.SCALE) + 0.1f);
                    playSound(SoundEvents.ROOTED_DIRT_BREAK, 0.5f, 1.0f);
                    playSound(SoundEvents.GENERIC_EAT, 0.6f, 2.0f);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.mobInteract(player, hand);
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

        this.setAirSupply(getMaxAirSupply());
        if (entityData.get(AGE) >= 2400) {
            Level world = this.level();
            if (world instanceof ServerLevel serverWorld) {
                AppledogEntity appledogEntity = ADEntities.APPLEDOG.create(this.level());
                if (appledogEntity != null) {
                    appledogEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
                    appledogEntity.finalizeSpawn(serverWorld, this.level().getCurrentDifficultyAt(appledogEntity.blockPosition()), MobSpawnType.CONVERSION, null);
                    appledogEntity.setNoAi(this.isNoAi());
                    appledogEntity.setVariant(AppledogEntity.Variant.byId(entityData.get(VARIANT)));
                    if (this.hasCustomName()) {
                        appledogEntity.setCustomName(this.getCustomName());
                        appledogEntity.setCustomNameVisible(this.isCustomNameVisible());
                    }

                    appledogEntity.setPersistenceRequired();
                    appledogEntity.fudgePositionAfterSizeChange(this.getDimensions(this.getPose()));
                    serverWorld.addFreshEntityWithPassengers(appledogEntity);
                    this.discard();
                }
            }
        }
        if (this.getAttributeValue(Attributes.SCALE) <= 1) {
        entityData.set(AGE, entityData.get(AGE) + 1);
        }
        super.tick();
    }

    public void setVariant(AppledogEntity.Variant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    public void setAge(int age) {
        this.entityData.set(AGE, age);
    }

    public AppledogEntity.Variant getVariant() {
        return AppledogEntity.Variant.byId(this.entityData.get(VARIANT));
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        if (spawnReason == MobSpawnType.SPAWN_EGG) {
            RandomSource random = world.getRandom();
            if (entityData instanceof AppledogEntity.AppledogData) {
            } else {
                entityData = new AppledogEntity.AppledogData(AppledogEntity.Variant.getRandom(random), AppledogEntity.Variant.getRandom(random));
            }

            this.setVariant(((AppledogEntity.AppledogData)entityData).getRandomVariant(random));

            return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        } else {
            return entityData;
        }
    }



    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        return null;
    }

}
