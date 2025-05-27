package net.appledog.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import static net.appledog.custom.DogappleItem.DOGAPPLE_ANIMATION;

public class CandiedDogappleItem extends Item {
    public CandiedDogappleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.contains(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) == 0) {
            itemStack.set(DOGAPPLE_ANIMATION, 15);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (stack.contains(DOGAPPLE_ANIMATION) && stack.getComponents().get(DOGAPPLE_ANIMATION) == 15) {
            stack.set(DOGAPPLE_ANIMATION, 0);
        }
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        world.playSoundFromEntity(null, user, SoundEvents.ENTITY_WOLF_WHINE, SoundCategory.PLAYERS, 1.0f, 1.3f);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 100;
    }
}
