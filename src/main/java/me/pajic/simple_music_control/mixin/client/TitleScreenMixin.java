package me.pajic.simple_music_control.mixin.client;

import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

	@Inject(
			method = {"extractRenderState", "render"},
			at = @At("TAIL")
	)
	private void renderNowPlayingWidget(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci) {
		if (SMC.CONFIG.showNowPlayingWidgetOnTitleScreen.get()) NowPlayingWidget.render(graphics);
	}
}
