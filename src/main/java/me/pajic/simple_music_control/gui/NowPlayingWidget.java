package me.pajic.simple_music_control.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.pajic.simple_music_control.config.ModConfig;
import me.pajic.simple_music_control.util.ModUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
//? if <= 1.21.1
import net.minecraft.util.FastColor;
//? if > 1.21.1
/*import net.minecraft.util.ARGB;*/
//? if >= 1.21.5
/*import com.mojang.blaze3d.opengl.GlStateManager;*/

public class NowPlayingWidget {
    private static final Minecraft MC = Minecraft.getInstance();
    private static SoundInstance soundInstance = null;
    private static float timer = 0;

    public static void initOverlay() {
        HudRenderCallback.EVENT.register(NowPlayingWidgetOverlay.INSTANCE::render);
    }

    public static void displayWidget(SoundInstance sound) {
        soundInstance = sound;
        timer = ModConfig.nowPlayingWidgetDuration * 20;
    }

    public static class NowPlayingWidgetOverlay implements LayeredDraw.Layer {
        protected static final NowPlayingWidgetOverlay INSTANCE = new NowPlayingWidgetOverlay();

        @Override
        public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
            if (ModConfig.nowPlayingWidget && soundInstance != null && timer > 0) {
                if (ModUtil.SOUND_SYSTEM_FAILED) {
                    guiGraphics.flush();
                    //? if < 1.21.5
                    RenderSystem.enableBlend();
                    //? if >= 1.21.5
                    /*GlStateManager._enableBlend();*/
                    renderActionBarText(MC, Component.translatable("gui.simple_music_control.soundSystemFail1"), guiGraphics, 0, 0, 16777215);
                    renderActionBarText(MC, Component.translatable("gui.simple_music_control.soundSystemFail1"), guiGraphics, 0, 12, 16777215);
                    guiGraphics.flush();
                    //? if < 1.21.5
                    RenderSystem.disableBlend();
                    //? if >= 1.21.5
                    /*GlStateManager._disableBlend();*/
                }
                else {
                    Component trackName = Component.translatable(soundInstance.getSound().getLocation().toShortLanguageKey().replace("/", "."));
                    Component note = Component.literal("♫");
                    float f = timer - deltaTracker.getGameTimeDeltaPartialTick(false);
                    int i = (int) (f * 255.0F / 20.0F);
                    if (i > 255) i = 255;
                    if (i > 8) {
                        int textColor = /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(i, 16777215);
                        int noteColor = /*? if <= 1.21.1 {*/FastColor.ARGB32/*?}*//*? if > 1.21.1 {*//*ARGB*//*?}*/.color(i, MusicNoteColorManager.musicNoteColor);
                        guiGraphics.flush();
                        //? if < 1.21.5
                        RenderSystem.enableBlend();
                        //? if >= 1.21.5
                        /*GlStateManager._enableBlend();*/
                        renderActionBarText(MC, trackName, guiGraphics, 0, 0, textColor);
                        renderActionBarText(MC, note, guiGraphics, MC.font.width(trackName) / 2 + 7, 0, noteColor);
                        renderActionBarText(MC, note, guiGraphics, -MC.font.width(trackName) / 2 - 7, 0, noteColor);
                        guiGraphics.flush();
                        //? if < 1.21.5
                        RenderSystem.disableBlend();
                        //? if >= 1.21.5
                        /*GlStateManager._disableBlend();*/
                    }
                    timer -= deltaTracker.getGameTimeDeltaTicks();
                    if (!MC.getSoundManager().isActive(soundInstance)) soundInstance = null;
                }
            }
        }
    }

    public static void displayPauseScreenWidget(GuiGraphics guiGraphics) {
        if (ModConfig.showNowPlayingWidgetInPauseMenu && soundInstance != null && !ModUtil.SOUND_SYSTEM_FAILED) {
            Component trackName = Component.translatable(soundInstance.getSound().getLocation().toShortLanguageKey().replace("/", "."));
            Component note = Component.literal("♫");
            switch (ModConfig.pauseWidgetPosition) {
                case TOP_LEFT -> {
                    guiGraphics.drawString(MC.font, note, 4, 4, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, 15, 4, 16777215);
                }
                case TOP_RIGHT -> {
                    guiGraphics.drawString(MC.font, note, MC.getWindow().getGuiScaledWidth() - 12, 4, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, MC.getWindow().getGuiScaledWidth() - MC.font.width(trackName) - 16, 4, 16777215);
                }
                case BOTTOM_LEFT -> {
                    guiGraphics.drawString(MC.font, note, 4, MC.getWindow().getGuiScaledHeight() - 11, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, 15, MC.getWindow().getGuiScaledHeight() - 11, 16777215);
                }
                case BOTTOM_RIGHT -> {
                    guiGraphics.drawString(MC.font, note, MC.getWindow().getGuiScaledWidth() - 12, MC.getWindow().getGuiScaledHeight() - 11, MusicNoteColorManager.musicNoteColor);
                    guiGraphics.drawString(MC.font, trackName, MC.getWindow().getGuiScaledWidth() - MC.font.width(trackName) - 16, MC.getWindow().getGuiScaledHeight() - 11, 16777215);
                }
            }
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
