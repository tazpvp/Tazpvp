package net.tazpvp.tazpvp.commands.gameplay.duel;

import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class DuelCommand extends NRCommand {
    public DuelCommand() {
        super(new Label("duel", null));

        setNativeExecutor((sender, args) -> {
            if (!(sender instanceof Player p)) {
                sendNoPermission(sender);
                return true;
            }

            p.sendMessage(CC.GREEN + "Duel Commands:\n" + "/duel <player> <type>\n" + "/duel accept");
            return true;
        });

        addSubcommand(new DuelSendCommand());
        addSubcommand(new DuelAcceptCommand());
        addSubcommand(new DuelSpectateCommand());
    }
}
