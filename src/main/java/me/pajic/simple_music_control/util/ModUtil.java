package me.pajic.simple_music_control.util;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.MusicNoteColorManager;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ModUtil {
	public static boolean globalPause = false;

	@SuppressWarnings("resource")
	public static Optional<Music> pickRandomSituationalMusic(LocalPlayer player) {
		if (SMC.CONFIG.unlockSituationalMusic.get()) {
			if (player.level().getRandom().nextFloat() < (float) SMC.CONFIG.situationalMusicChance.get() / 100) {
				if (player.level().dimension() == Level.OVERWORLD) {
					return Optional.of(Constants.OVERWORLD_SITUATIONAL_MUSIC.get(player.level().getRandom().nextInt(Constants.OVERWORLD_SITUATIONAL_MUSIC.size())));
				} else if (player.level().dimension() == Level.NETHER) {
					return Optional.of(Constants.NETHER_SITUATIONAL_MUSIC.get(player.level().getRandom().nextInt(Constants.NETHER_SITUATIONAL_MUSIC.size())));
				}
			}
			else if (player.isCreative()) return Optional.of(Musics.CREATIVE);
			else return SMC.CONFIG.creativeMusicInSurvival.get() ? Optional.of(Musics.CREATIVE) : Optional.of(Musics.GAME);
		}
		return Optional.empty();
	}

	public static void onClientTick(Minecraft client) {
		if (SMC.CONFIG.stopMusicOnJukeboxUse.get() && client.player != null && !JukeboxTracker.jukeboxes.isEmpty()) {
			for (Object2BooleanMap.Entry<BlockPos> jukebox : JukeboxTracker.jukeboxes.object2BooleanEntrySet()) {
				BlockPos pos = jukebox.getKey();
				if (client.player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > 4096) {
					JukeboxTracker.jukeboxes.put(pos, false);
				} else {
					JukeboxTracker.jukeboxes.put(pos, true);
					client.getMusicManager().stopPlaying();
				}
			}
		}
		if (ModKeybinds.NEXT_MUSIC_TRACK.consumeClick() && JukeboxTracker.noJukeboxesInRange()) {
			client.getMusicManager().stopPlaying();
			if (client.getSituationalMusic() != null) {
				client.getMusicManager().startPlaying(client.getSituationalMusic());
			}
		}
		if (ModKeybinds.TOGGLE_MUSIC.consumeClick()) {
			if (ModUtil.globalPause) {
				ModUtil.globalPause = false;
				client.getSoundManager().resume();
			} else {
				ModUtil.globalPause = true;
				client.getSoundManager().pauseAllExcept(SoundSource.MUSIC);
			}
			NowPlayingWidget.displayToggleNotification();
		}
		MusicNoteColorManager.tickMusicNotes(client);
	}
}
