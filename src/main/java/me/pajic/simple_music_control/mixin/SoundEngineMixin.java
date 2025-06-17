package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.audio.Channel;
import me.pajic.simple_music_control.config.ModConfig;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.sounds.SoundSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(SoundEngine.class)
public class SoundEngineMixin {

    @Shadow private boolean loaded;
    @Shadow @Final private Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel;

    //? if <= 1.21.5 {
    @WrapMethod(method = "pause")
    private void pauseAllExceptMusic(Operation<Void> original) {
        if (ModConfig.playMusicWhenPaused && loaded) {
            for (Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> entry : instanceToChannel.entrySet()) {
                if (!entry.getKey().getSource().equals(SoundSource.MUSIC)) {
                    entry.getValue().execute(Channel::pause);
                }
            }
        }
        else original.call();
    }
    //?}

    //? if > 1.21.5 {
    /*@WrapMethod(method = "pauseAllExcept")
    private void pauseAll(SoundSource[] soundSources, Operation<Void> original) {
        if (!ModConfig.playMusicWhenPaused && loaded) {
            instanceToChannel.forEach((instance, channel) -> channel.execute(Channel::pause));
        }
        else original.call((Object) soundSources);
    }
    *///?}

    @WrapMethod(method = "play")
    //? if < 1.21.6 {
    private void showWidgetOnMusicPlay(SoundInstance soundInstance, Operation<Void> original) {
        original.call(soundInstance);
        Minecraft mc = Minecraft.getInstance();
        if ((ModConfig.nowPlayingWidget || ModConfig.showNowPlayingWidgetInPauseMenu) && mc.player != null && soundInstance.getSource().equals(SoundSource.MUSIC)) {
            NowPlayingWidget.displayWidget(soundInstance);
        }
    }
    //?}
    //? if >= 1.21.6 {
    /*private SoundEngine.PlayResult showWidgetOnMusicPlay(SoundInstance soundInstance, Operation<SoundEngine.PlayResult> original) {
        Minecraft mc = Minecraft.getInstance();
        if ((ModConfig.nowPlayingWidget || ModConfig.showNowPlayingWidgetInPauseMenu) && mc.player != null && soundInstance.getSource().equals(SoundSource.MUSIC)) {
            NowPlayingWidget.displayWidget(soundInstance);
        }
        return original.call(soundInstance);
    }
    *///?}
}
