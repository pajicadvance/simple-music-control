package me.pajic.simple_music_control.util;

import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;

import java.util.List;

public class Constants {

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
}
