package me.pajic.simple_music_control.gui;

import dev.isxander.yacl3.api.NameableEnum;
import net.minecraft.network.chat.Component;

public enum PauseMenuWidgetPosition implements NameableEnum {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT;

    @Override
    public Component getDisplayName() {
        return Component.translatable("text.config.simple_music_control.option.pauseWidgetPosition." + name().toLowerCase());
    }
}
