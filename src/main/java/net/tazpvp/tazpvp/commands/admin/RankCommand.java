package net.tazpvp.tazpvp.commands.admin;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class RankCommand extends NRCommand {
    public RankCommand() {
        super(new Label("rank", "tazpvp.rank"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (!(sender instanceof Player p)) {
            return true;
        }

        if (!p.hasPermission(this.getLabel().getPermission())) {
            p.sendMessage(CC.RED + "You do not have permission to run this command.");
            return true;
        }



        return true;
    }
}
