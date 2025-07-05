package net.appledog.custom;

import net.appledog.Appledog;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class CoirBedBlockEntity extends BlockEntity {
    public CoirBedBlockEntity(BlockPos pos, BlockState state) {
        super(Appledog.COIR_BED_BLOCK_ENTITY, pos, state);
    }
}