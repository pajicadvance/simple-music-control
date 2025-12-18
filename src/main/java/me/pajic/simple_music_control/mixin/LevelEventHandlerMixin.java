package me.pajic.simple_music_control.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.kikugie.fletching_table.annotation.MixinEnvironment;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelEventHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.item.JukeboxSong;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@MixinEnvironment(type = MixinEnvironment.Env.CLIENT)
@Mixin(LevelEventHandler.class)
public class LevelEventHandlerMixin {

    @Shadow @Final private Minecraft minecraft;
    @Shadow @Final private ClientLevel level;

    @WrapMethod(method = "playJukeboxSong")
    private void onJukeboxPlay(Holder<JukeboxSong> song, BlockPos pos, Operation<Void> original) {
        original.call(song, pos);
        JukeboxTracker.onJukeboxPlay(level, minecraft, pos);
    }

    @WrapMethod(method = "stopJukeboxSong")
    private void onJukeboxStop(BlockPos pos, Operation<Void> original) {
        original.call(pos);
        JukeboxTracker.onJukeboxStop(pos);
    }
}
