package me.pajic.simple_music_control.util;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.util.RandomSource;

public record MultipliedWeighted(Weighted<Sound> weighted, int multiplier) implements Weighted<Sound> {

    @Override
    public int getWeight() {
        return weighted.getWeight() * multiplier;
    }

    @Override
    public Sound getSound(RandomSource random) {
        return weighted.getSound(random);
    }

    @Override
    public void preloadIfRequired(SoundEngine soundEngine) {
        weighted.preloadIfRequired(soundEngine);
    }
}
