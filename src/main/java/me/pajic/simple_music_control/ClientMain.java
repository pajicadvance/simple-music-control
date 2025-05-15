package me.pajic.simple_music_control;

import me.pajic.simple_music_control.gui.MusicNoteColorManager;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.fabricmc.api.ClientModInitializer;

public class ClientMain implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeybinds.init();
        JukeboxTracker.init();
        MusicNoteColorManager.init();
        NowPlayingWidget.initOverlay();
    }
}
