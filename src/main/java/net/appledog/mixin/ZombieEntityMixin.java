package net.appledog.mixin;

import net.appledog.entity.ApplepupEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initCustomGoals", at = @At("TAIL"))
    private void appledog$applepupTarget(CallbackInfo ci) {
        this.targetSelector.add(6, new ActiveTargetGoal<>(this, ApplepupEntity.class, true));
    }
}
