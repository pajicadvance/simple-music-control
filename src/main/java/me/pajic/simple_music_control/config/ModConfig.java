package me.pajic.simple_music_control.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.*;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import me.pajic.simple_music_control.gui.PauseMenuWidgetPosition;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModConfig {

    public static ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(ResourceLocation.fromNamespaceAndPath("simple_music_control", "mod_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("simple_music_control.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build()
            ).build();

    @SerialEntry public static boolean enableNextTrackKeybind = true;
    @SerialEntry public static boolean stopMusicOnJukeboxUse = true;
    @SerialEntry public static boolean creativeMusicInSurvival = true;
    @SerialEntry public static boolean situationalMusicInCreative = true;
    @SerialEntry public static boolean modifyMusicDelays = true;
    @SerialEntry public static int musicMinDelay = 300;
    @SerialEntry public static int musicMaxDelay = 600;
    @SerialEntry public static boolean playMusicWhenPaused = true;
    @SerialEntry public static boolean nowPlayingWidget = true;
    @SerialEntry public static boolean showNowPlayingWidgetInPauseMenu = true;
    @SerialEntry public static PauseMenuWidgetPosition pauseWidgetPosition = PauseMenuWidgetPosition.TOP_LEFT;
    @SerialEntry public static boolean unlockSituationalMusic = false;
    @SerialEntry public static int situationalMusicChance = 70;

    public static Screen makeScreen(Screen parentScreen) {
        return YetAnotherConfigLib.create(HANDLER, (defaults, config, builder) -> builder
                .title(Component.translatable("text.config.simple_music_control.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("text.config.simple_music_control.title"))
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.enableNextTrackKeybind"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.enableNextTrackKeybind")))
                                .binding(enableNextTrackKeybind, () -> enableNextTrackKeybind, newValue -> enableNextTrackKeybind = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.stopMusicOnJukeboxUse"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.stopMusicOnJukeboxUse")))
                                .binding(stopMusicOnJukeboxUse, () -> stopMusicOnJukeboxUse, newValue -> stopMusicOnJukeboxUse = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.creativeMusicInSurvival"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.creativeMusicInSurvival")))
                                .binding(creativeMusicInSurvival, () -> creativeMusicInSurvival, newValue -> creativeMusicInSurvival = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.situationalMusicInCreative"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.situationalMusicInCreative")))
                                .binding(situationalMusicInCreative, () -> situationalMusicInCreative, newValue -> situationalMusicInCreative = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.modifyMusicDelays"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.modifyMusicDelays")))
                                .binding(modifyMusicDelays, () -> modifyMusicDelays, newValue -> modifyMusicDelays = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.musicMinDelay"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.musicMinDelay.tooltip")))
                                .binding(musicMinDelay, () -> musicMinDelay, newValue -> musicMinDelay = newValue)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).range(1, Integer.MAX_VALUE))
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.musicMaxDelay"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.musicMaxDelay.tooltip")))
                                .binding(musicMaxDelay, () -> musicMaxDelay, newValue -> musicMaxDelay = newValue)
                                .controller(opt -> IntegerFieldControllerBuilder.create(opt).range(1, Integer.MAX_VALUE))
                                .flag(OptionFlag.GAME_RESTART)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.playMusicWhenPaused"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.playMusicWhenPaused")))
                                .binding(playMusicWhenPaused, () -> playMusicWhenPaused, newValue -> playMusicWhenPaused = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.nowPlayingWidget"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.nowPlayingWidget")))
                                .binding(nowPlayingWidget, () -> nowPlayingWidget, newValue -> nowPlayingWidget = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.showNowPlayingWidgetInPauseMenu"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.showNowPlayingWidgetInPauseMenu")))
                                .binding(showNowPlayingWidgetInPauseMenu, () -> showNowPlayingWidgetInPauseMenu, newValue -> showNowPlayingWidgetInPauseMenu = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<PauseMenuWidgetPosition>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.pauseWidgetPosition"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.pauseWidgetPosition")))
                                .binding(pauseWidgetPosition, () -> pauseWidgetPosition, newValue -> pauseWidgetPosition = newValue)
                                .controller(opt -> EnumControllerBuilder.create(opt).enumClass(PauseMenuWidgetPosition.class))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.unlockSituationalMusic"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.unlockSituationalMusic.tooltip")))
                                .binding(unlockSituationalMusic, () -> unlockSituationalMusic, newValue -> unlockSituationalMusic = newValue)
                                .controller(TickBoxControllerBuilder::create)
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("text.config.simple_music_control.option.situationalMusicChance"))
                                .description(OptionDescription.of(Component.translatable("text.config.simple_music_control.option.situationalMusicChance.tooltip")))
                                .binding(situationalMusicChance, () -> situationalMusicChance, newValue -> situationalMusicChance = newValue)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt).range(1, 100).step(1))
                                .build())
                        .build()
                )
        ).generateScreen(parentScreen);
    }
}
