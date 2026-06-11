package me.pajic.simple_music_control.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class VersionedUtil {

	public static Screen getScreen(Minecraft mc) {
		//? 26.1.2 {
		/*return mc.screen;
		*///?} else {
		return mc.gui.screen();
		//?}
	}

	public static boolean hudHidden(Minecraft mc) {
		//? 26.1.2 {
		/*return mc.options.hideGui;
		 *///?} else {
		return mc.gui.hud.isHidden();
		//?}
	}
}
