package net.tazpvp.tazpvp.commands.network;

import lombok.NonNull;
import net.tazpvp.tazpvp.utils.PlaytimeUtil;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
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

        int playtimeUnix = PersistentData.getInt(p.getUniqueId(), DataTypes.PLAYTIMEUNIX);
        long playtimeSeconds = playtimeUnix / 1000;
        String formattedPlaytime = PlaytimeUtil.secondsToDDHHMMSS(playtimeSeconds);

        p.sendMessage("Your total playtime: " + formattedPlaytime);

        return true;
    }
}
