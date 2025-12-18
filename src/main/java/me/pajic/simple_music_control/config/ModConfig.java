package me.pajic.simple_music_control.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.PauseMenuWidgetPosition;

@Version(version = 1)
public class ModConfig extends Config {
    public ModConfig() {
        super(SMC.CONFIG_RL);
    }

	public ValidatedBoolean stopMusicOnJukeboxUse = new ValidatedBoolean(true);
	public ValidatedBoolean creativeMusicInSurvival = new ValidatedBoolean(true);
	public ValidatedBoolean situationalMusicInCreative = new ValidatedBoolean(true);
	public ValidatedBoolean modifyMusicDelays = new ValidatedBoolean(true);
	public ValidatedInt musicMinDelay = new ValidatedInt(300, Integer.MAX_VALUE, 1);
	public ValidatedInt musicMaxDelay = new ValidatedInt(600, Integer.MAX_VALUE, 1);
	public ValidatedBoolean playMusicWhenPaused = new ValidatedBoolean(true);
	public ValidatedBoolean nowPlayingWidget = new ValidatedBoolean(true);
	public ValidatedInt nowPlayingWidgetDuration = new ValidatedInt(6, Integer.MAX_VALUE, 1);
	public ValidatedBoolean showNowPlayingWidgetInPauseMenu = new ValidatedBoolean(true);
	public ValidatedEnum<PauseMenuWidgetPosition> pauseWidgetPosition = new ValidatedEnum<>(PauseMenuWidgetPosition.TOP_LEFT);
	public ValidatedBoolean unlockSituationalMusic = new ValidatedBoolean(false);
	public ValidatedInt situationalMusicChance = new ValidatedInt(70, 100, 0);
}
