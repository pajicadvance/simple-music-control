package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.util.JukeboxTracker;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
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
			client.getSoundManager().pauseAllExcept(SoundSource.MUSIC);
		}
		NowPlayingWidget.displayToggleNotification();
	}

    public static void init() {}
}
