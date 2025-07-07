package me.pajic.simple_music_control.gui;

import me.pajic.simple_music_control.config.ModConfig;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
//? if <= 1.21.1
import net.minecraft.util.FastColor;
//? if > 1.21.1
/*import net.minecraft.util.ARGB;*/
//? if >= 1.21.5
/*import com.mojang.blaze3d.opengl.GlStateManager;*/

import java.awt.*;

public class NowPlayingWidget {
    private static final Minecraft MC = Minecraft.getInstance();
    private static SoundInstance soundInstance = null;
    private static Component trackName;
    private static final Component note = Component.literal("♫");
    private static float timer = 0;
    private static float toggleTimer = 0;
    private static boolean centered = true;

    public static void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (ModConfig.nowPlayingWidget && isMusicOn() && soundInstance != null && timer > 0) {
            if (soundInstance.getSound() == null) {
                renderConditionalText(guiGraphics, Component.translatable("gui.simple_music_control.soundSystemFail"), Color.WHITE.getRGB());
            }
            else if (!(MC.level != null && MC.screen != null)) {
                renderConditionalTextWithFade(trackName, guiGraphics, deltaTracker, true);
                if (!MC.getSoundManager().isActive(soundInstance)) soundInstance = null;
            }
        }
        else if (toggleTimer > 0) {
            renderConditionalTextWithFade(ModUtil.globalPause ?
                    Component.translatable("gui.simple_music_control.pause") :
                    Component.translatable("gui.simple_music_control.resume"),
                    guiGraphics, deltaTracker, false);
            if (ModUtil.globalPause) soundInstance = null;
        }
    }

    public static void displayWidget(SoundInstance sound) {
        if (!(MC.screen instanceof PauseScreen)) {
            soundInstance = sound;
            if (soundInstance != null && soundInstance.getSound() != null) {
                trackName = Component.translatable(soundInstance.getSound().getLocation().toShortLanguageKey().replace("/", "."));
                centered = MC.level != null;
                timer = ModConfig.nowPlayingWidgetDuration * 20;
            }
        }
    }

    public static void displayWidget() {
        if (!(MC.screen instanceof PauseScreen)) {
            centered = MC.level != null;
            toggleTimer = ModConfig.nowPlayingWidgetDuration * 20;
        }
    }

    public static void displayPauseScreenWidget(GuiGraphics guiGraphics) {
        if (ModConfig.showNowPlayingWidgetInPauseMenu && isMusicOn() && soundInstance != null && soundInstance.getSound() != null) {
            renderCornerText(guiGraphics, true);
            if (!MC.getSoundManager().isActive(soundInstance)) soundInstance = null;
        }
    }

    private static void renderConditionalText(GuiGraphics guiGraphics, Component text, int textColor, int noteColor) {
        ModUtil.startRender(guiGraphics);
        if (centered) {
            renderActionBarText(text, guiGraphics, 0, 0, textColor);
            renderActionBarText(note, guiGraphics, MC.font.width(trackName) / 2 + 7, 0, noteColor);
            renderActionBarText(note, guiGraphics, -MC.font.width(trackName) / 2 - 7, 0, noteColor);
        }
        else {
            renderCornerText(guiGraphics, false);
        }
        ModUtil.stopRender(guiGraphics);
    }

    private static void renderConditionalText(GuiGraphics guiGraphics, Component text, int textColor) {
        ModUtil.startRender(guiGraphics);
        if (centered) {
            renderActionBarText(text, guiGraphics, 0, 0, textColor);
        }
        else {
            renderCornerText(guiGraphics, false);
        }
        ModUtil.stopRender(guiGraphics);
    }

    private static void renderConditionalTextWithFade(Component text, GuiGraphics guiGraphics, DeltaTracker deltaTracker, boolean note) {
        float f = note ? timer - deltaTracker.getRealtimeDeltaTicks() : toggleTimer - deltaTracker.getRealtimeDeltaTicks();
        int i = (int) (f * 255.0F / 20.0F);
        if (i > 255) i = 255;
        if (i > 8) {
            int textColor = /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(i, Color.WHITE.getRGB());
            int noteColor = /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(i, MusicNoteColorManager.musicNoteColor);
            if (note) renderConditionalText(guiGraphics, text, textColor, noteColor);
            else renderConditionalText(guiGraphics, text, textColor);
        }
        if (note) timer -= deltaTracker.getRealtimeDeltaTicks();
        else toggleTimer -= deltaTracker.getRealtimeDeltaTicks();
    }

    private static void renderActionBarText(Component text, GuiGraphics guiGraphics, int xOffset, int yOffset, int color) {
        guiGraphics.drawString(
                MC.font, text,
                MC.getWindow().getGuiScaledWidth() / 2 - MC.font.width(text) / 2 + xOffset,
                MC.getWindow().getGuiScaledHeight() - 96 + yOffset,
                color
        );
    }

    private static void renderCornerText(GuiGraphics guiGraphics, boolean pauseMenu) {
        ModUtil.startRender(guiGraphics);
        if (pauseMenu) {
            switch (ModConfig.pauseWidgetPosition) {
                case TOP_LEFT -> {
                    guiGraphics.drawString(MC.font, note, 4, 4, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, 15, 4, Color.WHITE.getRGB());
                }
                case TOP_RIGHT -> {
                    guiGraphics.drawString(MC.font, note, MC.getWindow().getGuiScaledWidth() - 12, 4, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, MC.getWindow().getGuiScaledWidth() - MC.font.width(trackName) - 16, 4, Color.WHITE.getRGB());
                }
                case BOTTOM_LEFT -> {
                    guiGraphics.drawString(MC.font, note, 4, MC.getWindow().getGuiScaledHeight() - 11, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, 15, MC.getWindow().getGuiScaledHeight() - 11, Color.WHITE.getRGB());
                }
                case BOTTOM_RIGHT -> {
                    guiGraphics.drawString(MC.font, note, MC.getWindow().getGuiScaledWidth() - 12, MC.getWindow().getGuiScaledHeight() - 11, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, MC.getWindow().getGuiScaledWidth() - MC.font.width(trackName) - 16, MC.getWindow().getGuiScaledHeight() - 11, Color.WHITE.getRGB());
                }
            }
        } else {
            switch (ModConfig.pauseWidgetPosition) {
                case TOP_LEFT, BOTTOM_LEFT -> {
                    guiGraphics.drawString(MC.font, note, 4, 4, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, 15, 4, Color.WHITE.getRGB());
                }
                case TOP_RIGHT, BOTTOM_RIGHT -> {
                    guiGraphics.drawString(MC.font, note, MC.getWindow().getGuiScaledWidth() - 12, 4, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, MC.getWindow().getGuiScaledWidth() - MC.font.width(trackName) - 16, 4, Color.WHITE.getRGB());
                }
            }
        }
        ModUtil.stopRender(guiGraphics);
    }

    private static boolean isMusicOn() {
        return MC.options.getSoundSourceVolume(SoundSource.MASTER) > 0 && MC.options.getSoundSourceVolume(SoundSource.MUSIC) > 0;
    }
}
