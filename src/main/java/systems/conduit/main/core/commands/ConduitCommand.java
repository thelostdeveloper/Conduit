package systems.conduit.main.core.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import systems.conduit.main.Conduit;
import systems.conduit.main.core.commands.conduit.DimensionsCommand;
import systems.conduit.main.core.commands.conduit.PermissionsCommand;
import systems.conduit.main.core.commands.conduit.PluginsCommand;
import systems.conduit.main.core.commands.conduit.VersionCommand;
import systems.conduit.main.core.utils.PermissionUtils;

/**
 * @author Innectic
 * @since 10/25/2020
 */
public class ConduitCommand extends BaseCommand {

    @Override
    public LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("conduit").requires(ctx -> PermissionUtils.checkPermissions(ctx, "conduit.admin", false, true)).executes(c -> {
            c.getSource().sendSuccess(new TextComponent("Conduit v").append(Conduit.getVersion()), false);
            c.getSource().sendSuccess(new TextComponent("Minecraft Server v" + SharedConstants.getCurrentVersion().getName().split("/")[0]), false);
            return 1;
        })
                .then(VersionCommand.getCommand())
                .then(PluginsCommand.baseCommand()
                        .then(PluginsCommand.reloadSubcommand())
                        .then(PluginsCommand.changeStateSubcommand(true))
                        .then(PluginsCommand.changeStateSubcommand(false))
                        .then(PluginsCommand.listPlugins()))
                .then(DimensionsCommand.baseCommand()
                        .then(DimensionsCommand.listCommand())
                        .then(DimensionsCommand.teleportCommand()))
                .then(PermissionsCommand.baseCommand()
                        .then(PermissionsCommand.listPermissions())
                        .then(PermissionsCommand.addPermission())
                        .then(PermissionsCommand.removePermission()));
    }
}
