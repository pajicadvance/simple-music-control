package me.pajic.simple_music_control.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.simple_music_control.SMC;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeighedSoundEvents.class)
public class WeighedSoundEventsMixin {

    @Unique private boolean smc$music;

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void checkIfMusic(Identifier location, String subtitle, CallbackInfo ci) {
        smc$music = location.getPath().startsWith("music.");
    }

    @ModifyExpressionValue(
            method = {"getWeight", "getSound"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/sounds/Weighted;getWeight()I"
            )
    )
    private int equalizeMusicWeights(int original, @Local Weighted<Sound> weighted) {
        return smc$music && weighted instanceof Sound && SMC.CONFIG.equalizeMusicWeights.get() ? 1 : original;
    }
}
