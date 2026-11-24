package me.pajic.simple_music_control.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.simple_music_control.util.MultipliedWeighted;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.Weighted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.sounds.SoundManager$Preparations")
public class SoundManagerPreparationsMixin {

    @WrapOperation(
            method = "handleRegistration",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/WeighedSoundEvents;addSound(Lnet/minecraft/client/sounds/Weighted;)V"
            )
    )
    private void applyEventWeight(WeighedSoundEvents instance, Weighted<Sound> weighted, Operation<Void> original, @Local Sound sound) {
        if (sound.getType() == Sound.Type.SOUND_EVENT && sound.getWeight() != 1) {
            original.call(instance, new MultipliedWeighted(weighted, sound.getWeight()));
        }
        else original.call(instance, weighted);
    }
}
