package net.appledog.custom;

import java.util.function.UnaryOperator;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DogappleItem extends Item {
    public static final DataComponentType<Integer> DOGAPPLE_ANIMATION = register("dogapple_animation", (builder) -> builder.persistent(ExtraCodecs.intRange(0, 15)).networkSynchronized(ByteBufCodecs.VAR_INT));
    private static DataComponentType<Integer> register(String id, UnaryOperator<DataComponentType.Builder<Integer>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, (builderOperator.apply(DataComponentType.builder())).build());
    }
    public DogappleItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (itemStack.has(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) == 0) {
            itemStack.set(DOGAPPLE_ANIMATION, 15);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (entity instanceof Player user) {
            ItemStack itemStack = user.getItemInHand(user.getUsedItemHand());
            if (selected && itemStack.getComponents().has(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) > 0) {
                itemStack.set(DOGAPPLE_ANIMATION, itemStack.get(DOGAPPLE_ANIMATION) - 1);
                if (world.isClientSide() && itemStack.get(DOGAPPLE_ANIMATION) == 12) {
                    world.playSound(user, user.blockPosition(), SoundEvents.WOLF_AMBIENT, SoundSource.AMBIENT, 1.0f, 1.0f + world.getRandom().nextFloat()/3);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

}
