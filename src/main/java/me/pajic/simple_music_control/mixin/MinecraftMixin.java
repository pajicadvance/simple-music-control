package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.simple_music_control.ClientMain;
import me.pajic.simple_music_control.config.ModClientConfig;
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

    //? if <= 1.21.1 {
    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getBackgroundMusic()Ljava/util/Optional;"
            )
    )
    private Optional<Music> unlockSituationalMusicInSurvival(Optional<Music> original) {
        return ClientMain.pickRandomSituationalMusic(player).or(() -> original);
    }

    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/Musics;CREATIVE:Lnet/minecraft/sounds/Music;"
            )
    )
    private Music allowSituationalMusicInCreative(Music original) {
        return ModClientConfig.situationalMusicInCreative ? ClientMain.pickRandomSituationalMusic(player).orElse(player.level().getBiome(player.blockPosition()).value().getBackgroundMusic().orElse(original)) : original;
    }
    //?}

    //? if 1.21.4 {
    /*@ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/biome/Biome;getBackgroundMusic()Ljava/util/Optional;"
            )
    )
    private Optional<SimpleWeightedRandomList<Music>> unlockSituationalMusicInSurvival(Optional<SimpleWeightedRandomList<Music>> original) {
        return ClientMain.pickRandomSituationalMusic(player).or(() -> original);
    }

    @ModifyExpressionValue(
            method = "getSituationalMusic",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/sounds/Musics;CREATIVE:Lnet/minecraft/sounds/Music;"
            )
    )
    private Music allowSituationalMusicInCreative(Music original) {
        return ModClientConfig.situationalMusicInCreative ? ClientMain.pickRandomSituationalMusic(player).orElse(player.level().getBiome(player.blockPosition()).value().getBackgroundMusic().orElse(SimpleWeightedRandomList.single(original))).getRandomValue(player.level().random).orElse(original) : original;
    }
    *///?}
}