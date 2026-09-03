package me.pajic.simple_music_control.platform.fabric;

//? fabric {

import dev.kikugie.fletching_table.annotation.fabric.Entrypoint;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import me.pajic.simple_music_control.util.ModUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

//~ if <26.1 'keymapping.v1.KeyMappingHelper' -> 'keybinding.v1.KeyBindingHelper'
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

//? >=26.1 {
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.KeyMapping;
//?}

@Entrypoint("client")
public class FabricClientEntrypoint implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //? >=26.1
        KeyMapping.Category.register(SMC.id("keys"));
        //~ if <26.1 'KeyMappingHelper.registerKeyMapping' -> 'KeyBindingHelper.registerKeyBinding' {
        KeyMappingHelper.registerKeyMapping(ModKeybinds.NEXT_MUSIC_TRACK);
        KeyMappingHelper.registerKeyMapping(ModKeybinds.TOGGLE_MUSIC);
        //~}
        //? >=26.1 {
        HudElementRegistry.attachElementAfter(
                VanillaHudElements.OVERLAY_MESSAGE,
                SMC.id("info_overlay"),
                (graphics, dt) -> NowPlayingWidget.render(graphics)
        );
        //?}
        ClientTickEvents.END_CLIENT_TICK.register(ModUtil::onClientTick);
    }
}
//?}
