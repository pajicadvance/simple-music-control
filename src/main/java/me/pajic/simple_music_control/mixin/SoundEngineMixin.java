package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.audio.Channel;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
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

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(SoundEngine.class)
public class SoundEngineMixin {

    @Shadow private boolean loaded;
    @Shadow @Final private Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel;

    @WrapMethod(method = "pauseAllExcept")
    private void pauseAll(SoundSource[] ignoredSources, Operation<Void> original) {
        if (ModUtil.globalPause || (!SMC.CONFIG.playMusicWhenPaused.get() && loaded)) {
            instanceToChannel.forEach((_, channel) -> channel.execute(Channel::pause));
        }
        else original.call((Object) ignoredSources);
    }

    @WrapMethod(method = "resume")
    private void preventResumeIfGlobalPause(Operation<Void> original) {
        if (!ModUtil.globalPause) original.call();
    }

    @WrapMethod(method = "play")
    private SoundEngine.PlayResult showWidgetOnMusicPlay(SoundInstance instance, Operation<SoundEngine.PlayResult> original) {
        if (instance.getSource().equals(SoundSource.MUSIC)) {
            if (!ModUtil.globalPause) {
                SoundEngine.PlayResult result = original.call(instance);
                if (SMC.CONFIG.nowPlayingWidget.get() || SMC.CONFIG.showNowPlayingWidgetInPauseMenu.get()) NowPlayingWidget.displayWidget(instance);
                return result;
            }
            return SoundEngine.PlayResult.NOT_STARTED;
        }
        return original.call(instance);
    }
}
