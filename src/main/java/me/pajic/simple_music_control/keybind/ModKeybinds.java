package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.SMC;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

	public static final KeyMapping.Category MOD_KEYS = new KeyMapping.Category(SMC.id("keys"));

	public static final KeyMapping NEXT_MUSIC_TRACK = new KeyMapping(
			"key.simple_music_control.next_music_track",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_M,
			MOD_KEYS
	);

	public static final KeyMapping TOGGLE_MUSIC = new KeyMapping(
			"key.simple_music_control.toggle_music",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_UNKNOWN,
			MOD_KEYS
	);

    public static void init() {}
}
