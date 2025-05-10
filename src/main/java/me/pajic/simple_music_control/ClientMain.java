package me.pajic.simple_music_control;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.pajic.simple_music_control.config.ModConfig;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
//? if 1.21.4
/*import net.minecraft.util.random.SimpleWeightedRandomList;*/
//? if 1.21.5
/*import net.minecraft.util.random.WeightedList;*/

import java.util.List;
import java.util.Optional;

public class ClientMain implements ClientModInitializer {

    public static final List<Music> OVERWORLD_SITUATIONAL_MUSIC = List.of(
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DEEP_DARK),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_GROVE),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JAGGED_PEAKS),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_LUSH_CAVES),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_OLD_GROWTH_TAIGA),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_MEADOW),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CHERRY_GROVE),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FROZEN_PEAKS),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SNOWY_SLOPES),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_STONY_PEAKS),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DESERT),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BADLANDS),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_JUNGLE),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SPARSE_JUNGLE),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BAMBOO_JUNGLE),
            Musics.createGameMusic(SoundEvents.MUSIC_UNDER_WATER)
    );

    public static final List<Music> NETHER_SITUATIONAL_MUSIC = List.of(
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_BASALT_DELTAS),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CRIMSON_FOREST),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_NETHER_WASTES),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SOUL_SAND_VALLEY),
            Musics.createGameMusic(SoundEvents.MUSIC_BIOME_WARPED_FOREST)
    );

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

    //? if <= 1.21.1 {
    public static Optional<Music> pickRandomSituationalMusic(LocalPlayer player) {
        if (ModConfig.unlockSituationalMusic) {
            if (player.level().random.nextFloat() < (float) ModConfig.situationalMusicChance / 100) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    return Optional.of(OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(OVERWORLD_SITUATIONAL_MUSIC.size())));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(NETHER_SITUATIONAL_MUSIC.size())));
                }
            }
            else if (player.isCreative()) return Optional.of(Musics.CREATIVE);
            else return ModConfig.creativeMusicInSurvival ? Optional.of(Musics.CREATIVE) : Optional.of(Musics.GAME);
        }
        return Optional.empty();
    }
    //?}

    //? if 1.21.4 {
    /*public static Optional<SimpleWeightedRandomList<Music>> pickRandomSituationalMusic(LocalPlayer player) {
        if (ModConfig.unlockSituationalMusic) {
            if (player.level().random.nextFloat() < (float) ModConfig.situationalMusicChance / 100) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    return Optional.of(SimpleWeightedRandomList.single(OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(OVERWORLD_SITUATIONAL_MUSIC.size()))));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(SimpleWeightedRandomList.single(NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(NETHER_SITUATIONAL_MUSIC.size()))));
                }
            }
            else if (player.isCreative()) return Optional.of(SimpleWeightedRandomList.single(Musics.CREATIVE));
            else return ModConfig.creativeMusicInSurvival ? Optional.of(SimpleWeightedRandomList.single(Musics.CREATIVE)) : Optional.of(SimpleWeightedRandomList.single(Musics.GAME));
        }
        return Optional.empty();
    }
    *///?}

    //? if 1.21.5 {
    /*public static Optional<WeightedList<Music>> pickRandomSituationalMusic(LocalPlayer player) {
        if (ModConfig.unlockSituationalMusic) {
            if (player.level().random.nextFloat() < (float) ModConfig.situationalMusicChance / 100) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    return Optional.of(WeightedList.of(OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(OVERWORLD_SITUATIONAL_MUSIC.size()))));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(WeightedList.of(NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(NETHER_SITUATIONAL_MUSIC.size()))));
                }
            }
            else if (player.isCreative()) return Optional.of(WeightedList.of(Musics.CREATIVE));
            else return ModConfig.creativeMusicInSurvival ? Optional.of(WeightedList.of(Musics.CREATIVE)) : Optional.of(WeightedList.of(Musics.GAME));
        }
        return Optional.empty();
    }
    *///?}

    @Override
    public void onInitializeClient() {
        ModKeybinds.init();
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
