package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.PauseScreen;
import org.spongepowered.asm.mixin.Mixin;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(PauseScreen.class)
public class PauseScreenMixin {

    @WrapMethod(method = "extractRenderState")
    private void renderNowPlayingWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, Operation<Void> original) {
        original.call(graphics, mouseX, mouseY, a);
        NowPlayingWidget.displayPauseScreenWidget(graphics);
    }

    @WrapMethod(method = "rendersNowPlayingToast")
    private boolean dontRenderIfModWidgetEnabled(Operation<Boolean> original) {
        if (!SMC.CONFIG.showNowPlayingWidgetInPauseMenu.get()) return original.call();
        return false;
    }
}
