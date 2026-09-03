package me.pajic.simple_music_control.platform;

//$ loader_util_import
import me.pajic.simple_music_control.platform.fabric.FabricLoaderUtil;

public interface MultiLoaderUtil {
    MultiLoaderUtil INSTANCE = /*$ loader_util_inst*/ new FabricLoaderUtil();

    boolean isModLoaded(String modId);
    boolean isDevEnv();
}
