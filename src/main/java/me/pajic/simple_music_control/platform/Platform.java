package me.pajic.simple_music_control.platform;

public interface Platform {

	boolean isModLoaded(String modId);

	boolean isDevelopmentEnvironment();

	default boolean isDebug() {
		return isDevelopmentEnvironment();
	}
}
