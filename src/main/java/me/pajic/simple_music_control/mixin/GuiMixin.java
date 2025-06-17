package me.pajic.simple_music_control.mixin;

import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(
            method = "render",
            at = @At("TAIL")
    )
    private void renderModOverlays(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        NowPlayingWidget.render(guiGraphics, deltaTracker);
    }
}
