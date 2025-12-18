package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.util.JukeboxTracker;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.attribute.BackgroundMusic;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;

	@Shadow
	private volatile boolean pause;

	@Inject(
			method = "updateLevelInEngines(Lnet/minecraft/client/multiplayer/ClientLevel;Z)V",
			at = @At("TAIL")
	)
	private void onClientLevelChange(CallbackInfo ci) {
		JukeboxTracker.jukeboxes.clear();
	}

    @WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/MusicManager;tick()V"
            )
    )
    private boolean playMusicWhenPaused(MusicManager instance) {
		return !pause || SMC.CONFIG.playMusicWhenPaused.get();
	}

    @SuppressWarnings({"DataFlowIssue", "resource"})
	@ModifyReturnValue(
            method = "getSituationalMusic",
            at = @At(
					value = "RETURN",
					ordinal = 2
			)
    )
    private Music handleSituationalMusic(Music original, @Local BackgroundMusic backgroundMusic) {
		Optional<Music> unlockedSituational = ModUtil.pickRandomSituationalMusic(player);
		if (original != null && original.equals(Musics.CREATIVE) && SMC.CONFIG.situationalMusicInCreative.get()) {
			return unlockedSituational.orElse(player.level().getRandom().nextBoolean() ?
					Musics.CREATIVE : backgroundMusic.select(false, false).orElse(null)
			);
		}
        return unlockedSituational.orElse(original);
    }
}
