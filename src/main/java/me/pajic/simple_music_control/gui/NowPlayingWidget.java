package me.pajic.simple_music_control.gui;

import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.util.ModUtil;
import me.pajic.simple_music_control.util.VersionedUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ARGB;

import java.awt.*;

public class NowPlayingWidget {
    private static final Minecraft MC = Minecraft.getInstance();
    private static SoundInstance soundInstance = null;
    private static Component trackName;
    private static final Component NOTE = Component.literal("♫");
    private static long timer = 0;
    private static long toggleTimer = 0;
	private static long previousTickMillis = System.currentTimeMillis();
    private static boolean centered = true;

    public static void render(GuiGraphicsExtractor guiGraphics) {
		if (!(VersionedUtil.getScreen(MC) instanceof PauseScreen)) {
		    if (SMC.CONFIG.nowPlayingWidget.get() && !VersionedUtil.hudHidden(MC) && isMusicOn() && soundInstance != null && timer > 0) {
				if (soundInstance.getSound() == null) {
					renderConditionalText(guiGraphics, Component.translatable("gui.simple_music_control.soundSystemTrippedFellAndExploded"), Color.WHITE.getRGB(), false, 0);
				} else if (!(MC.level != null && VersionedUtil.getScreen(MC) != null)) {
					renderConditionalTextWithFade(trackName, guiGraphics, true);
					if (!MC.getSoundManager().isActive(soundInstance)) soundInstance = null;
				}
			} else if (toggleTimer > 0) {
				renderConditionalTextWithFade(ModUtil.globalPause ?
								Component.translatable("gui.simple_music_control.pause") :
								Component.translatable("gui.simple_music_control.resume"),
						guiGraphics, false);
				if (ModUtil.globalPause) soundInstance = null;
			}
	    }
		previousTickMillis = System.currentTimeMillis();
    }

    public static void displayWidget(SoundInstance sound) {
		soundInstance = sound;
		if (soundInstance != null && soundInstance.getSound() != null) {
			trackName = Component.translatable(soundInstance.getSound().getLocation().toShortLanguageKey().replace("/", "."));
			centered = MC.level != null;
			timer = SMC.CONFIG.nowPlayingWidgetDuration.get() * 1000L;
		}
    }

    public static void displayToggleNotification() {
        if (!(VersionedUtil.getScreen(MC) instanceof PauseScreen)) {
            centered = MC.level != null;
            toggleTimer = SMC.CONFIG.nowPlayingWidgetDuration.get() * 1000L;
        }
    }

    public static void displayPauseScreenWidget(GuiGraphicsExtractor guiGraphics) {
        if (SMC.CONFIG.showNowPlayingWidgetInPauseMenu.get() && isMusicOn() && soundInstance != null && soundInstance.getSound() != null) {
            renderCornerText(guiGraphics, true, Color.WHITE.getRGB(), MusicNoteColorManager.musicNoteColor);
        }
		if (!MC.getSoundManager().isActive(soundInstance)) soundInstance = null;
    }

    private static void renderConditionalText(GuiGraphicsExtractor guiGraphics, Component text, int textColor, boolean note, int noteColor) {
        if (centered) {
            renderActionBarText(text, guiGraphics, 0, textColor);
			if (note) {
		        renderActionBarText(NOTE, guiGraphics, MC.font.width(trackName) / 2 + 7, noteColor);
				renderActionBarText(NOTE, guiGraphics, -MC.font.width(trackName) / 2 - 7, noteColor);
	        }
        }
        else renderCornerText(guiGraphics, false, textColor, noteColor);
    }

    private static void renderConditionalTextWithFade(Component text, GuiGraphicsExtractor guiGraphics, boolean note) {
		long delta = System.currentTimeMillis() - previousTickMillis;
        long t = note ? timer - delta : toggleTimer - delta;
        int i = (int) (t * 255L / 1000L);
        if (i > 255) i = 255;
        if (i > 8) {
            int textColor = ARGB.color(i, Color.WHITE.getRGB());
            int noteColor = ARGB.color(i, MusicNoteColorManager.musicNoteColor);
            renderConditionalText(guiGraphics, text, textColor, note, noteColor);
        }
        if (note) timer -= delta;
        else toggleTimer -= delta;
    }

    private static void renderActionBarText(Component text, GuiGraphicsExtractor guiGraphics, int xOffset, int color) {
        guiGraphics.text(
                MC.font, text,
                MC.getWindow().getGuiScaledWidth() / 2 - MC.font.width(text) / 2 + xOffset,
                MC.getWindow().getGuiScaledHeight() - 96,
                color
        );
    }

    private static void renderCornerText(GuiGraphicsExtractor guiGraphics, boolean pauseMenu, int textColor, int noteColor) {
        if (pauseMenu) renderCornerText(SMC.CONFIG.pauseWidgetPosition.get(), guiGraphics, textColor, noteColor);
		else renderCornerText(SMC.CONFIG.titleWidgetPosition.get(), guiGraphics, textColor, noteColor);
    }

	private static void renderCornerText(WidgetPosition pos, GuiGraphicsExtractor guiGraphics, int textColor, int noteColor) {
		switch (pos) {
			case TOP_LEFT -> {
				guiGraphics.text(MC.font, NOTE, 4, 4, noteColor);
				guiGraphics.text(MC.font, trackName, 15, 4, textColor);
			}
			case TOP_RIGHT -> {
				guiGraphics.text(MC.font, NOTE, MC.getWindow().getGuiScaledWidth() - 12, 4, noteColor);
				guiGraphics.text(MC.font, trackName, MC.getWindow().getGuiScaledWidth() - MC.font.width(trackName) - 16, 4, textColor);
			}
			case BOTTOM_LEFT -> {
				guiGraphics.text(MC.font, NOTE, 4, MC.getWindow().getGuiScaledHeight() - 11, noteColor);
				guiGraphics.text(MC.font, trackName, 15, MC.getWindow().getGuiScaledHeight() - 11, textColor);
			}
			case BOTTOM_RIGHT -> {
				guiGraphics.text(MC.font, NOTE, MC.getWindow().getGuiScaledWidth() - 12, MC.getWindow().getGuiScaledHeight() - 11, noteColor);
				guiGraphics.text(MC.font, trackName, MC.getWindow().getGuiScaledWidth() - MC.font.width(trackName) - 16, MC.getWindow().getGuiScaledHeight() - 11, textColor);
			}
		}
	}

    private static boolean isMusicOn() {
        return MC.options.getSoundSourceVolume(SoundSource.MASTER) > 0 && MC.options.getSoundSourceVolume(SoundSource.MUSIC) > 0;
    }
}
