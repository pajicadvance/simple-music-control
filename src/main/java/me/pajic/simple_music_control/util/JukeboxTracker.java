package me.pajic.simple_music_control.util;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.pajic.simple_music_control.config.ModClientConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

@EventBusSubscriber(modid = "simple_music_control", value = Dist.CLIENT)
public class JukeboxTracker {
    public static Object2BooleanOpenHashMap<BlockPos> jukeboxes = new Object2BooleanOpenHashMap<>();

    public static void onJukeboxPlay(Level level, Minecraft minecraft, BlockPos pos) {
        if (ModClientConfig.stopMusicOnJukeboxUse && level != null) {
            minecraft.getMusicManager().stopPlaying();
            jukeboxes.put(pos, true);
        }
    }

    public static void onJukeboxStop(BlockPos pos) {
        if (ModClientConfig.stopMusicOnJukeboxUse) {
            jukeboxes.removeBoolean(pos);
        }
    }

    public static boolean noJukeboxesInRange() {
        return jukeboxes.object2BooleanEntrySet().stream().noneMatch(Object2BooleanMap.Entry::getBooleanValue);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();
        if (ModClientConfig.stopMusicOnJukeboxUse && client.player != null && !jukeboxes.isEmpty()) {
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
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        jukeboxes.clear();
    }
}
