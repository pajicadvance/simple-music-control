package me.pajic.simple_music_control.mixin.client;

//? >=1.21.2 {

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelEventHandler.class)
public class LevelEventHandlerMixin {

    @Shadow @Final private Minecraft minecraft;

    @WrapMethod(method = "playJukeboxSong")
    private void onJukeboxPlay(Holder<JukeboxSong> songHolder, BlockPos pos, Operation<Void> original) {
        original.call(songHolder, pos);
        JukeboxTracker.onJukeboxPlay(minecraft.level, minecraft, pos);
    }

    @WrapMethod(method = "stopJukeboxSong")
    private void onJukeboxStop(BlockPos pos, Operation<Void> original) {
        original.call(pos);
        JukeboxTracker.onJukeboxStop(pos);
    }
}
//?}
