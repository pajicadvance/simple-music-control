package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.simple_music_control.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

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
    private Music allowCreativeMusic(Music original) {
        return Main.CONFIG.creativeMusicInSurvival() && player.level().random.nextBoolean() ? Musics.CREATIVE : original;
    }
}