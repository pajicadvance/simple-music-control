package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(MusicManager.class)
public class MusicManagerMixin {

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/MusicManager;startPlaying(Lnet/minecraft/sounds/Music;)V"
            )
    )
    private boolean dontPlayIfJukeboxInRange(MusicManager instance, Music music) {
        return JukeboxTracker.noJukeboxesInRange();
    }

	@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
	@Mixin(MusicManager.MusicFrequency.class)
	private static class MusicFrequencyMixin {

		@ModifyExpressionValue(
				method = "getNextSongDelay",
				at = @At(
						value = "INVOKE",
						target = "Lnet/minecraft/sounds/Music;minDelay()I"
				)
		)
		private int modifyMinDelay(int original) {
			return SMC.CONFIG.modifyMusicDelays.get() ? SMC.CONFIG.musicMinDelay.get() * 20 : original;
		}

		@ModifyExpressionValue(
				method = "getNextSongDelay",
				at = @At(
						value = "INVOKE",
						target = "Lnet/minecraft/sounds/Music;maxDelay()I"
				)
		)
		private int modifyMaxDelay(int original) {
			return SMC.CONFIG.modifyMusicDelays.get() ? SMC.CONFIG.musicMaxDelay.get() * 20 : original;
		}
	}
}
