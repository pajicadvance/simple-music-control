package me.pajic.simple_music_control.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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

    //? >=26.1 {
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
			return Math.max(original, SMC.CONFIG.musicMinDelay.get() * 20);
		}

		@ModifyExpressionValue(
				method = "getNextSongDelay",
				at = @At(
						value = "INVOKE",
						target = "Lnet/minecraft/sounds/Music;maxDelay()I"
				)
		)
		private int modifyMaxDelay(int original) {
			return Math.min(original, SMC.CONFIG.musicMaxDelay.get() * 20);
		}
	}
    //?} else {
    /*@ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/sounds/Music;getMinDelay()I"
            )
    )
    private int modifyMinDelay(int original) {
        return Math.max(original, SMC.CONFIG.musicMinDelay.get() * 20);
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/sounds/Music;getMaxDelay()I"
            )
    )
    private int modifyMaxDelay(int original) {
        return Math.min(original, SMC.CONFIG.musicMaxDelay.get() * 20);
    }
    *///?}
}
