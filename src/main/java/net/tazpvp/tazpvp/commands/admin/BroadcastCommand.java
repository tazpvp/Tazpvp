package net.tazpvp.tazpvp.commands.admin;

import lombok.NonNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class BroadcastCommand extends NRCommand {
    public BroadcastCommand() {
        super(new Label("broadcast", "tazpvp.broadcast"));
    }
}
