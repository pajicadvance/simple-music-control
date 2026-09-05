package me.pajic.simple_music_control.mixin.client;


import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.util.JukeboxTracker;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Optional;

//? >=26.1 {
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.attribute.BackgroundMusic;
//?} else {
/*import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.objectweb.asm.Opcodes;
*///?}

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;
	@Shadow private boolean pause;

	@Inject(
            //~ if <26.1 'updateLevelInEngines(Lnet/minecraft/client/multiplayer/ClientLevel;Z)V' -> 'updateLevelInEngines'
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

    //? >=26.1 {
    @SuppressWarnings({"DataFlowIssue", "resource"})
	@ModifyReturnValue(
            method = "getSituationalMusic",
            at = @At(
					value = "RETURN",
					ordinal = 2
			)
    )
    private Music handleSituationalMusic(Music original, @Local(name = "backgroundMusic") BackgroundMusic backgroundMusic) {
		Optional<Music> unlockedSituational = ModUtil.pickRandomSituationalMusic(player);
		if (original != null && original.equals(Musics.CREATIVE) && SMC.CONFIG.situationalMusicInCreative.get()) {
			return unlockedSituational.orElse(player.level().getRandom().nextBoolean() ?
					Musics.CREATIVE : backgroundMusic.select(false, false).orElse(null)
			);
		}
        return unlockedSituational.orElse(original);
    }
    //?} else {
    /*@ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/Musics;GAME:Lnet/minecraft/sounds/Music;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private Music allowCreativeMusicInSurvival(Music original) {
        return SMC.CONFIG.situationalMusicInCreative.get() ? Musics.CREATIVE : original;
    }

    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/MusicManager;isPlayingMusic(Lnet/minecraft/sounds/Music;)Z"
            )
    )
    private boolean skipUnderwaterCheckIfSituationalMusicUnlocked(boolean original) {
        return !SMC.CONFIG.unlockSituationalMusic.get() && original;
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getBackgroundMusic()Ljava/util/Optional;"
            )
    )
    private Optional<Music> unlockSituationalMusicInSurvival(Optional<Music> original) {
        return ModUtil.pickRandomSituationalMusic(player).or(() -> original);
    }

    @SuppressWarnings("DataFlowIssue")
    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/Musics;CREATIVE:Lnet/minecraft/sounds/Music;",
                    opcode = Opcodes.GETSTATIC
            )
    )
    private Music allowSituationalMusicInCreative(Music original) {
        return SMC.CONFIG.situationalMusicInCreative.get() ? ModUtil.pickRandomSituationalMusic(player).orElse(player.level().getBiome(player.blockPosition()).value().getBackgroundMusic().orElse(original)) : original;
    }
    *///?}
}
