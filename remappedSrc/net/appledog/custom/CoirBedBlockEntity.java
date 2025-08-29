package net.appledog.custom;

import net.appledog.Appledog;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CoirBedBlockEntity extends BlockEntity {
    public CoirBedBlockEntity(BlockPos pos, BlockState state) {
        super(Appledog.COIR_BED_BLOCK_ENTITY, pos, state);
    }
}
