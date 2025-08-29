package net.appledog.mixin;

import net.appledog.registry.ADBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.appledog.custom.ApplecogBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void appledog$spinOnApplecog(CallbackInfo ci) {
        if (this.level().getBlockState(this.blockPosition()).is(ADBlocks.APPLECOG) && this.level().getBlockState(this.blockPosition()).getValue(ApplecogBlock.getFaceProperty(Direction.DOWN)) && Math.abs(this.getDeltaMovement().x + this.getDeltaMovement().z) < 0.002 && this.onGround()) {
            this.setYRot(this.getYRot() + (5 * (this.level().getBlockState(this.blockPosition()).getValue(ApplecogBlock.REVERSED) ? -1 : 1)));
            this.setYBodyRot(this.getYRot());
            this.setYHeadRot(this.getYRot());
        }
    }
}
