package me.pajic.simple_music_control.util;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.pajic.simple_music_control.config.ModConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

public class JukeboxTracker {
    public static Object2BooleanOpenHashMap<BlockPos> jukeboxes = new Object2BooleanOpenHashMap<>();

    public static void onJukeboxPlay(Level level, Minecraft minecraft, BlockPos pos) {
        if (ModConfig.stopMusicOnJukeboxUse && level != null) {
            minecraft.getMusicManager().stopPlaying();
            jukeboxes.put(pos, true);
        }
    }

    public static void onJukeboxStop(BlockPos pos) {
        if (ModConfig.stopMusicOnJukeboxUse) {
            jukeboxes.removeBoolean(pos);
        }
    }

    public static boolean noJukeboxesInRange() {
        return jukeboxes.object2BooleanEntrySet().stream().noneMatch(Object2BooleanMap.Entry::getBooleanValue);
    }

    public static void init() {
        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((client, world) -> jukeboxes.clear());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ModConfig.stopMusicOnJukeboxUse && client.player != null && !jukeboxes.isEmpty()) {
                for (Object2BooleanMap.Entry<BlockPos> jukebox : jukeboxes.object2BooleanEntrySet()) {
                    BlockPos pos = jukebox.getKey();
                    if (client.player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > 4096) {
                        jukeboxes.put(pos, false);
                    } else {
                        jukeboxes.put(pos, true);
                        client.getMusicManager().stopPlaying();
                    }
                }
            }
        });
    }
}
