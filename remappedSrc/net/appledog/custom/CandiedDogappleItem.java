package net.appledog.custom;

import static net.appledog.custom.DogappleItem.DOGAPPLE_ANIMATION;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CandiedDogappleItem extends Item {
    public CandiedDogappleItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (itemStack.has(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) == 0) {
            itemStack.set(DOGAPPLE_ANIMATION, 15);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (stack.has(DOGAPPLE_ANIMATION) && stack.getComponents().get(DOGAPPLE_ANIMATION) == 15) {
            stack.set(DOGAPPLE_ANIMATION, 0);
        }
        super.releaseUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        world.playSound(null, user, SoundEvents.WOLF_WHINE, SoundSource.PLAYERS, 1.0f, 1.3f);
        return super.finishUsingItem(stack, world, user);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 100;
    }
}
