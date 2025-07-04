package net.appledog.entity;

import net.appledog.Appledog;
import net.appledog.registry.*;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

public class AppledogEntity extends TameableEntity {
    private static final TrackedData<Integer> VARIANT = DataTracker.registerData(AppledogEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> COLLAR_COLOR = DataTracker.registerData(AppledogEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public AppledogEntity(EntityType<? extends AppledogEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, -1.0F);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0F);
    }

    public static boolean canSpawn(EntityType<AppledogEntity> appledogEntityEntityType, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        int range = 50;
        return blockPos.getY() > 40 && (blockPos.getX() < -255 + range && blockPos.getX() > -255 - range && blockPos.getZ() > 69 - range && blockPos.getZ() < 69 + range) && random.nextFloat() < 0.5f;
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new TameableEscapeDangerGoal(1.5F, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.add(2, new SitGoal(this));
        this.goalSelector.add(6, new FollowOwnerGoal(this, 1.0F, 10.0F, 2.0F));
        this.goalSelector.add(7, new TemptGoal(this, 1.2, (stack) -> stack.isOf(Items.APPLE), false));
        this.goalSelector.add(8, new AnimalMateGoal(this, 1.0F));
        this.goalSelector.add(9, new WanderAroundFarGoal(this, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(10, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAppledogAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 128.0).add(EntityAttributes.GENERIC_SCALE, 1);
    }
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.APPLE);
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return ADSounds.APPLEDOG_HURT;
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

    private ActionResult returnItem(ItemStack handStack, PlayerEntity player, ItemStack stack) {
        this.dropStack(stack);
        playSound(SoundEvents.ENTITY_CHICKEN_EGG);
        playSound(SoundEvents.ENTITY_WOLF_AMBIENT, 0.6f, 1.2f);
        handStack.decrementUnlessCreative(1, player);
        return ActionResult.SUCCESS;
    }

    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        if (!this.getWorld().isClient || this.isBaby() && this.isBreedingItem(itemStack)) {
            if (itemStack.isOf(Items.IRON_INGOT)) {
                return returnItem(itemStack, player, ADBlocks.APPLECOG.asItem().getDefaultStack());
            }
            if (itemStack.isIn(ItemTags.LEAVES)) {
                return returnItem(itemStack, player, ADItems.APPLEDOGLLAR.getDefaultStack().copyWithCount(4));
            }
            if (itemStack.isIn(ItemTags.STONE_CRAFTING_MATERIALS)) {
                return returnItem(itemStack, player, ADItems.APPLEROCK.getDefaultStack().copyWithCount(8));
            }
            if (itemStack.isIn(ItemTags.LOGS)) {
                return returnItem(itemStack, player, ADBlocks.APPLOG.asItem().getDefaultStack());
            }
            if (itemStack.isOf(Items.PAINTING)) {
                ItemStack stack = new ItemStack(Items.PAINTING);
                NbtCompound nbt = new NbtCompound();
                nbt.putString("id", "minecraft:painting");
                nbt.putString("variant", "appledog:caninedy");
                stack.set(DataComponentTypes.ENTITY_DATA, NbtComponent.of(nbt));
                return returnItem(itemStack, player, stack);
            }
            if (this.isTamed()) {
                ActionResult actionResult = super.interactMob(player, hand);
                if (this.isBreedingItem(itemStack) && this.getHealth() < this.getMaxHealth()) {
                    itemStack.decrementUnlessCreative(1, player);
                    FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
                    float f = foodComponent != null ? (float)foodComponent.nutrition() : 1.0F;
                    this.heal(5.0F * f);
                    return ActionResult.success(this.getWorld().isClient());
                } else if (item instanceof DyeItem) {
                    DyeItem dyeItem = (DyeItem)item;
                    if (this.isOwner(player)) {
                        DyeColor dyeColor = dyeItem.getColor();
                        if (dyeColor != this.getCollarColor()) {
                            this.setCollarColor(dyeColor);
                            itemStack.decrementUnlessCreative(1, player);
                            return ActionResult.SUCCESS;
                        }

                        return super.interactMob(player, hand);
                    }
                } else if (!actionResult.isAccepted() && this.isOwner(player)) {
                    this.setSitting(!this.isSitting());
                    this.jumping = false;
                    this.navigation.stop();
                    return ActionResult.SUCCESS_NO_ITEM_USED;
                }
                return super.interactMob(player, hand);
            } else if (itemStack.isOf(Items.APPLE)) {
                this.tryTame(player);
                return ActionResult.SUCCESS;
            } else if (itemStack.isOf(ADItems.APPLESAUCE)) {
                itemStack.decrementUnlessCreative(1, player);
                this.setOwner(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setSitting(true);
                this.getWorld().sendEntityStatus(this, (byte)7);
                return ActionResult.SUCCESS;
            } else {
                return super.interactMob(player, hand);
            }
        } else {
            boolean bl = this.isOwner(player) || this.isTamed() || itemStack.isOf(Items.BONE) && !this.isTamed();
            return bl ? ActionResult.CONSUME : ActionResult.PASS;
        }
    }
    public int getMaxLookPitchChange() {
        return this.isInSittingPose() ? 20 : super.getMaxLookPitchChange();
    }


    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    private void tryTame(PlayerEntity player) {
        if (this.random.nextInt(20) == 0) {
            this.setOwner(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setSitting(true);
            this.getWorld().sendEntityStatus(this, (byte)7);
        } else {
            this.getWorld().sendEntityStatus(this, (byte)6);
        }

    }

    @Override
    public void tick() {
        if (random.nextFloat() < 0.05 && isOnGround()) {
            BlockState state = getWorld().getBlockState(getBlockPos());
            if (state.isOf(Blocks.COMPOSTER)) {
                if (state.get(Properties.LEVEL_8) < ComposterBlock.MAX_LEVEL) {
                    ComposterBlock.playEffects(getWorld(), getBlockPos(), false);
                    getWorld().playSoundAtBlockCenter(getBlockPos(), SoundEvents.ENTITY_WOLF_WHINE, SoundCategory.BLOCKS, 0.8F, 1.5F, false);
                    getWorld().setBlockState(getBlockPos(), state.cycle(Properties.LEVEL_8));
                    remove(RemovalReason.DISCARDED);
                }
            }
        }
        super.tick();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getOwnerUuid() == null && source.getAttacker() instanceof PlayerEntity player) {
            if (player.getStackInHand(player.getActiveHand()).isOf(Items.BOWL)) {
                this.dropStack(new ItemStack(ADItems.APPLESAUCE));
                player.getStackInHand(player.getActiveHand()).decrementUnlessCreative(1, player);
                this.discard();
                playSound(ADSounds.APPLEDOG_SAUCIFY);
                for (int i = 0; i < 100; i++) {
                    double x = this.getX() + (random.nextFloat() - 0.5);
                    double y = this.getY() + 0.5 + (random.nextFloat() - 0.5);
                    double z = this.getZ() + (random.nextFloat() - 0.5);
                    this.getWorld().addParticle(ADEntities.APPLESAUCE, x, y, z, -(this.getX() - x) / 1.2, -(this.getY() - y) / 2, -(this.getZ() - z) / 1.2);
                }
                for (int i = 0; i < 300; i++) {
                    double x = this.getX() + (random.nextFloat() - 0.5);
                    double y = this.getY() + 0.5 + (random.nextFloat() - 0.5);
                    double z = this.getZ() + (random.nextFloat() - 0.5);
                    this.getWorld().addParticle(ADEntities.APPLESAUCE, x, y, z, -(this.getX() - x) / 6, -(this.getY() - y) / 6, -(this.getZ() - z) / 6);
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    double x = this.getX() + (random.nextFloat()-0.5);
                    double y = this.getY() + 0.5 + (random.nextFloat()-0.5);
                    double z = this.getZ() + (random.nextFloat()-0.5);
                    this.getWorld().addParticle(ADEntities.APPLESAUCE, x, y, z,  -(this.getX()-x)/6, -(this.getY()-y)/6, -(this.getZ()-z)/6);
                }
                player.getHungerManager().add(2, 1f);
            }
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (!this.getWorld().isClient) {
                this.setSitting(false);
            }
            return super.damage(source, amount);
        }
    }

    protected SoundEvent getDeathSound() {
        return ADSounds.APPLEDOG_HURT;
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(VARIANT, 0);
        builder.add(COLLAR_COLOR, DyeColor.GREEN.getId());
        super.initDataTracker(builder);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.dataTracker.get(COLLAR_COLOR));
    }

    private void setCollarColor(DyeColor color) {
        this.dataTracker.set(COLLAR_COLOR, color.getId());
    }

    private void setDigestion(int digestion) {
        this.dataTracker.set(COLLAR_COLOR, digestion);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getVariant().getId());
        nbt.putByte("CollarColor", (byte)this.getCollarColor().getId());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVariant(Variant.byId(nbt.getInt("Variant")));
        if (nbt.contains("CollarColor", 99)) {
            this.setCollarColor(DyeColor.byId(nbt.getInt("CollarColor")));
        }
    }

    public void setVariant(Variant variant) {
        this.dataTracker.set(VARIANT, variant.getId());
    }

    public Variant getVariant() {
        return Variant.byId(this.dataTracker.get(VARIANT));
    }

    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        if (spawnReason == SpawnReason.SPAWN_EGG) {
            Random random = world.getRandom();
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
            return super.initialize(world, difficulty, spawnReason, entityData);
        } else {

            return entityData;
        }
    }

    @Override
    public @Nullable PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MobEntity child = this.createAppleChild(world, entity);
        if (entity != null) {
            entity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            world.spawnEntityAndPassengers(child);
        }
        return (PassiveEntity) child;
    }

    public static enum Variant implements StringIdentifiable {
        RED_DELICIOUS(0, "red_delicious"),
        GRANNY_SMITH(1, "granny_smith"),
        GOLDEN_DELICIOUS(2, "golden_delicious"),
        MACOUN(3, "macoun"),
        PINK_LADY(4, "pink_lady");

        private static final IntFunction<Variant> BY_ID = ValueLists.createIdToValueFunction(Variant::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
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

        public static Variant byId(int id) {
            return BY_ID.apply(id);
        }

        static Variant getRandom(Random random) {
            Variant[] variants = Arrays.stream(values()).toArray(Variant[]::new);
            return Util.getRandom(variants, random);
        }
    }

    public static class AppledogData extends PassiveData {
        public final Variant[] variants;

        public AppledogData(Variant... variants) {
            super(false);
            this.variants = variants;
        }

        public Variant getRandomVariant(Random random) {
            return this.variants[random.nextInt(this.variants.length)];
        }
    }

}
