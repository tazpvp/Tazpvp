package net.tazpvp.tazpvp.commands.admin.hide;

import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class HideCommand extends NRCommand {
    public HideCommand() {
        super(new Label("hide", "tazpvp.hide"));
        setNativeExecutor(((commandSender, strings) -> {
            if (!commandSender.hasPermission(getLabel().getPermission())) {
                sendNoPermission(commandSender);
                return true;
            }
            commandSender.sendMessage("Wrong Usage, use tab completion");
            return true;
        }));

        addSubcommand(new HideFromSelfCommand());
    }
}
