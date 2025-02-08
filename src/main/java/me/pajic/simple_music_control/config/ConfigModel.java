package me.pajic.simple_music_control.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;

@Modmenu(modId = "simple_music_control")
@Config(name = "simple_music_control", wrapperName = "ModConfig")
@Sync(Option.SyncMode.NONE)
@SuppressWarnings("unused")
public class ConfigModel {
    public boolean enableNextTrackKeybind = true;
    public boolean stopMusicOnJukeboxUse = true;
    public boolean creativeMusicInSurvival = true;
    public boolean situationalMusicInCreative = true;
    public boolean unlockSituationalMusic = false;
    @RangeConstraint(min = 1, max = 100) public int situationalMusicChance = 70;
    public boolean modifyMusicDelays = true;
    @PredicateConstraint("greaterThanZero") public int musicMinDelay = 600;
    @PredicateConstraint("greaterThanZero") public int musicMaxDelay = 1200;

    public static boolean greaterThanZero(int value) {
        return value > 0;
    }
}
