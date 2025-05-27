package net.appledog.custom;

import net.appledog.Appledog;
import net.appledog.registry.ADItems;
import net.minecraft.component.ComponentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

import java.util.function.UnaryOperator;

import static net.appledog.custom.DogappleItem.DOGAPPLE_ANIMATION;

public class SaltedDogappleItem extends Item {
    public SaltedDogappleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.contains(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) == 0) {
            itemStack.set(DOGAPPLE_ANIMATION, 4);
        }
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity user) {
            if (selected && world.random.nextFloat() < 0.01f) {
                world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.AMBIENT, 0.2f, 1.0f + world.getRandom().nextFloat()/5);
            }
            ItemStack itemStack = user.getStackInHand(user.getActiveHand());
            if (selected && itemStack.getComponents().contains(DOGAPPLE_ANIMATION) && itemStack.getComponents().get(DOGAPPLE_ANIMATION) > 0) {
                int i = itemStack.get(DOGAPPLE_ANIMATION);
                itemStack.set(DOGAPPLE_ANIMATION, i - 1);
                if (i < 2) {
                    user.dropStack(ADItems.SALTED_DOGAPPLE.getDefaultStack());
                    world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_FOX_BITE, SoundCategory.AMBIENT, 1.0f, 1.0f + world.getRandom().nextFloat()/3);
                    DamageSource damageSource = new DamageSource(
                            world.getRegistryManager()
                                    .get(RegistryKeys.DAMAGE_TYPE)
                                    .entryOf(RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Appledog.MOD_ID, "salted_dogapple_bite"))));
                    user.damage(damageSource, 6);
                    if (user.isInCreativeMode()) {
                        user.kill();
                    }
                    user.getInventory().setStack(slot, ItemStack.EMPTY);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
