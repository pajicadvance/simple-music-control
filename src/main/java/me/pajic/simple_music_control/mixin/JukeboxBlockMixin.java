package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.simple_music_control.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.JukeboxBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(JukeboxBlock.class)
public class JukeboxBlockMixin {

    @Environment(EnvType.CLIENT)
    @ModifyExpressionValue(
            method = "useItemOn",
            at = @At(
                    value = "INVOKE",
                    //? if 1.21.4
                    /*target = "Lnet/minecraft/world/InteractionResult;consumesAction()Z"*/
                    //? if <= 1.21.1
                    target = "Lnet/minecraft/world/ItemInteractionResult;consumesAction()Z"
            )
    )
    private boolean stopMusicOnJukeboxPlay(boolean original) {
        if (Main.CONFIG.stopMusicOnJukeboxUse() && original) {
            Minecraft.getInstance().getMusicManager().stopPlaying();
        }
        return original;
    }
}
