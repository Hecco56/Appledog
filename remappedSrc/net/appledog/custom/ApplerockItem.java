package net.appledog.custom;

import net.appledog.entity.ApplerockEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.level.Level;

public class ApplerockItem extends Item implements ProjectileItem {
    public ApplerockItem(Item.Properties settings) {
        super(settings);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.EGG_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.WOLF_AMBIENT, SoundSource.NEUTRAL, 0.5F, 1.6f);
        if (!world.isClientSide) {
            ApplerockEntity applerockEntity = new ApplerockEntity(world, user);
            applerockEntity.setItem(itemStack);
            applerockEntity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
            world.addFreshEntity(applerockEntity);
        }

        user.awardStat(Stats.ITEM_USED.get(this));
        itemStack.consume(1, user);
        return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
    }

    public Projectile asProjectile(Level world, Position pos, ItemStack stack, Direction direction) {
        ApplerockEntity applerockEntity = new ApplerockEntity(world, pos.x(), pos.y(), pos.z());
        applerockEntity.setItem(stack);
        return applerockEntity;
    }
}
