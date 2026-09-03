package me.pajic.simple_music_control.mixin.client;

//? fabric && <26.1 {

/*import com.llamalad7.mixinextras.sugar.Local;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;",
                    ordinal = 0
            )
    )
    private void renderModOverlays(DeltaTracker deltaTracker, boolean renderLevel, CallbackInfo ci, @Local GuiGraphicsExtractor guiGraphics) {
        NowPlayingWidget.render(guiGraphics);
    }
}
*///?}
