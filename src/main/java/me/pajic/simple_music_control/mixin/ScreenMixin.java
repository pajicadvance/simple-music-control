package me.pajic.simple_music_control.mixin;

import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(Screen.class)
public class ScreenMixin {

	@Shadow @Final protected Minecraft minecraft;

	@Inject(
			method = "keyPressed",
			at = @At("HEAD")
	)
	private void handleKeybinds(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
		Screen self = (Screen) (Object) this;
		if (self instanceof PauseScreen || self instanceof TitleScreen) {
			if (ModKeybinds.NEXT_MUSIC_TRACK.matches(event)) ModKeybinds.onNextMusicTrack(minecraft);
			else if (ModKeybinds.TOGGLE_MUSIC.matches(event)) ModKeybinds.onToggleMusic(minecraft);
		}
	}
}
