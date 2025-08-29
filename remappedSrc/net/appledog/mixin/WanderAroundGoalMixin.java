package net.appledog.mixin;

import net.appledog.registry.ADBlocks;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.appledog.custom.ApplecogBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RandomStrollGoal.class)
public class WanderAroundGoalMixin {

    @Shadow @Final protected PathfinderMob mob;

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    private void appledog$disableIfOnApplecog(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.level().getBlockState(this.mob.blockPosition()).is(ADBlocks.APPLECOG) && this.mob.level().getBlockState(this.mob.blockPosition()).getValue(ApplecogBlock.getFaceProperty(Direction.DOWN)) && Math.abs(this.mob.getDeltaMovement().x + this.mob.getDeltaMovement().z) < 0.002 && this.mob.onGround()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
