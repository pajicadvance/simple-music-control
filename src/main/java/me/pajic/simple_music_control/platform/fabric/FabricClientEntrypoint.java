package me.pajic.simple_music_control.platform.fabric;

//? fabric {

import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import me.pajic.simple_music_control.util.ModUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

@SuppressWarnings("unused")
public class FabricClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		SMC.onInitializeClient();
		initKeybinds();
		initOnClientTick();
	}

	private static void initKeybinds() {
		ModKeybinds.init();
		KeyMapping.Category.register(SMC.id("keys"));
		KeyBindingHelper.registerKeyBinding(ModKeybinds.NEXT_MUSIC_TRACK);
		KeyBindingHelper.registerKeyBinding(ModKeybinds.TOGGLE_MUSIC);
	}

	private static void initOnClientTick() {
		ClientTickEvents.END_CLIENT_TICK.register(ModUtil::onClientTick);
	}
}
//?}
