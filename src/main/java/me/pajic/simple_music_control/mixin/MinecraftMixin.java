package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.simple_music_control.config.ModClientConfig;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
//? if 1.21.4
/*import net.minecraft.util.random.SimpleWeightedRandomList;*/
//? if >= 1.21.5
/*import net.minecraft.util.random.WeightedList;*/

import java.util.Optional;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow @Nullable public LocalPlayer player;

    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/Musics;GAME:Lnet/minecraft/sounds/Music;"
            )
    )
    private Music allowCreativeMusicInSurvival(Music original) {
        return ModClientConfig.creativeMusicInSurvival ? Musics.CREATIVE : original;
    }

    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/MusicManager;isPlayingMusic(Lnet/minecraft/sounds/Music;)Z"
            )
    )
    private boolean skipUnderwaterCheckIfSituationalMusicUnlocked(boolean original) {
        return !ModClientConfig.unlockSituationalMusic && original;
    }

    @ModifyExpressionValue(
            method = "tick",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/Minecraft;pause:Z",
                    ordinal = 6
            )
    )
    private boolean playMusicWhenPaused(boolean original) {
        return !ModClientConfig.playMusicWhenPaused && original;
    }

    //? if <= 1.21.1 {
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

    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/Musics;CREATIVE:Lnet/minecraft/sounds/Music;"
            )
    )
    private Music allowSituationalMusicInCreative(Music original) {
        return ModClientConfig.situationalMusicInCreative ? ModUtil.pickRandomSituationalMusic(player).orElse(player.level().getBiome(player.blockPosition()).value().getBackgroundMusic().orElse(original)) : original;
    }
    //?}

    //? if >= 1.21.4 {
    /*@ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getBackgroundMusic()Ljava/util/Optional;"
            )
    )
    //? if 1.21.4 {
    /^private Optional<SimpleWeightedRandomList<Music>> unlockSituationalMusicInSurvival(Optional<SimpleWeightedRandomList<Music>> original) {
        return ModUtil.pickRandomSituationalMusic(player).or(() -> original);
    }
    ^///?}
    //? if >= 1.21.5 {
    /^private Optional<WeightedList<Music>> unlockSituationalMusicInSurvival(Optional<WeightedList<Music>> original) {
        return ModUtil.pickRandomSituationalMusic(player).or(() -> original);
    }
    ^///?}

    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/Musics;CREATIVE:Lnet/minecraft/sounds/Music;"
            )
    )
    private Music allowSituationalMusicInCreative(Music original) {
        return ModClientConfig.situationalMusicInCreative ? ModUtil.pickRandomSituationalMusic(player).orElse(player.level().getBiome(player.blockPosition()).value().getBackgroundMusic().orElse(
                //? if 1.21.4
                /^SimpleWeightedRandomList.single(original))).getRandomValue^/
                //? if >= 1.21.5
                /^WeightedList.of(original))).getRandom^/
        (player.level().random).orElse(original) : original;
    }
    *///?}
}