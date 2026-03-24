package me.pajic.simple_music_control.platform.neoforge;

//? neoforge {

/*import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.NowPlayingWidget;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import me.pajic.simple_music_control.util.ModUtil;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = SMC.MOD_ID, value = Dist.CLIENT)
public class NeoforgeClientEventSubscriber {

	@SubscribeEvent
	private static void onClientSetup(FMLClientSetupEvent event) {
		SMC.onInitialize();
	}

	@SubscribeEvent
	private static void initKeybinds(RegisterKeyMappingsEvent event) {
		ModKeybinds.init();
		event.registerCategory(ModKeybinds.MOD_KEYS);
		event.register(ModKeybinds.NEXT_MUSIC_TRACK);
		event.register(ModKeybinds.TOGGLE_MUSIC);
	}

	@SubscribeEvent
	private static void initOnClientTick(ClientTickEvent.Post event) {
		ModUtil.onClientTick(Minecraft.getInstance());
	}

	@SubscribeEvent
	private static void registerNowPlayingWidget(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.SUBTITLE_OVERLAY, SMC.id("now_playing"), NowPlayingWidget::render);
	}
}
*///?}
