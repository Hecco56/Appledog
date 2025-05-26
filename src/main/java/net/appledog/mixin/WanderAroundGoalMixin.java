package net.appledog.mixin;

import net.appledog.registry.ADBlocks;
import net.appledog.registry.ApplecogBlock;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WanderAroundGoal.class)
public class WanderAroundGoalMixin {

    @Shadow @Final protected PathAwareEntity mob;

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    private void appledog$disableIfOnApplecog(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.getWorld().getBlockState(this.mob.getBlockPos()).isOf(ADBlocks.APPLECOG) && this.mob.getWorld().getBlockState(this.mob.getBlockPos()).get(ApplecogBlock.getProperty(Direction.DOWN)) && Math.abs(this.mob.getVelocity().x + this.mob.getVelocity().z) < 0.002 && this.mob.isOnGround()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
