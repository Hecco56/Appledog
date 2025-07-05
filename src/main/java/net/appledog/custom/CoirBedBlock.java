package net.appledog.custom;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CoirBedBlock extends BedBlock {
    public CoirBedBlock(Settings settings) {
        super(DyeColor.BROWN, settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CoirBedBlockEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {        BlockState state1 = state;
        BlockPos pos1 = pos;
        if (world.isClient) {
            return ActionResult.CONSUME;
        } else {
            if (state.get(PART) != BedPart.HEAD) {
                pos = pos.offset(state.get(FACING));
                state = world.getBlockState(pos);
                if (!state.isOf(this)) {
                    return ActionResult.CONSUME;
                }
            }

            if (!isBedWorking(world)) {
                world.removeBlock(pos, false);
                BlockPos blockPos = pos.offset(state.get(FACING).getOpposite());
                if (world.getBlockState(blockPos).isOf(this)) {
                    world.removeBlock(blockPos, false);
                }

                Vec3d vec3d = pos.toCenterPos();
                world.createExplosion(null, world.getDamageSources().badRespawnPoint(vec3d), null, vec3d, 5.0F, true, World.ExplosionSourceType.BLOCK);
                return ActionResult.SUCCESS;
            } else if (state.get(OCCUPIED)) {
                return super.onUse(state1, world, pos1, player, hand, hit);
            } else {
                player.trySleep(pos).ifLeft((reason) -> {
                    if (reason.getMessage() != null) {
                        player.sendMessage(reason.getMessage(), true);
                    }

                });
                return ActionResult.SUCCESS;
            }
        }
    }
}