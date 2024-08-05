package net.tazpvp.tazpvp.commands.admin;

import lombok.NonNull;
import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.game.crates.KeyFactory;
import net.tazpvp.tazpvp.helpers.ChatHelper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class KeyallCommand extends NRCommand {
    public KeyallCommand() {
        super(new Label("keyall", "tazpvp.keyall"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

        if (!(sender instanceof Player p)) {
            return true;
        }

        for (Player op : Bukkit.getOnlinePlayers()) {
            op.getInventory().addItem(KeyFactory.getFactory().createCommonKey());
            op.getInventory().addItem(KeyFactory.getFactory().createRareKey());
            op.getInventory().addItem(KeyFactory.getFactory().createMythicKey());
        }
        ChatHelper.announce(CC.LIGHT_PURPLE.BOLD + "KEYALL" + CC.WHITE + " - Everyone has been given crate keys!\n", Sound.BLOCK_AMETHYST_BLOCK_CHIME);

        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        return super.complete(sender, args);
    }
}
