package me.bagel.spleef.game;

import java.util.List;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class SpleefGame {
	public static final Codec<SpleefGame> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
            		SpleefLevel.CODEC.listOf().fieldOf("levels").forGetter((SpleefGame o) -> o.levels))
            .apply(instance, SpleefGame::new));

	private List<SpleefLevel> levels;

	public SpleefGame(List<SpleefLevel> levels) {
		this.levels = levels;
	}
	
	public List<SpleefLevel> getLevels() {
		return levels;
	}
}