package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.simple_music_control.SMC;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.NowPlayingToast;
import org.spongepowered.asm.mixin.Mixin;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(NowPlayingToast.class)
public class NowPlayingToastMixin {

	@WrapMethod(method = "extractRenderState")
    private void dontRenderIfModWidgetEnabled(GuiGraphicsExtractor graphics, Font font, long fullyVisibleForMs, Operation<Void> original) {
        if (!SMC.CONFIG.nowPlayingWidget.get()) original.call(graphics, font, fullyVisibleForMs);
    }
}
