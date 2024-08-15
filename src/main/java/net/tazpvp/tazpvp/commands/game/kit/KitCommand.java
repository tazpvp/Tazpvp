package net.tazpvp.tazpvp.commands.game.kit;

import lombok.NonNull;
import net.tazpvp.tazpvp.data.implementations.KitServiceImpl;
import net.tazpvp.tazpvp.game.kits.KitMakerGui;
import net.tazpvp.tazpvp.services.KitMakerServiceImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class KitCommand extends NRCommand {
    public KitCommand() {
        super(new Label("kit", null));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        KitMakerGui kitMakerGui = new KitMakerGui(new KitMakerServiceImpl(), new KitServiceImpl(), p);

        // this should be all we gootta do fr

        return true;
    }
}
