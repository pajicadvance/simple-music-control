package me.pajic.simple_music_control.gui;

import me.fzzyhmstrs.fzzy_config.util.EnumTranslatable;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public enum WidgetPosition implements EnumTranslatable {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

	@Override @NotNull public String prefix() {
		return "simple_music_control.config.widgetPosition";
	}

    public Component getName() {
        return Component.translatable(prefix() + "." + name());
    }
}
