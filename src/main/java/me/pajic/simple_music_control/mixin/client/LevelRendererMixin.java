package me.pajic.simple_music_control.mixin.client;

//? <26.1 {

/*import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import me.pajic.simple_music_control.util.JukeboxTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;import net.minecraft.core.Holder;import net.minecraft.world.item.JukeboxSong;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow @Nullable private ClientLevel level;
    @Shadow @Final private Minecraft minecraft;

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
*///?}
