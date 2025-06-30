package me.pajic.simple_music_control.config;

import me.pajic.simple_music_control.gui.PauseMenuWidgetPosition;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = "simple_music_control", bus = EventBusSubscriber.Bus.MOD)
public class ModClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue STOP_MUSIC_ON_JUKEBOX_USE = BUILDER
            .translation("text.config.simple_music_control.option.stopMusicOnJukeboxUse")
            .define("stopMusicOnJukeboxUse", true);
    private static final ModConfigSpec.BooleanValue CREATIVE_MUSIC_IN_SURVIVAL = BUILDER
            .translation("text.config.simple_music_control.option.creativeMusicInSurvival")
            .define("creativeMusicInSurvival", true);
    private static final ModConfigSpec.BooleanValue SITUATIONAL_MUSIC_IN_CREATIVE = BUILDER
            .translation("text.config.simple_music_control.option.situationalMusicInCreative")
            .define("situationalMusicInCreative", true);
    private static final ModConfigSpec.BooleanValue MODIFY_MUSIC_DELAYS = BUILDER
            .translation("text.config.simple_music_control.option.modifyMusicDelays")
            .define("modifyMusicDelays", true);
    private static final ModConfigSpec.IntValue MUSIC_MIN_DELAY = BUILDER
            .translation("text.config.simple_music_control.option.musicMinDelay")
            .gameRestart()
            .defineInRange("musicMinDelay", 300, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.IntValue MUSIC_MAX_DELAY = BUILDER
            .translation("text.config.simple_music_control.option.musicMaxDelay")
            .gameRestart()
            .defineInRange("musicMaxDelay", 600, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue PLAY_MUSIC_WHEN_PAUSED = BUILDER
            .translation("text.config.simple_music_control.option.playMusicWhenPaused")
            .define("playMusicWhenPaused", true);
    private static final ModConfigSpec.BooleanValue NOW_PLAYING_WIDGET = BUILDER
            .translation("text.config.simple_music_control.option.nowPlayingWidget")
            .define("nowPlayingWidget", true);
    private static final ModConfigSpec.IntValue NOW_PLAYING_WIDGET_DURATION = BUILDER
            .translation("text.config.simple_music_control.option.nowPlayingWidgetDuration")
            .defineInRange("nowPlayingWidgetDuration", 6, 1, Integer.MAX_VALUE);
    private static final ModConfigSpec.BooleanValue SHOW_NOW_PLAYING_WIDGET_IN_PAUSE_MENU = BUILDER
            .translation("text.config.simple_music_control.option.showNowPlayingWidgetInPauseMenu")
            .define("showNowPlayingWidgetInPauseMenu", true);
    private static final ModConfigSpec.EnumValue<PauseMenuWidgetPosition> PAUSE_WIDGET_POSITION = BUILDER
            .translation("text.config.simple_music_control.option.pauseWidgetPosition")
            .defineEnum("pauseWidgetPosition", PauseMenuWidgetPosition.TOP_LEFT);
    private static final ModConfigSpec.BooleanValue UNLOCK_SITUATIONAL_MUSIC = BUILDER
            .translation("text.config.simple_music_control.option.unlockSituationalMusic")
            .define("unlockSituationalMusic", false);
    private static final ModConfigSpec.IntValue SITUATIONAL_MUSIC_CHANCE = BUILDER
            .translation("text.config.simple_music_control.option.situationalMusicChance")
            .defineInRange("situationalMusicChance", 70, 1, 100);

    public static final ModConfigSpec CLIENT_SPEC = BUILDER.build();

    public static boolean stopMusicOnJukeboxUse;
    public static boolean creativeMusicInSurvival;
    public static boolean situationalMusicInCreative;
    public static boolean modifyMusicDelays;
    public static int musicMinDelay;
    public static int musicMaxDelay;
    public static boolean playMusicWhenPaused;
    public static boolean nowPlayingWidget;
    public static int nowPlayingWidgetDuration;
    public static boolean showNowPlayingWidgetInPauseMenu;
    public static PauseMenuWidgetPosition pauseWidgetPosition;
    public static boolean unlockSituationalMusic;
    public static int situationalMusicChance;

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
            stopMusicOnJukeboxUse = STOP_MUSIC_ON_JUKEBOX_USE.get();
            creativeMusicInSurvival = CREATIVE_MUSIC_IN_SURVIVAL.get();
            situationalMusicInCreative = SITUATIONAL_MUSIC_IN_CREATIVE.get();
            modifyMusicDelays = MODIFY_MUSIC_DELAYS.get();
            musicMinDelay = MUSIC_MIN_DELAY.get();
            musicMaxDelay = MUSIC_MAX_DELAY.get();
            playMusicWhenPaused = PLAY_MUSIC_WHEN_PAUSED.get();
            nowPlayingWidget = NOW_PLAYING_WIDGET.get();
            nowPlayingWidgetDuration = NOW_PLAYING_WIDGET_DURATION.get();
            showNowPlayingWidgetInPauseMenu = SHOW_NOW_PLAYING_WIDGET_IN_PAUSE_MENU.get();
            pauseWidgetPosition = PAUSE_WIDGET_POSITION.get();
            unlockSituationalMusic = UNLOCK_SITUATIONAL_MUSIC.get();
            situationalMusicChance = SITUATIONAL_MUSIC_CHANCE.get();
        }
    }
}
