package me.bagel.spleef.generation;

import net.minecraft.command.arguments.BlockStateInput;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class MapGenerator {
	
	public static void makeCylinder(ServerWorld world, BlockPos pos, BlockStateInput state, double width, int height) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		for(int j = 0; j < width; ++j) {
			for(int k = 0; k < width; ++k) {
				for(int l = 0; l < height; ++l) {
					world.setBlockState(new BlockPos(x+j, y+l, z+k), state.getState());
				}
			}
		}
	}
}
