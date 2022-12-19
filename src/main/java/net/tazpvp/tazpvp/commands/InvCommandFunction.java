package net.tazpvp.tazpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.ArmorManager;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

public class InvCommandFunction extends CommandCore implements CommandFunction {
    public InvCommandFunction() {
        super("inv", "invtest", "eeee");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof Player p) {
            ArmorManager.storeAndClearInventory(p);
        }
    }
}
