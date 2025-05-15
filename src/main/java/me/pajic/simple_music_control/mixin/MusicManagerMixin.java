package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if > 1.21.1
/*import net.minecraft.client.sounds.MusicInfo;*/

@Mixin(MusicManager.class)
public class MusicManagerMixin {

    //? if <= 1.21.1 {
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
    //?}

    //? if > 1.21.1 {
    /*@WrapWithCondition(
            method = "tick",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/MusicManager;startPlaying(Lnet/minecraft/client/sounds/MusicInfo;)V"
            )
    )
    private boolean dontPlayIfJukeboxInRange(MusicManager instance, MusicInfo music) {
        return JukeboxTracker.noJukeboxesInRange();
    }
    *///?}
}
