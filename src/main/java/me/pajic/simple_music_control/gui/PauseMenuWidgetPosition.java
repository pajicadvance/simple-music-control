package me.pajic.simple_music_control.gui;

import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import org.jetbrains.annotations.NotNull;

public enum PauseMenuWidgetPosition implements EnumTranslatable {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

	@Override
	@NotNull public String prefix() {
		return "simple_music_control.config.pauseWidgetPosition";
	}
}
