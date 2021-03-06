package systems.conduit.main.mixins.player;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import systems.conduit.main.Conduit;
import systems.conduit.main.core.events.types.WorldEvents;

@Mixin(value = ServerPlayerGameMode.class, remap = false)
public abstract class ServerPlayerGameModeMixin {

    @Shadow @Final protected ServerPlayer player;
    @Shadow protected ServerLevel level;

    @Inject(method = "destroyBlock", at = @At("HEAD"))
    private void destroyAndAck(BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        // TODO: Event cancellations
        WorldEvents.BlockBreakEvent event = new WorldEvents.BlockBreakEvent((systems.conduit.main.api.mixins.ServerPlayer) player, level.getBlockState(blockPos));
        Conduit.getEventManager().dispatchEvent(event);
        if (event.isCanceled()) return;
    }
}
