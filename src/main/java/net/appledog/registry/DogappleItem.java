package net.appledog.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.function.UnaryOperator;

public class DogappleItem extends Item {
    public static final ComponentType<Integer> DOGAPPLE_ANIMATION = register("dogapple_animation", (builder) -> {
        return builder.codec(Codecs.rangedInt(0, 15)).packetCodec(PacketCodecs.VAR_INT);
    });
    private static <T> ComponentType register(String id, UnaryOperator<ComponentType.Builder<Integer>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, id, (builderOperator.apply(ComponentType.builder())).build());
    }
    public DogappleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!itemStack.getComponents().contains(DOGAPPLE_ANIMATION) || itemStack.getComponents().get(DOGAPPLE_ANIMATION) == 0) {
            itemStack.set(DOGAPPLE_ANIMATION, 15);
            return TypedActionResult.pass(itemStack);
        }
        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity user) {
            ItemStack itemStack = user.getStackInHand(user.getActiveHand());
            if (itemStack.getComponents().contains(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) > 0) {
                itemStack.set(DOGAPPLE_ANIMATION, itemStack.get(DOGAPPLE_ANIMATION) - 1);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}