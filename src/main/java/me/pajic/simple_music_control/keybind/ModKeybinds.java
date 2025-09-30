package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.util.JukeboxTracker;
import me.pajic.simple_music_control.util.ModUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    //? if >= 1.21.9 {
    /*private static final KeyMapping.Category MOD_KEYS = KeyMapping.Category.register(
            ResourceLocation.fromNamespaceAndPath("simple_music_control", "keys")
    );
    *///?}

    private static final KeyMapping NEXT_MUSIC_TRACK = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.simple_music_control.next_music_track",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_M,
                    //? if < 1.21.9
                    "category.simple_music_control.keybindings"
                    //? if >= 1.21.9
                    /*MOD_KEYS*/
            )
    );

    private static final KeyMapping TOGGLE_MUSIC = KeyBindingHelper.registerKeyBinding(
            new KeyMapping(
                    "key.simple_music_control.toggle_music",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN,
                    //? if < 1.21.9
                    "category.simple_music_control.keybindings"
                    //? if >= 1.21.9
                    /*MOD_KEYS*/
            )
    );

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (NEXT_MUSIC_TRACK.consumeClick() && JukeboxTracker.noJukeboxesInRange()) {
                client.getMusicManager().stopPlaying();
                //? if >= 1.21.4
                /*if (client.getSituationalMusic().music() != null)*/
                    client.getMusicManager().startPlaying(client.getSituationalMusic());
            }
            if (TOGGLE_MUSIC.consumeClick()) {
                if (ModUtil.globalPause) {
                    ModUtil.globalPause = false;
                    client.getSoundManager().resume();
                } else {
                    ModUtil.globalPause = true;
                    client.getSoundManager()
                            //? if < 1.21.6
                            .pause();
                            //? if >= 1.21.6
                            /*.pauseAllExcept(SoundSource.MUSIC);*/
                }
                NowPlayingWidget.displayWidget();
            }
        });
    }
}
