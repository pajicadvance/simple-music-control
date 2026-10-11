package me.pajic.simple_music_control.config;

import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedBoolean;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber;
import me.pajic.simple_music_control.SMC;
import me.pajic.simple_music_control.gui.WidgetPosition;

@Version(version = 1)
public class ModConfig extends Config {

    public ModConfig() {
        super(SMC.id("config"));
    }

	public ValidatedBoolean stopMusicOnJukeboxUse = new ValidatedBoolean(true);
	public ValidatedBoolean creativeMusicInSurvival = new ValidatedBoolean(true);
	public ValidatedBoolean situationalMusicInCreative = new ValidatedBoolean(true);
	public ValidatedInt musicMinDelay = new ValidatedInt(300, 2400, 0, ValidatedNumber.WidgetType.TEXTBOX);
	public ValidatedInt musicMaxDelay = new ValidatedInt(600, 2400, 1, ValidatedNumber.WidgetType.TEXTBOX);
	public ValidatedBoolean playMusicWhenPaused = new ValidatedBoolean(true);
	public ValidatedBoolean nowPlayingWidget = new ValidatedBoolean(true);
	public ValidatedInt nowPlayingWidgetDuration = new ValidatedInt(6, 30, 1);
	public ValidatedBoolean showNowPlayingWidgetInPauseMenu = new ValidatedBoolean(true);
	public ValidatedBoolean showNowPlayingWidgetOnTitleScreen = new ValidatedBoolean(true);
	public ValidatedEnum<WidgetPosition> pauseWidgetPosition = new ValidatedEnum<>(WidgetPosition.TOP_LEFT);
	public ValidatedEnum<WidgetPosition> titleWidgetPosition = new ValidatedEnum<>(WidgetPosition.TOP_LEFT);
	public ValidatedBoolean unlockSituationalMusic = new ValidatedBoolean(false);
	public ValidatedInt situationalMusicChance = new ValidatedInt(70, 100, 0);
}
