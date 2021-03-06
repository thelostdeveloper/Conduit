package systems.conduit.main.mixins.server.command;

import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import systems.conduit.main.Conduit;

import java.util.UUID;

@Mixin(value = CommandSourceStack.class, remap = false)
public abstract class CommandSourceMixin {

    @Shadow @Final private CommandSource source;

    @Shadow public abstract Entity getEntity();

    @Redirect(method = "sendSuccess", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/CommandSource;sendMessage(Lnet/minecraft/network/chat/Component;)V"))
    private void sendSuccess(CommandSource source, Component component) {
        sendColoredString(component);
    }

    @Redirect(method = "sendFailure", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/CommandSource;sendMessage(Lnet/minecraft/network/chat/Component;)V"))
    private void sendFailure(CommandSource source, Component component) {
        sendColoredString((new TextComponent("")).append(component).withStyle(ChatFormatting.RED));
    }

    private void sendColoredString(Component component) {
        if (this.getEntity() == null) {
            Conduit.getLogger().info(new TextComponent(component.getContents()).getText());
        } else {
            this.source.sendMessage(component, UUID.randomUUID());
        }
    }
}
