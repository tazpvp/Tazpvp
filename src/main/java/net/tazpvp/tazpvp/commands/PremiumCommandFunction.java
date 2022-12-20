package net.tazpvp.tazpvp.commands;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.command.CommandSender;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

import java.util.UUID;

public class PremiumCommandFunction extends CommandCore implements CommandFunction {
    public PremiumCommandFunction() {
        super("premium", "setprem", "pem");
        setDefaultFunction(this);
    }


    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (args.length > 0) {
            UUID uuid = UUID.fromString(args[0]);
            boolean premiumStatus = PersistentData.isPremium(uuid);
            PersistentData.setValueB(uuid, DataTypes.PREMIUM.getColumnName(), !premiumStatus);
        }
    }
}
