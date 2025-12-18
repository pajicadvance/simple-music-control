package me.pajic.simple_music_control.mixin;

//? if fabric {

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Gui.class)
public class GuiMixin {

	@Inject(
			method = "render",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/gui/Gui;renderSubtitleOverlay(Lnet/minecraft/client/gui/GuiGraphics;Z)V"
			)
	)
	private void renderNowPlayingWidget(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
		NowPlayingWidget.render(guiGraphics, deltaTracker);
	}
}
//?}
