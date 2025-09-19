package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.util.JukeboxTracker;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.KeyMapping;
import net.minecraft.sounds.SoundSource;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = "simple_music_control", value = Dist.CLIENT)
public class ModKeybinds {

    public static final Lazy<KeyMapping> NEXT_MUSIC_TRACK = Lazy.of(() ->
            new KeyMapping(
                    "key.simple_music_control.next_music_track",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_M,
                    "category.simple_music_control.keybindings"
            )
    );

    public static final Lazy<KeyMapping> TOGGLE_MUSIC = Lazy.of(() ->
            new KeyMapping(
                    "key.simple_music_control.toggle_music",
                    InputConstants.Type.KEYSYM,
                    GLFW.GLFW_KEY_UNKNOWN,
                    "category.simple_music_control.keybindings"
            )
    );

    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(NEXT_MUSIC_TRACK.get());
        event.register(TOGGLE_MUSIC.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (NEXT_MUSIC_TRACK.get().consumeClick() && JukeboxTracker.noJukeboxesInRange() && mc.player != null) {
            mc.getMusicManager().stopPlaying();
            //? if >= 1.21.4
            /*if (mc.getSituationalMusic().music() != null)*/
                mc.getMusicManager().startPlaying(mc.getSituationalMusic());
        }
        if (TOGGLE_MUSIC.get().consumeClick()) {
            if (ModUtil.globalPause) {
                ModUtil.globalPause = false;
                mc.getSoundManager().resume();
            } else {
                ModUtil.globalPause = true;
                mc.getSoundManager()
                        //? if < 1.21.6
                        .pause();
                //? if >= 1.21.6
                /*.pauseAllExcept(SoundSource.MUSIC);*/
            }
            NowPlayingWidget.displayWidget();
        }
    }
}
