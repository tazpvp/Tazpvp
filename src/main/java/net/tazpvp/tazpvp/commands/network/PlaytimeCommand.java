package net.tazpvp.tazpvp.commands.network;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.PlaytimeUtil;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import net.tazpvp.tazpvp.utils.enums.CC;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class PlaytimeCommand extends NRCommand {
    public PlaytimeCommand() {
        super(new Label("playtime", null, "pt"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {
        if (!(sender instanceof Player p)) {
            sendNoPermission(sender);
            return true;
        }

        OfflinePlayer target;

        if (args.length > 0)
            target = Bukkit.getOfflinePlayer(args[0]);
        else
            target = p;

        int playtimeUnix = PersistentData.getInt(target.getUniqueId(), DataTypes.PLAYTIMEUNIX);
        long playtimeSeconds = playtimeUnix / 1000;
        String formattedPlaytime = PlaytimeUtil.secondsToDDHHMMSS(playtimeSeconds);

        p.sendMessage(CC.GOLD + "" + CC.BOLD + target.getName() + "'s Playtime: " + CC.YELLOW + formattedPlaytime);

        return true;
    }
}
