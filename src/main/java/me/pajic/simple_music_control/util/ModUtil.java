package me.pajic.simple_music_control.util;

import me.pajic.simple_music_control.config.ModClientConfig;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.Level;
//? if 1.21.4
/*import net.minecraft.util.random.SimpleWeightedRandomList;*/
//? if 1.21.5
/*import net.minecraft.util.random.WeightedList;*/

import java.util.Optional;

public class ModUtil {

    //? if <= 1.21.1 {
    public static Optional<Music> pickRandomSituationalMusic(LocalPlayer player) {
        if (ModClientConfig.unlockSituationalMusic) {
            if (player.level().random.nextFloat() < (float) ModClientConfig.situationalMusicChance / 100) {
                if (player.level().dimension() == Level.OVERWORLD) {
                    return Optional.of(Constants.OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(Constants.OVERWORLD_SITUATIONAL_MUSIC.size())));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(Constants.NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(Constants.NETHER_SITUATIONAL_MUSIC.size())));
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
                    return Optional.of(SimpleWeightedRandomList.single(Constants.OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(Constants.OVERWORLD_SITUATIONAL_MUSIC.size()))));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(SimpleWeightedRandomList.single(Constants.NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(Constants.NETHER_SITUATIONAL_MUSIC.size()))));
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
                    return Optional.of(WeightedList.of(Constants.OVERWORLD_SITUATIONAL_MUSIC.get(player.level().random.nextInt(Constants.OVERWORLD_SITUATIONAL_MUSIC.size()))));
                } else if (player.level().dimension() == Level.NETHER) {
                    return Optional.of(WeightedList.of(Constants.NETHER_SITUATIONAL_MUSIC.get(player.level().random.nextInt(Constants.NETHER_SITUATIONAL_MUSIC.size()))));
                }
            }
            else if (player.isCreative()) return Optional.of(WeightedList.of(Musics.CREATIVE));
            else return ModClientConfig.creativeMusicInSurvival ? Optional.of(WeightedList.of(Musics.CREATIVE)) : Optional.of(WeightedList.of(Musics.GAME));
        }
        return Optional.empty();
    }
    *///?}
}
