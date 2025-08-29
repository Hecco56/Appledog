package net.appledog.custom;

import com.mojang.serialization.MapCodec;
import net.appledog.entity.ApplepupEntity;
import net.appledog.registry.ADBlocks;
import net.appledog.registry.ADEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ApplepupBlock extends FallingBlock {
    public ApplepupBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.above(), Direction.DOWN) && !world.isWaterAt(pos)
                || world.getBlockState(pos.above()).is(ADBlocks.APPLEAVES) && !world.isWaterAt(pos);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.1f) {
            world.scheduleTick(pos, this, random.nextIntBetweenInclusive(20, 200));
        }
        super.randomTick(state, world, pos, random);
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (isFree(state) && pos.getY() >= world.getMinBuildHeight()) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, pos, state);
            this.falling(fallingBlockEntity);
        }
    }

    @Override
    public void onBrokenAfterFall(Level world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        world.addFreshEntity(new ApplepupEntity(ADEntities.APPLEPUP, world));
        fallingBlockEntity.discard();
    }
}
