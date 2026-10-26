package me.pajic.simple_music_control.util;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.pajic.simple_music_control.SMC;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class JukeboxTracker {

    public static Object2BooleanOpenHashMap<BlockPos> jukeboxes = new Object2BooleanOpenHashMap<>();

    public static void onJukeboxPlay(Level level, Minecraft minecraft, BlockPos pos) {
        if (level != null) {
            jukeboxes.put(pos, true);
            if (SMC.CONFIG.stopMusicOnJukeboxUse.get()) minecraft.getMusicManager().stopPlaying();
        }
    }

    public static void onJukeboxStop(BlockPos pos) {
        jukeboxes.removeBoolean(pos);
    }

    public static boolean noJukeboxesInRange() {
        return !SMC.CONFIG.stopMusicOnJukeboxUse.get() || jukeboxes.object2BooleanEntrySet().stream().noneMatch(Object2BooleanMap.Entry::getBooleanValue);
    }
}
