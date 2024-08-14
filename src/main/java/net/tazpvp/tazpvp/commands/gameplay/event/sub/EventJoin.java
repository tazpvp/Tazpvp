package net.tazpvp.tazpvp.commands.gameplay.event.sub;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class EventJoin extends NRCommand {

    public EventJoin() {
        super(new Label("join", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {


        return super.execute(sender, args);
    }
}
