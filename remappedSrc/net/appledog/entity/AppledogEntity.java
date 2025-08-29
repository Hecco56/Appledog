package net.appledog.entity;

import net.appledog.Appledog;
import net.appledog.registry.*;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

public class AppledogEntity extends TamableAnimal {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(AppledogEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLLAR_COLOR = SynchedEntityData.defineId(AppledogEntity.class, EntityDataSerializers.INT);
    public AppledogEntity(EntityType<? extends AppledogEntity> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_POWDER_SNOW, -1.0F);
    }

    public static boolean canSpawn(EntityType<AppledogEntity> appledogEntityEntityType, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        int range = 50;
        return blockPos.getY() > 40 && (blockPos.getX() < -255 + range && blockPos.getX() > -255 - range && blockPos.getZ() > 69 - range && blockPos.getZ() < 69 + range) && random.nextFloat() < 0.5f;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TamableAnimalPanicGoal(1.5F, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0F, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new TemptGoal(this, 1.2, (stack) -> stack.is(Items.APPLE), false));
        this.goalSelector.addGoal(8, new BreedGoal(this, 1.0F));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAppledogAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3f).add(Attributes.MAX_HEALTH, 32.0).add(Attributes.ATTACK_DAMAGE, 128.0).add(Attributes.SCALE, 1);
    }
    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.APPLE);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return ADSounds.APPLEDOG_HURT;
    }

    public Mob createAppleChild(ServerLevel world, AgeableMob entity) {
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
    public void spawnChildFromBreeding(ServerLevel world, Animal other) {
        Mob entity = this.createAppleChild(world, other);
        if (entity != null) {
            entity.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            this.appleBreed(world, (AppledogEntity) other, entity);
            world.addFreshEntityWithPassengers(entity);
        }
    }

    public void appleBreed(ServerLevel world, AppledogEntity other, @Nullable Mob baby) {
        Optional.ofNullable(this.getLoveCause()).or(() -> Optional.ofNullable(other.getLoveCause())).ifPresent((player) -> {
            player.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(player, this, other, (AgeableMob) baby);
        });
        this.setAge(6000);
        other.setAge(6000);
        this.resetLove();
        other.resetLove();
        world.broadcastEntityEvent(this, (byte)18);
        if (world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            world.addFreshEntity(new ExperienceOrb(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }

    }

    private InteractionResult returnItem(ItemStack handStack, Player player, ItemStack stack) {
        this.spawnAtLocation(stack);
        makeSound(SoundEvents.CHICKEN_EGG);
        playSound(SoundEvents.WOLF_AMBIENT, 0.6f, 1.2f);
        handStack.consume(1, player);
        return InteractionResult.SUCCESS;
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (!this.level().isClientSide || this.isBaby() && this.isFood(itemStack)) {
            if (itemStack.is(Items.IRON_INGOT)) {
                return returnItem(itemStack, player, ADBlocks.APPLECOG.asItem().getDefaultInstance());
            }
            if (itemStack.is(ItemTags.LEAVES)) {
                return returnItem(itemStack, player, ADItems.APPLEDOGLLAR.getDefaultInstance().copyWithCount(4));
            }
            if (itemStack.is(ItemTags.STONE_CRAFTING_MATERIALS)) {
                return returnItem(itemStack, player, ADItems.APPLEROCK.getDefaultInstance().copyWithCount(8));
            }
            if (itemStack.is(ItemTags.LOGS)) {
                return returnItem(itemStack, player, ADBlocks.APPLOG.asItem().getDefaultInstance());
            }
            if (itemStack.is(Items.PAINTING)) {
                ItemStack stack = new ItemStack(Items.PAINTING);
                CompoundTag nbt = new CompoundTag();
                nbt.putString("id", "minecraft:painting");
                nbt.putString("variant", "appledog:caninedy");
                stack.set(DataComponents.ENTITY_DATA, CustomData.of(nbt));
                return returnItem(itemStack, player, stack);
            }
            if (this.isTame()) {
                InteractionResult actionResult = super.mobInteract(player, hand);
                if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    itemStack.consume(1, player);
                    FoodProperties foodComponent = itemStack.get(DataComponents.FOOD);
                    float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0F;
                    this.heal(5.0F * f);
                    return InteractionResult.sidedSuccess(this.level().isClientSide());
                } else if (item instanceof DyeItem) {
                    DyeItem dyeItem = (DyeItem)item;
                    if (this.isOwnedBy(player)) {
                        DyeColor dyeColor = dyeItem.getDyeColor();
                        if (dyeColor != this.getCollarColor()) {
                            this.setCollarColor(dyeColor);
                            itemStack.consume(1, player);
                            return InteractionResult.SUCCESS;
                        }

                        return super.mobInteract(player, hand);
                    }
                } else if (!actionResult.consumesAction() && this.isOwnedBy(player)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    return InteractionResult.SUCCESS_NO_ITEM_USED;
                }
                return super.mobInteract(player, hand);
            } else if (itemStack.is(Items.APPLE)) {
                this.tryTame(player);
                return InteractionResult.SUCCESS;
            } else if (itemStack.is(ADItems.APPLESAUCE)) {
                itemStack.consume(1, player);
                this.tame(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
                return InteractionResult.SUCCESS;
            } else {
                return super.mobInteract(player, hand);
            }
        } else {
            boolean bl = this.isOwnedBy(player) || this.isTame() || itemStack.is(Items.BONE) && !this.isTame();
            return bl ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
    }
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }


    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    private void tryTame(Player player) {
        if (this.random.nextInt(20) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }

    }

    @Override
    public void tick() {
        if (random.nextFloat() < 0.05 && onGround()) {
            BlockState state = level().getBlockState(blockPosition());
            if (state.is(Blocks.COMPOSTER)) {
                if (state.getValue(BlockStateProperties.LEVEL_COMPOSTER) < ComposterBlock.MAX_LEVEL) {
                    ComposterBlock.handleFill(level(), blockPosition(), false);
                    level().playLocalSound(blockPosition(), SoundEvents.WOLF_WHINE, SoundSource.BLOCKS, 0.8F, 1.5F, false);
                    level().setBlockAndUpdate(blockPosition(), state.cycle(BlockStateProperties.LEVEL_COMPOSTER));
                    remove(RemovalReason.DISCARDED);
                }
            }
        }
        super.tick();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.getOwnerUUID() == null && source.getEntity() instanceof Player player) {
            if (player.getItemInHand(player.getUsedItemHand()).is(Items.BOWL)) {
                this.spawnAtLocation(new ItemStack(ADItems.APPLESAUCE));
                player.getItemInHand(player.getUsedItemHand()).consume(1, player);
                this.discard();
                makeSound(ADSounds.APPLEDOG_SAUCIFY);
                for (int i = 0; i < 100; i++) {
                    double x = this.getX() + (random.nextFloat() - 0.5);
                    double y = this.getY() + 0.5 + (random.nextFloat() - 0.5);
                    double z = this.getZ() + (random.nextFloat() - 0.5);
                    this.level().addParticle(ADEntities.APPLESAUCE, x, y, z, -(this.getX() - x) / 1.2, -(this.getY() - y) / 2, -(this.getZ() - z) / 1.2);
                }
                for (int i = 0; i < 300; i++) {
                    double x = this.getX() + (random.nextFloat() - 0.5);
                    double y = this.getY() + 0.5 + (random.nextFloat() - 0.5);
                    double z = this.getZ() + (random.nextFloat() - 0.5);
                    this.level().addParticle(ADEntities.APPLESAUCE, x, y, z, -(this.getX() - x) / 6, -(this.getY() - y) / 6, -(this.getZ() - z) / 6);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    double x = this.getX() + (random.nextFloat()-0.5);
                    double y = this.getY() + 0.5 + (random.nextFloat()-0.5);
                    double z = this.getZ() + (random.nextFloat()-0.5);
                    this.level().addParticle(ADEntities.APPLESAUCE, x, y, z,  -(this.getX()-x)/6, -(this.getY()-y)/6, -(this.getZ()-z)/6);
                }
                player.getFoodData().eat(2, 1f);
            }
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.setOrderedToSit(false);
            }
            return super.hurt(source, amount);
        }
    }

    protected SoundEvent getDeathSound() {
        return ADSounds.APPLEDOG_HURT;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(VARIANT, 0);
        builder.define(COLLAR_COLOR, DyeColor.GREEN.getId());
        super.defineSynchedData(builder);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(COLLAR_COLOR));
    }

    private void setCollarColor(DyeColor color) {
        this.entityData.set(COLLAR_COLOR, color.getId());
    }

    private void setDigestion(int digestion) {
        this.entityData.set(COLLAR_COLOR, digestion);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putInt("Variant", this.getVariant().getId());
        nbt.putByte("CollarColor", (byte)this.getCollarColor().getId());
    }

    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setVariant(Variant.byId(nbt.getInt("Variant")));
        if (nbt.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(nbt.getInt("CollarColor")));
        }
    }

    public void setVariant(Variant variant) {
        this.entityData.set(VARIANT, variant.getId());
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(VARIANT));
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData) {
        if (spawnReason == MobSpawnType.SPAWN_EGG) {
            RandomSource random = world.getRandom();
            if (entityData instanceof AppledogData) {
            } else {
                entityData = new AppledogData(Variant.getRandom(random), Variant.getRandom(random));
            }

            Variant variant = ((AppledogData)entityData).getRandomVariant(random);
            this.setVariant(variant);
            DyeColor color = switch (variant) {
            case RED_DELICIOUS, MACOUN -> DyeColor.LIME;
            case GRANNY_SMITH, GOLDEN_DELICIOUS -> DyeColor.RED;
            case PINK_LADY -> DyeColor.GREEN;
            };
            this.setCollarColor(color);
            return super.finalizeSpawn(world, difficulty, spawnReason, entityData);
        } else {

            return entityData;
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel world, AgeableMob entity) {
        Mob child = this.createAppleChild(world, entity);
        if (entity != null) {
            entity.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            world.addFreshEntityWithPassengers(child);
        }
        return (AgeableMob) child;
    }

    public static enum Variant implements StringRepresentable {
        RED_DELICIOUS(0, "red_delicious"),
        GRANNY_SMITH(1, "granny_smith"),
        GOLDEN_DELICIOUS(2, "golden_delicious"),
        MACOUN(3, "macoun"),
        PINK_LADY(4, "pink_lady");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
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

        public String getSerializedName() {
            return this.name;
        }

        public static Variant byId(int id) {
            return BY_ID.apply(id);
        }

        static Variant getRandom(RandomSource random) {
            Variant[] variants = Arrays.stream(values()).toArray(Variant[]::new);
            return Util.getRandom(variants, random);
        }
    }

    public static class AppledogData extends AgeableMobGroupData {
        public final Variant[] variants;

        public AppledogData(Variant... variants) {
            super(false);
            this.variants = variants;
        }

        public Variant getRandomVariant(RandomSource random) {
            return this.variants[random.nextInt(this.variants.length)];
        }
    }

}
