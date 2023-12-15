package net.tazpvp.tazpvp.commands.admin;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.crate.KeyFactory;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class KeyallCommand extends NRCommand {
    public KeyallCommand() {
        super(new Label("keyall", "tazpvp.key"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            return true;
        }

        for (Player op : Bukkit.getOnlinePlayers()) {
            op.getInventory().addItem(KeyFactory.getFactory().createCommonKey());
            op.getInventory().addItem(KeyFactory.getFactory().createRareKey());
            op.getInventory().addItem(KeyFactory.getFactory().createMythicKey());
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return super.complete(sender, args);
    }
}
