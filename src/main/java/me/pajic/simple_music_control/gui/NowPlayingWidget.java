package me.pajic.simple_music_control.gui;

import me.pajic.simple_music_control.config.ModClientConfig;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
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

    public static void displayWidget(SoundInstance sound) {
        soundInstance = sound;
        timer = ModClientConfig.nowPlayingWidgetDuration * 20;
    }

    @SubscribeEvent
    public static void renderNowPlayingWidget(RenderGuiEvent.Post event) {
        GuiGraphics guiGraphics = event.getGuiGraphics();
        DeltaTracker deltaTracker = event.getPartialTick();
        if (ModClientConfig.nowPlayingWidget && soundInstance != null && timer > 0) {
            if (soundInstance.getSound() == null) {
                ModUtil.startRender(guiGraphics);
                renderActionBarText(MC, Component.translatable("gui.simple_music_control.soundSystemFail"), guiGraphics, 0, 0, Color.WHITE.getRGB());
                ModUtil.stopRender(guiGraphics);
            }
            else {
                trackName = Component.translatable(soundInstance.getSound().getLocation().toShortLanguageKey().replace("/", "."));
                float f = timer - deltaTracker.getGameTimeDeltaPartialTick(false);
                int i = (int) (f * 255.0F / 20.0F);
                if (i > 255) i = 255;
                if (i > 8) {
                    int textColor = /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(i, Color.WHITE.getRGB());
                    int noteColor = /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(i, MusicNoteColorManager.musicNoteColor);
                    ModUtil.startRender(guiGraphics);
                    renderActionBarText(MC, trackName, guiGraphics, 0, 0, textColor);
                    renderActionBarText(MC, note, guiGraphics, MC.font.width(trackName) / 2 + 7, 0, noteColor);
                    renderActionBarText(MC, note, guiGraphics, -MC.font.width(trackName) / 2 - 7, 0, noteColor);
                    ModUtil.stopRender(guiGraphics);
                }
                timer -= deltaTracker.getGameTimeDeltaTicks();
                if (!MC.getSoundManager().isActive(soundInstance)) soundInstance = null;
            }
        }
    }

    public static void displayPauseScreenWidget(GuiGraphics guiGraphics) {
        if (ModClientConfig.showNowPlayingWidgetInPauseMenu && soundInstance != null && soundInstance.getSound() != null) {
            ModUtil.startRender(guiGraphics);
            switch (ModClientConfig.pauseWidgetPosition) {
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
            ModUtil.stopRender(guiGraphics);
            if (!MC.getSoundManager().isActive(soundInstance)) soundInstance = null;
        }
    }

    public static void renderActionBarText(Minecraft mc, Component text, GuiGraphics guiGraphics, int xOffset, int yOffset, int color) {
        guiGraphics.drawString(
                mc.font, text,
                mc.getWindow().getGuiScaledWidth() / 2 - mc.font.width(text) / 2 + xOffset,
                mc.getWindow().getGuiScaledHeight() - 96 + yOffset,
                color
        );
    }
}
