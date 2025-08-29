package net.appledog.custom;

import com.mojang.serialization.MapCodec;
import net.appledog.registry.ADBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class ApplecogBlock extends MultifaceBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty REVERSED = BooleanProperty.create("reversed");
    public ApplecogBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.getStateDefinition().any().setValue(POWERED, true).setValue(REVERSED, false).setValue(getFaceProperty(Direction.NORTH), false).setValue(getFaceProperty(Direction.EAST), false).setValue(getFaceProperty(Direction.SOUTH), false).setValue(getFaceProperty(Direction.WEST), false).setValue(getFaceProperty(Direction.UP), false).setValue(getFaceProperty(Direction.DOWN), false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(POWERED, REVERSED);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getClickedPos();
        BlockState state = this.defaultBlockState();
        if (ctx.getLevel().getBlockState(pos).is(this)) {
            state = ctx.getLevel().getBlockState(pos);
        }
        if (ctx.getLevel().getBlockState(pos.relative(ctx.getClickedFace().getOpposite())).isFaceSturdy(ctx.getLevel(), pos.relative(ctx.getClickedFace().getOpposite()), ctx.getClickedFace())) {
            return state.setValue(getFaceProperty(ctx.getClickedFace().getOpposite()), true)
                    .setValue(REVERSED, Math.abs(pos.getX() + pos.getY() + pos.getZ()) % 2 == 1);
        } else {
            return null;
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.is(this) && super.updateShape(state, direction, neighborState, world, pos, neighborPos).hasProperty(REVERSED)) {
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos).setValue(REVERSED, Math.abs(pos.getX() + pos.getY() + pos.getZ()) % 2 == 1);
        } else {
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        return context.getItemInHand().is(ADBlocks.APPLECOG.asItem());
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }

    @Override
    protected MapCodec<? extends MultifaceBlock> codec() {
        return null;
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return null;
    }
}
