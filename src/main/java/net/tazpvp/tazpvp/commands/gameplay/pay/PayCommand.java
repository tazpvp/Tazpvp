package net.tazpvp.tazpvp.commands.gameplay.pay;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.DataTypes;
import net.tazpvp.tazpvp.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class PayCommand extends NRCommand {

    public PayCommand() {
        super(new Label("pay", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NonNull String[] args) {

        if (!(sender instanceof Player p)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length != 2) {
            sendIncorrectUsage(sender, "/" + super.getLabel() + " <user> <amount>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            p.sendMessage(CC.RED + "Player not found.");
            return true;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            p.sendMessage(CC.RED + "Invalid amount.");
            return true;
        }

        if (amount < 1) {
            p.sendMessage(CC.RED + "Amount must be greater than 0.");
            return true;
        }

        if (PersistentData.getInt(p, DataTypes.COINS) >= amount) {
            PersistentData.remove(p, DataTypes.COINS, amount);
            PersistentData.add(target, DataTypes.COINS, amount);
            p.sendMessage(CC.GREEN + "You have paid " + target.getName() + " " + amount + " coins.");
            target.sendMessage(CC.GREEN + p.getName() + " has paid you " + amount + " coins.");
        } else {
            p.sendMessage(CC.RED + "You do not have enough coins.");
            return true;
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Completer.onlinePlayers(args[0]);
        }
        return List.of();
    }
}
