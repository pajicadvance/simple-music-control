package me.pajic.simple_music_control.gui;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
//? if <= 1.21.1
import net.minecraft.util.FastColor;
//? if > 1.21.1
/*import net.minecraft.util.ARGB;*/

public class MusicNoteColorManager {
    private static int musicNoteColorTick;
    private static long lastMusicNoteColorChange;
    public static int musicNoteColor = -1;

    private static final DyeColor[] MUSIC_NOTE_COLORS = new DyeColor[]{
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

    private static int getLerpedColor(float f) {
        int i = Mth.floor(f);
        int j = i / 30;
        int k = MUSIC_NOTE_COLORS.length;
        int l = j % k;
        int m = (j + 1) % k;
        float g = (i % 30 + Mth.frac(f)) / 30;
        int n = getModifiedColor(MUSIC_NOTE_COLORS[l]);
        int o = getModifiedColor(MUSIC_NOTE_COLORS[m]);
        return lerp(g, n, o);
    }

    private static int lerp(float f, int i, int j) {
        int k = Mth.lerpInt(f, /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.alpha(i), /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.alpha(j));
        int l = Mth.lerpInt(f, /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.red(i), /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.red(j));
        int m = Mth.lerpInt(f, /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.green(i), /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.green(j));
        int n = Mth.lerpInt(f, /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.blue(i), /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.blue(j));
        return /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(k, l, m, n);
    }

    private static int getModifiedColor(DyeColor dyeColor) {
        if (dyeColor == DyeColor.WHITE) {
            return -1644826;
        } else {
            int i = dyeColor.getTextureDiffuseColor();
            return /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(
                    255,
                    Mth.floor(/*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.red(i) * (float) 1.25),
                    Mth.floor(/*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.green(i) * (float) 1.25),
                    Mth.floor(/*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.blue(i) * (float) 1.25)
            );
        }
    }

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.getMusicManager().currentMusic != null) {
                long l = System.currentTimeMillis();
                if (l > lastMusicNoteColorChange + 25L) {
                    musicNoteColorTick++;
                    lastMusicNoteColorChange = l;
                    musicNoteColor = getLerpedColor(musicNoteColorTick);
                }
            }
        });
    }
}
