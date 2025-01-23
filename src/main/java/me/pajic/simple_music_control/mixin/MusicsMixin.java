package me.pajic.simple_music_control.mixin;

import me.pajic.simple_music_control.config.ModClientConfig;
import net.minecraft.sounds.Musics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Musics.class)
public class MusicsMixin {

    @ModifyConstant(
            method = "createGameMusic",
            constant = @Constant(intValue = 12000)
    )
    private static int modifyMinSurvivalMusicDelay(int original) {
        if (ModClientConfig.modifyMusicDelays) {
            return ModClientConfig.musicMinDelay * 20;
        }
        return original;
    }

    @ModifyConstant(
            method = "createGameMusic",
            constant = @Constant(intValue = 24000)
    )
    private static int modifyMaxSurvivalMusicDelay(int original) {
        if (ModClientConfig.modifyMusicDelays) {
            return ModClientConfig.musicMaxDelay * 20;
        }
        return original;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 12000)
    )
    private static int modifyMinCreativeMusicDelay(int original) {
        if (ModClientConfig.modifyMusicDelays) {
            return ModClientConfig.musicMinDelay * 20;
        }
        return original;
    }

    @ModifyConstant(
            method = "<clinit>",
            constant = @Constant(intValue = 24000)
    )
    private static int modifyMaxCreativeMusicDelay(int original) {
        if (ModClientConfig.modifyMusicDelays) {
            return ModClientConfig.musicMaxDelay * 20;
        }
        return original;
    }
}