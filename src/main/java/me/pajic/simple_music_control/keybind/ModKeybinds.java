package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.config.ModConfig;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {

    private static final KeyMapping NEXT_MUSIC_TRACK = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.simple_music_control.next_music_track",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_M,
                    "category.simple_music_control.keybindings"
            )
    );

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ModConfig.enableNextTrackKeybind && NEXT_MUSIC_TRACK.consumeClick() && JukeboxTracker.noJukeboxesInRange() && client.player != null) {
                client.getMusicManager().stopPlaying();
                client.getMusicManager().startPlaying(client.getSituationalMusic());
            }
        });
    }
}
