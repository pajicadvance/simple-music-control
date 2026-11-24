package me.pajic.simple_music_control.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;

//? >=1.21.6 {
import net.minecraft.client.color.ColorLerper;
//?} else {
/*import me.pajic.simple_music_control.mixin.client.MusicManagerAccessor;
//~ if <1.21.2 'ARGB' -> 'FastColor'
import net.minecraft.util.ARGB;
*///?}

public class MusicNoteColorManager {

	private static int musicNoteColorTick;
	private static long lastMusicNoteColorChange;
	public static int musicNoteColor = -1;

	public static void tickMusicNotes(Minecraft client) {
        //~ if <1.21.6 'client.getMusicManager().getCurrentMusicTranslationKey()' -> '((MusicManagerAccessor) client.getMusicManager()).smc$getCurrentMusic()'
		if (client.getMusicManager().getCurrentMusicTranslationKey() != null) {
			long l = System.currentTimeMillis();
			if (l > lastMusicNoteColorChange + 25L) {
				musicNoteColorTick++;
				lastMusicNoteColorChange = l;
				musicNoteColor = getLerpedColor(musicNoteColorTick);
			}
		}
	}

    private static int getLerpedColor(float f) {
        //? >=1.21.6 {
        return ColorLerper.getLerpedColor(ColorLerper.Type.MUSIC_NOTE, f);
        //?} else {
        /*int i = Mth.floor(f);
        int j = i / 30;
        int k = MUSIC_NOTE_COLORS.length;
        int l = j % k;
        int m = (j + 1) % k;
        float g = (i % 30 + Mth.frac(f)) / 30;
        int n = getModifiedColor(MUSIC_NOTE_COLORS[l]);
        int o = getModifiedColor(MUSIC_NOTE_COLORS[m]);
        //~ if <1.21.2 'ARGB' -> 'FastColor.ARGB32'
        return ARGB.lerp(g, n, o);
        *///?}
    }

    //? <1.21.6 {
    /*private static final DyeColor[] MUSIC_NOTE_COLORS = new DyeColor[]{
            DyeColor.WHITE,
            DyeColor.LIGHT_GRAY,
            DyeColor.LIGHT_BLUE,
            DyeColor.BLUE,
            DyeColor.CYAN,
            DyeColor.GREEN,
            DyeColor.LIME,
            DyeColor.YELLOW,
            DyeColor.ORANGE,
            DyeColor.PINK,
            DyeColor.RED,
            DyeColor.MAGENTA
    };

    private static int getModifiedColor(DyeColor dyeColor) {
        if (dyeColor == DyeColor.WHITE) {
            return -1644826;
        } else {
            int i = dyeColor.getTextureDiffuseColor();
            //~ if <1.21.2 'ARGB' -> 'FastColor.ARGB32' {
            return ARGB.color(
                    255,
                    Mth.floor(ARGB.red(i) * (float) 1.25),
                    Mth.floor(ARGB.green(i) * (float) 1.25),
                    Mth.floor(ARGB.blue(i) * (float) 1.25)
            );
            //~}
        }
    }
    *///?}
}
