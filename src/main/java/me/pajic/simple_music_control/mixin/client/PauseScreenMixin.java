package me.pajic.simple_music_control.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;

//? >=26.1
import me.pajic.simple_music_control.SMC;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {

    @WrapMethod(method = {"extractRenderState", "render"})
    private void renderNowPlayingWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, Operation<Void> original) {
        original.call(graphics, mouseX, mouseY, a);
        NowPlayingWidget.displayPauseScreenWidget(graphics);
    }

    //? >=26.1 {
    @WrapMethod(method = "rendersNowPlayingToast")
    private boolean dontRenderIfModWidgetEnabled(Operation<Boolean> original) {
        if (!SMC.CONFIG.showNowPlayingWidgetInPauseMenu.get()) return original.call();
        return false;
    }
    //?}
}
