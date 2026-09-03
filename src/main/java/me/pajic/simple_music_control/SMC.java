package me.pajic.simple_music_control;

import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.pajic.simple_music_control.config.ModConfig;
import me.pajic.simple_music_control.platform.MultiLoaderUtil;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMC {

    public static final String MOD_ID = /*$ mod_id*/ "simple_music_control";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ModConfig CONFIG = ConfigApiJava.registerAndLoadConfig(ModConfig::new, RegisterType.CLIENT);

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void debugLog(String message, Object ... args) {
        if (MultiLoaderUtil.INSTANCE.isDevEnv()) LOGGER.info(message, args);
    }
}
