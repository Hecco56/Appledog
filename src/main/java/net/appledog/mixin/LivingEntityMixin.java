package net.appledog.mixin;

import net.appledog.registry.ADBlocks;
import net.appledog.custom.ApplecogBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void appledog$spinOnApplecog(CallbackInfo ci) {
        if (this.getWorld().getBlockState(this.getBlockPos()).isOf(ADBlocks.APPLECOG) && this.getWorld().getBlockState(this.getBlockPos()).get(ApplecogBlock.getProperty(Direction.DOWN)) && Math.abs(this.getVelocity().x + this.getVelocity().z) < 0.002 && this.isOnGround()) {
            this.setYaw(this.getYaw() + (5 * (this.getWorld().getBlockState(this.getBlockPos()).get(ApplecogBlock.REVERSED) ? -1 : 1)));
            this.setBodyYaw(this.getYaw());
            this.setHeadYaw(this.getYaw());
        }
    }
}
