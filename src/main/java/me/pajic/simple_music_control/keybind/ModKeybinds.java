package me.pajic.simple_music_control.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import me.pajic.simple_music_control.config.ModClientConfig;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.minecraft.client.KeyMapping;
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

    public static void registerKeybinds(RegisterKeyMappingsEvent event) {
        event.register(NEXT_MUSIC_TRACK.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (ModClientConfig.enableNextTrackKeybind && NEXT_MUSIC_TRACK.get().consumeClick() && JukeboxTracker.noJukeboxesInRange() && mc.player != null) {
            mc.getMusicManager().stopPlaying();
            mc.getMusicManager().startPlaying(mc.getSituationalMusic());
        }
    }
}
