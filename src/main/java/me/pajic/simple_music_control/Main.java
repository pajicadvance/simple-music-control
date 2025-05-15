package me.pajic.simple_music_control;

import me.pajic.simple_music_control.config.ModConfig;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig.HANDLER.load();
    }
}
