package net.appledog.custom;

import net.appledog.Appledog;
import net.appledog.registry.ADItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.function.UnaryOperator;

import static net.appledog.custom.DogappleItem.DOGAPPLE_ANIMATION;

public class SaltedDogappleItem extends Item {
    public SaltedDogappleItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        if (itemStack.has(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) == 0) {
            itemStack.set(DOGAPPLE_ANIMATION, 4);
        }
        return InteractionResultHolder.pass(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (entity instanceof Player user) {
            if (selected && world.random.nextFloat() < 0.01f) {
                world.playSound(user, user.blockPosition(), SoundEvents.WOLF_GROWL, SoundSource.AMBIENT, 0.2f, 1.0f + world.getRandom().nextFloat()/5);
            }
            ItemStack itemStack = user.getItemInHand(user.getUsedItemHand());
            if (selected && itemStack.getComponents().has(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) > 0) {
                int i = itemStack.get(DOGAPPLE_ANIMATION);
                itemStack.set(DOGAPPLE_ANIMATION, i - 1);
                if (i < 2) {
                    world.playSound(user, user.blockPosition(), SoundEvents.FOX_BITE, SoundSource.AMBIENT, 1.0f, 1.0f + world.getRandom().nextFloat()/3);
                    DamageSource damageSource = new DamageSource(
                            world.registryAccess()
                                    .registryOrThrow(Registries.DAMAGE_TYPE)
                                    .getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Appledog.MOD_ID, "salted_dogapple_bite"))));
                    user.hurt(damageSource, 6);
                    if (user.hasInfiniteMaterials()) {
                        user.kill();
                    } else {
                        user.spawnAtLocation(ADItems.SALTED_DOGAPPLE.getDefaultInstance());
                    }
                    user.getInventory().setItem(slot, ItemStack.EMPTY);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
