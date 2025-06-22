package net.appledog.custom;

import com.mojang.serialization.MapCodec;
import net.appledog.registry.ADBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LichenGrower;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class ApplecogBlock extends MultifaceGrowthBlock {
    public static final BooleanProperty POWERED = Properties.POWERED;
    public static final BooleanProperty REVERSED = BooleanProperty.of("reversed");
    public ApplecogBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState().with(POWERED, true).with(REVERSED, false).with(getProperty(Direction.NORTH), false).with(getProperty(Direction.EAST), false).with(getProperty(Direction.SOUTH), false).with(getProperty(Direction.WEST), false).with(getProperty(Direction.UP), false).with(getProperty(Direction.DOWN), false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED, REVERSED);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        BlockState state = this.getDefaultState();
        if (ctx.getWorld().getBlockState(pos).isOf(this)) {
            state = ctx.getWorld().getBlockState(pos);
        }
        if (ctx.getWorld().getBlockState(pos.offset(ctx.getSide().getOpposite())).isSideSolidFullSquare(ctx.getWorld(), pos.offset(ctx.getSide().getOpposite()), ctx.getSide())) {
            return state.with(getProperty(ctx.getSide().getOpposite()), true)
                    .with(REVERSED, Math.abs(pos.getX() + pos.getY() + pos.getZ()) % 2 == 1);
        } else {
            return null;
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.isOf(this) && super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).contains(REVERSED)) {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(REVERSED, Math.abs(pos.getX() + pos.getY() + pos.getZ()) % 2 == 1);
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().isOf(ADBlocks.APPLECOG.asItem());
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    protected MapCodec<? extends MultifaceGrowthBlock> getCodec() {
        return null;
    }

    @Override
    public LichenGrower getGrower() {
        return null;
    }
}
