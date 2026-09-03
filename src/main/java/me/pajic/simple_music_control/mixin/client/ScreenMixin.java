package me.pajic.simple_music_control.mixin.client;

import me.pajic.simple_music_control.keybind.ModKeybinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? >=26.1 {
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Final;
//?}

@Mixin(Screen.class)
public class ScreenMixin {

	@Shadow /*? >=26.1 {*/@Final/*?}*/ protected Minecraft minecraft;

	@Inject(
			method = "keyPressed",
			at = @At("HEAD")
	)
    //~ if <26.1 'KeyEvent event' -> 'int keyCode, int scanCode, int modifiers'
	private void handleKeybinds(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
		Screen self = (Screen) (Object) this;
		if (self instanceof PauseScreen || self instanceof TitleScreen) {
            //~ if <26.1 'event' -> 'keyCode, scanCode' {
			if (ModKeybinds.NEXT_MUSIC_TRACK.matches(event)) ModKeybinds.onNextMusicTrack(minecraft);
			else if (ModKeybinds.TOGGLE_MUSIC.matches(event)) ModKeybinds.onToggleMusic(minecraft);
            //~}
		}
	}
}
