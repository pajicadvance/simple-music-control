package me.pajic.simple_music_control.platform.fabric;

//? fabric {

import me.pajic.simple_music_control.SMC;
import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import me.pajic.simple_music_control.util.ModUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.KeyMapping;

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		SMC.onInitialize();
		initKeybinds();
		initHudLayers();
		initOnClientTick();
	}

	private static void initKeybinds() {
		ModKeybinds.init();
		KeyMapping.Category.register(SMC.id("keys"));
		KeyMappingHelper.registerKeyMapping(ModKeybinds.NEXT_MUSIC_TRACK);
		KeyMappingHelper.registerKeyMapping(ModKeybinds.TOGGLE_MUSIC);
	}

	private static void initHudLayers() {
		HudElementRegistry.attachElementAfter(
				VanillaHudElements.OVERLAY_MESSAGE,
				SMC.id("info_overlay"),
				NowPlayingWidget::render
		);
	}

	private static void initOnClientTick() {
		ClientTickEvents.END_CLIENT_TICK.register(ModUtil::onClientTick);
	}
}
//?}
