package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.mixin.client.SoundManagerAccessor;
import me.pajic.simple_music_control.util.JukeboxTracker;
import me.pajic.simple_music_control.util.ModUtil;
import me.pajic.simple_music_control.util.MusicPauseControl;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

//? >=1.21.9
import me.pajic.simple_music_control.SMC;

public class ModKeybinds {

    //? >=1.21.9
	public static final KeyMapping.Category MOD_KEYS = new KeyMapping.Category(SMC.id("keys"));

	public static final KeyMapping NEXT_MUSIC_TRACK = new KeyMapping(
			"key.simple_music_control.next_music_track",
			InputConstants.getKey("key.keyboard.m").getValue(),
            //~ if <1.21.9 'MOD_KEYS' -> '"key.category.simple_music_control.keys"'
			MOD_KEYS
	);

	public static final KeyMapping TOGGLE_MUSIC = new KeyMapping(
			"key.simple_music_control.toggle_music",
			InputConstants.UNKNOWN.getValue(),
            //~ if <1.21.9 'MOD_KEYS' -> '"key.category.simple_music_control.keys"'
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
		ModUtil.globalPause = !ModUtil.globalPause;
		((MusicPauseControl) ((SoundManagerAccessor) client.getSoundManager()).smc$getSoundEngine()).smc$setMusicPaused(ModUtil.globalPause);
		NowPlayingWidget.displayToggleNotification();
	}
}
