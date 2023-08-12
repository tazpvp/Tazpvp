package net.tazpvp.tazpvp.commands.admin;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.data.PlayerRankData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Completer;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;
import java.util.UUID;

public class PremiumCommandFunction extends NRCommand {

    public PremiumCommandFunction() {
        super(new Label("premium", "is.op"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (args.length > 0) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                UUID uuid = target.getUniqueId();
                boolean isPremium = PlayerRankData.isPremium(uuid);

                if (!isPremium) {
                    PlayerRankData.setPremium(uuid, true);
                } else {
                    target.sendMessage("This user is already premium.");
                }
            }
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return Completer.onlinePlayers(args[0]);
        }
        return List.of();
    }
}
