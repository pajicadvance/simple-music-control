package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.simple_music_control.config.ModConfig;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {

    @WrapMethod(method = "render")
    private void renderNowPlayingWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        original.call(guiGraphics, mouseX, mouseY, partialTick);
        NowPlayingWidget.displayPauseScreenWidget(guiGraphics);
    }

    //? if > 1.21.5 {
    /*@WrapMethod(method = "rendersNowPlayingToast")
    private boolean dontRenderIfModWidgetEnabled(Operation<Boolean> original) {
        if (!ModConfig.showNowPlayingWidgetInPauseMenu) return original.call();
        return false;
    }
    *///?}
}
