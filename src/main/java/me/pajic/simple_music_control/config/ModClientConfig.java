package me.pajic.simple_music_control.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = "simple_music_control", bus = EventBusSubscriber.Bus.MOD)
public class ModClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLE_NEXT_TRACK_KEYBIND = BUILDER
            .translation("text.config.simple_music_control.option.enableNextTrackKeybind")
            .define("enableNextTrackKeybind", true);
    private static final ModConfigSpec.BooleanValue STOP_MUSIC_ON_JUKEBOX_USE = BUILDER
            .translation("text.config.simple_music_control.option.stopMusicOnJukeboxUse")
            .define("stopMusicOnJukeboxUse", true);
    private static final ModConfigSpec.BooleanValue CREATIVE_MUSIC_IN_SURVIVAL = BUILDER
            .translation("text.config.simple_music_control.option.creativeMusicInSurvival")
            .define("creativeMusicInSurvival", true);
    private static final ModConfigSpec.BooleanValue MODIFY_MUSIC_DELAYS = BUILDER
            .translation("text.config.simple_music_control.option.modifyMusicDelays")
            .define("modifyMusicDelays", true);
    private static final ModConfigSpec.IntValue MUSIC_MIN_DELAY = BUILDER
            .translation("text.config.simple_music_control.option.musicMinDelay")
            .defineInRange("musicMinDelay", 600, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MUSIC_MAX_DELAY = BUILDER
            .translation("text.config.simple_music_control.option.musicMaxDelay")
            .defineInRange("musicMaxDelay", 1200, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec CLIENT_SPEC = BUILDER.build();

    public static boolean enableNextTrackKeybind;
    public static boolean stopMusicOnJukeboxUse;
    public static boolean creativeMusicInSurvival;
    public static boolean modifyMusicDelays;
    public static int musicMinDelay;
    public static int musicMaxDelay;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        updateConfig(event);
    }

    @SubscribeEvent
    static void onChange(final ModConfigEvent.Reloading event) {
        updateConfig(event);
    }

    private static void updateConfig(ModConfigEvent event) {
        if (event.getConfig().getSpec() == CLIENT_SPEC) {
            enableNextTrackKeybind = ENABLE_NEXT_TRACK_KEYBIND.get();
            stopMusicOnJukeboxUse = STOP_MUSIC_ON_JUKEBOX_USE.get();
            creativeMusicInSurvival = CREATIVE_MUSIC_IN_SURVIVAL.get();
            modifyMusicDelays = MODIFY_MUSIC_DELAYS.get();
            musicMinDelay = MUSIC_MIN_DELAY.get();
            musicMaxDelay = MUSIC_MAX_DELAY.get();
        }
    }
}
