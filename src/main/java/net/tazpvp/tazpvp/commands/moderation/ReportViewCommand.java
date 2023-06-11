package net.tazpvp.tazpvp.commands.moderation;

import lombok.NonNull;
import org.bukkit.command.CommandSender;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class ReportViewCommand extends NRCommand {
    public ReportViewCommand() {
        super(new Label("viewreport", "tazpvp.staff"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {
        // TODO: finish this lol
        return true;
    }
}

