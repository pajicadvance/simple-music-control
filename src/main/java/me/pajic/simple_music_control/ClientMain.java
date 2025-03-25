package me.pajic.simple_music_control;

import me.pajic.simple_music_control.config.ModClientConfig;
import me.pajic.simple_music_control.keybind.ModKeybinds;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
//? if 1.21.4
/*import net.minecraft.util.random.SimpleWeightedRandomList;*/
//? if 1.21.5
/*import net.minecraft.util.random.WeightedList;*/

import java.util.List;
import java.util.Optional;

@Mod(value = "simple_music_control", dist = Dist.CLIENT)
public class ClientMain {

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

    //? if <= 1.21.1 {
    public static Optional<Music> pickRandomSituationalMusic(LocalPlayer player) {
        if (ModClientConfig.unlockSituationalMusic) {
            if (player.level().random.nextFloat() < (float) ModClientConfig.situationalMusicChance / 100) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    return Optional.of(ClientMain.OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(ClientMain.OVERWORLD_SITUATIONAL_MUSIC.size())));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(ClientMain.NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(ClientMain.NETHER_SITUATIONAL_MUSIC.size())));
                }
            }
            else if (player.isCreative()) return Optional.of(Musics.CREATIVE);
            else return ModClientConfig.creativeMusicInSurvival ? Optional.of(Musics.CREATIVE) : Optional.of(Musics.GAME);
        }
        return Optional.empty();
    }
    //?}

    //? if 1.21.4 {
    /*public static Optional<SimpleWeightedRandomList<Music>> pickRandomSituationalMusic(LocalPlayer player) {
        if (ModClientConfig.unlockSituationalMusic) {
            if (player.level().random.nextFloat() < (float) ModClientConfig.situationalMusicChance / 100) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    return Optional.of(SimpleWeightedRandomList.single(ClientMain.OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(ClientMain.OVERWORLD_SITUATIONAL_MUSIC.size()))));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(SimpleWeightedRandomList.single(ClientMain.NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(ClientMain.NETHER_SITUATIONAL_MUSIC.size()))));
                }
            }
            else if (player.isCreative()) return Optional.of(SimpleWeightedRandomList.single(Musics.CREATIVE));
            else return ModClientConfig.creativeMusicInSurvival ? Optional.of(SimpleWeightedRandomList.single(Musics.CREATIVE)) : Optional.of(SimpleWeightedRandomList.single(Musics.GAME));
        }
        return Optional.empty();
    }
    *///?}

    //? if 1.21.5 {
    /*public static Optional<WeightedList<Music>> pickRandomSituationalMusic(LocalPlayer player) {
        if (ModClientConfig.unlockSituationalMusic) {
            if (player.level().random.nextFloat() < (float) ModClientConfig.situationalMusicChance / 100) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    return Optional.of(WeightedList.of(OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(OVERWORLD_SITUATIONAL_MUSIC.size()))));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(WeightedList.of(NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(NETHER_SITUATIONAL_MUSIC.size()))));
                }
            }
            else if (player.isCreative()) return Optional.of(WeightedList.of(Musics.CREATIVE));
            else return ModClientConfig.creativeMusicInSurvival ? Optional.of(WeightedList.of(Musics.CREATIVE)) : Optional.of(WeightedList.of(Musics.GAME));
        }
        return Optional.empty();
    }
    *///?}

    public ClientMain(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(ModKeybinds::registerKeybinds);
        modContainer.registerConfig(ModConfig.Type.CLIENT, ModClientConfig.CLIENT_SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
