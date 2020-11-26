package systems.conduit.main.mixins.api;

import lombok.Getter;
import net.minecraft.util.ProgressListener;
import net.minecraft.world.level.storage.ServerLevelData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import systems.conduit.main.Conduit;
import systems.conduit.main.api.Entity;
import systems.conduit.main.api.ServerLevel;
import systems.conduit.main.api.ServerPlayer;
import systems.conduit.main.events.types.WorldEvents;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Innectic
 * @since 10/24/2020
 */
@Mixin(value = net.minecraft.server.level.ServerLevel.class, remap = false)
public abstract class ServerLevelMixin implements ServerLevel {

    @Shadow @Final @Getter private List<ServerPlayer> players;
    @Shadow @Final @Getter private Map<UUID, Entity> entitiesByUuid;
    @Shadow @Final @Getter private ServerLevelData serverLevelData;

    @Override
    public Optional<Entity> getEntityByUuid(UUID uuid) {
        return Optional.ofNullable(entitiesByUuid.getOrDefault(uuid, null));
    }

    @Inject(method = "save", at = @At("HEAD"))
    public void onLevelSave(ProgressListener progressListener, boolean b, boolean b1, CallbackInfo ci) {
        WorldEvents.WorldSaveEvent event = new WorldEvents.WorldSaveEvent(this);
        Conduit.getEventManager().dispatchEvent(event);
    }
}
