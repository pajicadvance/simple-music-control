package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.simple_music_control.config.ModConfig;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.NowPlayingToast;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NowPlayingToast.class)
public class NowPlayingToastMixin {

    @WrapMethod(method = "render")
    private void dontRenderIfModWidgetEnabled(GuiGraphics guiGraphics, Font font, long visibilityTime, Operation<Void> original) {
        if (!ModConfig.nowPlayingWidget) original.call(guiGraphics, font, visibilityTime);
    }
}
