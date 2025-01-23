package me.pajic.simple_music_control;

import me.pajic.simple_music_control.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;

public class ClientMain implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModKeybinds.init();
    }
}
