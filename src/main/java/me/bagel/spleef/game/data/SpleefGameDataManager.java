package me.bagel.spleef.game.data;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;

import me.bagel.spleef.Spleef;
import me.bagel.spleef.game.SpleefGame;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;


/**
 * Handles all the Data Driven Endimation internals.
 * @author SmellyModder (Luke Tonon)
 */
public final class SpleefGameDataManager extends JsonReloadListener {
	private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(SpleefGame.class, new SpleefGameDeserializer()).create();
	public static final Map<ResourceLocation, SpleefGame> LEVELS = Maps.newHashMap();
	
	public SpleefGameDataManager() {
		super(GSON, "games/spleef");
	}
	
	public static class SpleefGameDeserializer implements JsonDeserializer<SpleefGame> {
		@Override
		public SpleefGame deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			DataResult<Pair<SpleefGame, JsonElement>> decode = SpleefGame.CODEC.decode(JsonOps.INSTANCE, json.getAsJsonObject());
			
			Optional<Pair<SpleefGame, JsonElement>> result = decode.result();
			if (result.isPresent()) {
				return result.get().getFirst();
			}

			decode.error().ifPresent(error -> Spleef.LOGGER.error("Failed to decode Spleef game: {}", error.toString()));
			return null;
		}
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> resourceMap, IResourceManager resourceManagerIn, IProfiler profilerIn) {
		for (Map.Entry<ResourceLocation, JsonElement> entry : resourceMap.entrySet()) {
			ResourceLocation resourcelocation = entry.getKey();
			if (resourcelocation.getPath().startsWith("_")) continue;
			
			try {
				SpleefGame instructions = GSON.fromJson(entry.getValue(), SpleefGame.class);
				LEVELS.put(resourcelocation, instructions);
			} catch (IllegalArgumentException | JsonParseException jsonparseexception) {
				Spleef.LOGGER.error("Parsing error loading Spleef Game {}", resourcelocation, jsonparseexception);
			}
		}
		Spleef.LOGGER.info("Spleef has loaded {} Games", LEVELS.size());
	}
}