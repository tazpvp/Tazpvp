package net.tazpvp.tazpvp.commands.gameplay.PayCommand;

import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import org.bukkit.Bukkit;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand extends NRCommand {

    public PayCommand() {
        super(new Label("pay", "tazpvp.pay"));
    }

    public boolean onExecute(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2) {
            player.sendMessage("Usage: /pay <player> <amount>");
            return true;
        }

        String targetName = args[0];
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid amount. Please enter a number.");
            return true;
        }

        player.sendMessage("You paid " + targetName + " $" + amount);
        Player playerTarget = Bukkit.getPlayer(targetName);
        if (playerTarget == null) {

            return true;
        }
        if (PersistentData.getInt(player.getUniqueId(), DataTypes.COINS) >= amount) {
            PersistentData.add(playerTarget, DataTypes.COINS, amount);

            PersistentData.remove(player.getUniqueId(), DataTypes.COINS, amount);
            return true;

        }

        return false;
    }

}