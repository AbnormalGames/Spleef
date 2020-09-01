package me.bagel.spleef;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;

public class SpleefLevel {
	public static final Codec<SpleefLevel> CODEC = RecordCodecBuilder.create(
			instance -> instance.group(
					Codec.INT.fieldOf("width").forGetter((SpleefLevel o) -> o.width), 
					BlockState.CODEC.listOf().fieldOf("blockstates").forGetter((SpleefLevel o) -> o.blockStates))
			.apply(instance, SpleefLevel::new));

	private int width;
	private List<BlockState> blockStates;

	public SpleefLevel(int width, List<BlockState> blockStates) {
		this.width = width;
		this.blockStates = blockStates;
	}
	
	public int getWidth() {
		return width;
	}
	
	public List<BlockState> getBlockStates() {
		return blockStates;
	}
}