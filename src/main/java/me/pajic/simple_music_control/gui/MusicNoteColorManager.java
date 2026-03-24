package me.pajic.simple_music_control.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.ColorLerper;

public class MusicNoteColorManager {

	private static int musicNoteColorTick;
	private static long lastMusicNoteColorChange;
	public static int musicNoteColor = -1;

	public static void tickMusicNotes(Minecraft client) {
		if (client.getMusicManager().getCurrentMusicTranslationKey() != null) {
			long l = System.currentTimeMillis();
			if (l > lastMusicNoteColorChange + 25L) {
				musicNoteColorTick++;
				lastMusicNoteColorChange = l;
				musicNoteColor = ColorLerper.getLerpedColor(ColorLerper.Type.MUSIC_NOTE, musicNoteColorTick);
			}
		}
	}
}
