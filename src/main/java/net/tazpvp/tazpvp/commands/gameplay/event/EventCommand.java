package net.tazpvp.tazpvp.commands.gameplay.event;

import net.tazpvp.tazpvp.commands.gameplay.event.sub.EventJoin;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class EventCommand extends NRCommand {

    public EventCommand() {
        super(new Label("event", "tazpvp.events", "events"));

        addSubcommand(new EventJoin());
    }


}
