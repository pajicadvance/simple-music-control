package me.pajic.simple_music_control;

import me.pajic.simple_music_control.config.ModClientConfig;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "simple_music_control", dist = Dist.CLIENT)
public class ClientMain {
    public ClientMain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(ModKeybinds::registerKeybinds);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ModClientConfig.CLIENT_SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
