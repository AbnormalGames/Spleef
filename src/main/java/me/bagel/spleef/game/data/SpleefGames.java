package me.bagel.spleef.game.data;

import java.util.ArrayList;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;

import me.bagel.spleef.game.SpleefGame;
import me.bagel.spleef.game.SpleefLevel;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;

/**
 * @author SmellyModder (Luke Tonon)
 */
public final class SpleefGames {
	public static final InstructionRegistry REGISTRY = new InstructionRegistry();
	
	static {
		ArrayList<BlockState> list = new ArrayList<>();
		list.add(Blocks.COARSE_DIRT.getDefaultState());
		register("default", new SpleefGame(ImmutableList.of(new SpleefLevel(5, list))));
	}
	
	/**
	 * Use this method to register an {@link EndimationInstruction} during the initialization of your mod.
	 * @param name - The registry name for this instruction.
	 * @param instruction - The instruction to register.
	 */
	public static synchronized <E extends SpleefGame> void register(String name, E instruction) {
		REGISTRY.register(name, instruction);
	}

	public static class InstructionRegistry implements Codec<SpleefGame> {
		private final Lifecycle lifecycle;
		private final BiMap<String, SpleefGame> map = HashBiMap.create();
		
		InstructionRegistry() {
			this.lifecycle = Lifecycle.stable();
		}
		
		private <E extends SpleefGame> void register(String instructionName, E instruction) {
			this.map.put(instructionName, instruction);
		}

		@Override
		public <T> DataResult<T> encode(SpleefGame input, DynamicOps<T> ops, T prefix) {
			return ops.mergeToPrimitive(prefix, ops.createString(this.map.inverse().get(input))).setLifecycle(this.lifecycle);
		}

		@Override
		public <T> DataResult<Pair<SpleefGame, T>> decode(DynamicOps<T> ops, T input) {
			return Codec.STRING.decode(ops, input).addLifecycle(this.lifecycle).flatMap(pair -> {
				String name = pair.getFirst();
				if (!this.map.containsKey(name)) {
					return DataResult.error("Unknown Spleef Game: " + name);
				}
				return DataResult.success(pair.mapFirst(string -> this.map.get(string)), this.lifecycle);
			});
		}
	}
}