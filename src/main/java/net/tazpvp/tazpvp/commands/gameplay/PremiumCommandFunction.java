package net.tazpvp.tazpvp.commands.gameplay;

import net.tazpvp.tazpvp.utils.data.PlayerRankData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.List;
import java.util.UUID;

public class PremiumCommandFunction extends CommandCore implements CommandFunction {
    public PremiumCommandFunction() {
        super("premium", "manager", "prem");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                UUID uuid = p.getUniqueId();
                boolean isPremium = PlayerRankData.isPremium(uuid);

                if (!isPremium) {
                    PlayerRankData.setPremium(uuid, true);
                } else {
                    p.sendMessage("This user is already premium.");
                }
            }
        }
    }

    @Override
    public List<String> tabCompletion(CommandSender commandSender, String[] strings) {
        return List.of("");
    }
}
