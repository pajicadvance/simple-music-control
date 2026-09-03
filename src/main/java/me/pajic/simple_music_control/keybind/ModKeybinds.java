package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.util.JukeboxTracker;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

//? >=26.1 {
import me.pajic.simple_music_control.SMC;
import net.minecraft.sounds.SoundSource;
//?}

//? <26.3-pre-1 {
import org.lwjgl.glfw.GLFW;
//?} else {
/*import org.lwjgl.sdl.SDLKeycode;
*///?}

public class ModKeybinds {

    //? >=26.1
	public static final KeyMapping.Category MOD_KEYS = new KeyMapping.Category(SMC.id("keys"));

	public static final KeyMapping NEXT_MUSIC_TRACK = new KeyMapping(
			"key.simple_music_control.next_music_track",
            //~ if <26.3-pre-1 'KEYBOARD' -> 'KEYSYM'
			InputConstants.Type.KEYSYM,
            //~ if <26.3-pre-1 'SDLKeycode.SDLK_' -> 'GLFW.GLFW_KEY_'
            GLFW.GLFW_KEY_M,
            //~ if <26.1 'MOD_KEYS' -> '"key.category.simple_music_control.keys"'
			MOD_KEYS
	);

	public static final KeyMapping TOGGLE_MUSIC = new KeyMapping(
			"key.simple_music_control.toggle_music",
            //~ if <26.3-pre-1 'KEYBOARD' -> 'KEYSYM'
			InputConstants.Type.KEYSYM,
            //~ if <26.3-pre-1 'SDLKeycode.SDLK_' -> 'GLFW.GLFW_KEY_'
            GLFW.GLFW_KEY_UNKNOWN,
            //~ if <26.1 'MOD_KEYS' -> '"key.category.simple_music_control.keys"'
			MOD_KEYS
	);

	public static void onNextMusicTrack(Minecraft client) {
		if (JukeboxTracker.noJukeboxesInRange()) {
			client.getMusicManager().stopPlaying();
			if (client.getSituationalMusic() != null) {
				client.getMusicManager().startPlaying(client.getSituationalMusic());
			}
		}
	}

	public static void onToggleMusic(Minecraft client) {
		if (ModUtil.globalPause) {
			ModUtil.globalPause = false;
			client.getSoundManager().resume();
		} else {
			ModUtil.globalPause = true;
            //~ if <26.1 'pauseAllExcept(SoundSource.MUSIC)' -> 'pause()'
			client.getSoundManager().pauseAllExcept(SoundSource.MUSIC);
		}
		NowPlayingWidget.displayToggleNotification();
	}
}
