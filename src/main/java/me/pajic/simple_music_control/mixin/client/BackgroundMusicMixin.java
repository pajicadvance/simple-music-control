package me.pajic.simple_music_control.mixin.client;

//? >=26.1 {

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.pajic.simple_music_control.SMC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.level.Level;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.Random;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Mixin(BackgroundMusic.class)
public class BackgroundMusicMixin {

	@ModifyExpressionValue(
			method = "select",
			at = @At(
					value = "FIELD",
					target = "Lnet/minecraft/world/attribute/BackgroundMusic;defaultMusic:Ljava/util/Optional;",
					opcode = Opcodes.GETFIELD
			)
	)
	private Optional<Music> allowCreativeMusicInSurvival(Optional<Music> original) {
		if (original.isPresent() && SMC.CONFIG.creativeMusicInSurvival.get()) {
			ClientLevel level = Minecraft.getInstance().level;
			if (original.get().equals(Musics.GAME)) return Optional.of(Musics.CREATIVE);
			else if (level != null && level.dimension() == Level.OVERWORLD) {
				return new Random().nextBoolean() ? original : Optional.of(Musics.CREATIVE);
			}
		}
		return original;
	}
}
//?}
