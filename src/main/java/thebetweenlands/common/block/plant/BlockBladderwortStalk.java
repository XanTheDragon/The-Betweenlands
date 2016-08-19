package thebetweenlands.common.block.plant;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import thebetweenlands.common.registries.BlockRegistry;

public class BlockBladderwortStalk extends BlockGenericStackablePlantUnderwater {
	public BlockBladderwortStalk(Fluid fluid, Material materialIn) {
		super(fluid, materialIn);
	}

	@Override
	protected boolean canGrow(World world, BlockPos pos, IBlockState state, int height) {
		return world.getBlockState(pos.up()) != this && 
				(world.getBlockState(pos.up()).getMaterial() == Material.WATER || (world.getBlockState(pos).getMaterial() == Material.WATER && world.isAirBlock(pos.up()))) 
				&& (this.maxHeight == -1 || height < this.maxHeight);
	}

	@Override
	protected void growUp(World world, BlockPos pos) {
		if(!world.getBlockState(pos.up()).getMaterial().isLiquid()) {
			world.setBlockState(pos.up(), BlockRegistry.BLADDERWORT_FLOWER.getDefaultState());
		} else {
			world.setBlockState(pos.up(), this.getDefaultState());
		}
	}
}
