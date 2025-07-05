package net.appledog.custom;

import net.appledog.Appledog;
import net.appledog.registry.ADItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SaltedDogappleItem extends Item {
    public SaltedDogappleItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getNbt() != null && itemStack.getNbt().contains("dogapple_animation")) {
            if (itemStack.getNbt().getInt("dogapple_animation") == 0) {
                itemStack.getOrCreateNbt().putInt("dogapple_animation", 4);
            }
        }
        return TypedActionResult.pass(itemStack);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity user) {
            if (selected && world.random.nextFloat() < 0.01f) {
                world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_WOLF_GROWL, SoundCategory.AMBIENT, 0.2f, 1.0f + world.getRandom().nextFloat() / 5);
            }
            ItemStack itemStack = user.getStackInHand(user.getActiveHand());
            if (itemStack.getNbt() != null && selected && itemStack.getNbt().contains("dogapple_animation")) {
                if (itemStack.getNbt().getInt("dogapple_animation") > 0) {
                    int i = itemStack.getNbt().getInt("dogapple_animation");
                    itemStack.getNbt().putInt("dogapple_animation", i - 1);
                    if (i < 2) {
                        world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_FOX_BITE, SoundCategory.AMBIENT, 1.0f, 1.0f + world.getRandom().nextFloat() / 3);
                        DamageSource damageSource = new DamageSource(
                                world.getRegistryManager()
                                        .get(RegistryKeys.DAMAGE_TYPE)
                                        .entryOf(RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Appledog.MOD_ID, "salted_dogapple_bite"))));
                        user.damage(damageSource, 6);
                        if (user.isCreative()) {
                            user.kill();
                        } else {
                            user.dropStack(ADItems.SALTED_DOGAPPLE.getDefaultStack());
                        }
                        user.getInventory().setStack(slot, ItemStack.EMPTY);
                    }
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
