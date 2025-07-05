package net.appledog.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class DogappleItem extends Item {
    public DogappleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getNbt() == null || itemStack.getNbt().getInt("dogapple_animation") == 0) {
            itemStack.getOrCreateNbt().putInt("dogapple_animation", 15);
            return TypedActionResult.consume(itemStack);
        } else {
            return TypedActionResult.pass(itemStack);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity user) {
            ItemStack itemStack = user.getStackInHand(user.getActiveHand());
            if (itemStack.getNbt() != null && itemStack.getNbt().contains("dogapple_animation") && itemStack.getNbt().getInt("dogapple_animation") > 0) {
                itemStack.getNbt().putInt("dogapple_animation", itemStack.getNbt().getInt("dogapple_animation") - 1);
                if (world.isClient() && itemStack.getNbt().getInt("dogapple_animation") == 12) {
                    world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_WOLF_AMBIENT, SoundCategory.AMBIENT, 1.0f, 1.0f + world.getRandom().nextFloat()/3);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
