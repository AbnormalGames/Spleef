package me.bagel.spleef;

import net.minecraft.util.SharedConstants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Spleef.MOD_ID)
public class Spleef {
	public static final String MOD_ID = "spleef";

	public Spleef() {
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(this::crashClient);
		modBus.addListener(this::setup);

		SpleefConfig.init(ModLoadingContext.get());

		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void setupServer(FMLServerStartedEvent event) {
		if (SpleefConfig.INSTANCE.enabled.get()) {
		}
	}

	@SubscribeEvent
	public void onEvent(RegisterCommandsEvent event) {
		if (SpleefConfig.INSTANCE.enabled.get()) {
		}
	}

	private void setup(FMLCommonSetupEvent event) {
		SharedConstants.developmentMode = true;
	}

	private void crashClient(FMLClientSetupEvent event) {
		throw new UnsupportedOperationException("This mod is not meant to be run on the client, please start this on a dedicated server.");
	}
}
