package me.pajic.simple_music_control.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.audio.Channel;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.util.ModUtil;
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

    //? >=26.1 {
    @WrapMethod(method = "pauseAllExcept")
    private void pauseAll(SoundSource[] ignoredSources, Operation<Void> original) {
        if (ModUtil.globalPause || (!SMC.CONFIG.playMusicWhenPaused.get() && loaded)) {
            instanceToChannel.forEach((soundInstance, channel) -> channel.execute(Channel::pause));
        }
        else original.call((Object) ignoredSources);
    }
    //?} else {
    /*@WrapMethod(method = "pause")
    private void pauseAllExceptMusic(Operation<Void> original) {
        if (SMC.CONFIG.playMusicWhenPaused.get() && loaded && !ModUtil.globalPause) {
            for (Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> entry : instanceToChannel.entrySet()) {
                if (!entry.getKey().getSource().equals(SoundSource.MUSIC)) {
                    entry.getValue().execute(Channel::pause);
                }
            }
        }
        else original.call();
    }
    *///?}

    @WrapMethod(method = "resume")
    private void preventResumeIfGlobalPause(Operation<Void> original) {
        if (!ModUtil.globalPause) original.call();
    }

    @WrapMethod(method = "play")
    //~ if <26.1 'SoundEngine.PlayResult' -> 'void'
    //~ if <26.1 'Operation<SoundEngine.PlayResult>' -> 'Operation<Void>'
    private SoundEngine.PlayResult showWidgetOnMusicPlay(SoundInstance instance, Operation<SoundEngine.PlayResult> original) {
        if (instance.getSource().equals(SoundSource.MUSIC)) {
            if (!ModUtil.globalPause) {
                /*? >=26.1 {*/SoundEngine.PlayResult result = /*?}*/original.call(instance);
                if (SMC.CONFIG.nowPlayingWidget.get() || SMC.CONFIG.showNowPlayingWidgetInPauseMenu.get()) NowPlayingWidget.displayWidget(instance);
                //? >=26.1
                return result;
            }
            //? >=26.1
            return SoundEngine.PlayResult.NOT_STARTED;
        }
        //~ if <26.1 'return' -> 'else'
        return original.call(instance);
    }
}
