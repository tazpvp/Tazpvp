package net.tazpvp.tazpvp.commands;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.UUID;

public class PremiumCommandFunction extends CommandCore implements CommandFunction {
    public PremiumCommandFunction() {
        super("premium", "setprem", "prem");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            Player p = Bukkit.getPlayer(args[0]);
            if (p != null) {
                UUID uuid = p.getUniqueId();
                boolean premiumStatus = PersistentData.isPremium(uuid);
                PersistentData.setValueB(uuid, DataTypes.PREMIUM.getColumnName(), !premiumStatus);
            }
        }
    }
}
