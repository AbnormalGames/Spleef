package me.bagel.spleef;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class SpleefConfig {
    public static final SpleefConfig INSTANCE;
    private static final ForgeConfigSpec SPEC;

    static {
        Pair<SpleefConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SpleefConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

    public ForgeConfigSpec.BooleanValue enabled;

    private SpleefConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Spleef Server Config");
        this.enabled = builder
                .worldRestart()
                .comment("Whether or not Spleef is enabled.")
                .translation("config." + Spleef.MOD_ID + ".enabled")
                .define("enabled", true);
    }

    public static void init(ModLoadingContext context) {
        context.registerConfig(ModConfig.Type.SERVER, SPEC);
    }
}