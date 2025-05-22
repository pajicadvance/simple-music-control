package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.audio.Channel;
import me.pajic.simple_music_control.config.ModClientConfig;
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

    @WrapMethod(method = "pause")
    private void pauseAllExceptMusic(Operation<Void> original) {
        if (ModClientConfig.playMusicWhenPaused && loaded) {
            for (Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> entry : instanceToChannel.entrySet()) {
                if (!entry.getKey().getSource().equals(SoundSource.MUSIC)) {
                    entry.getValue().execute(Channel::pause);
                }
            }
        }
        else original.call();
    }

    @WrapMethod(method = "play")
    private void showWidgetOnMusicPlay(SoundInstance soundInstance, Operation<Void> original) {
        original.call(soundInstance);
        Minecraft mc = Minecraft.getInstance();
        if ((ModClientConfig.nowPlayingWidget || ModClientConfig.showNowPlayingWidgetInPauseMenu) && mc.player != null && soundInstance.getSource().equals(SoundSource.MUSIC)) {
            NowPlayingWidget.displayWidget(soundInstance);
        }
    }
}
