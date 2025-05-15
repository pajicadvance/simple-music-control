package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.simple_music_control.config.ModClientConfig;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PauseScreen.class)
public class PauseScreenMixin {

    @WrapMethod(method = "render")
    private void renderNowPlayingWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, Operation<Void> original) {
        original.call(guiGraphics, mouseX, mouseY, partialTick);
        if (ModClientConfig.showNowPlayingWidgetInPauseMenu) {
            NowPlayingWidget.displayPauseScreenWidget(guiGraphics);
        }
    }
}
