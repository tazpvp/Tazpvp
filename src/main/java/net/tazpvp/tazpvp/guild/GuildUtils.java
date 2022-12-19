package net.tazpvp.tazpvp.guild;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.GuildData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public final class GuildUtils {

    public boolean isInGuild(OfflinePlayer p) {
        return PersistentData.getString(p, DataTypes.GUILD_ID).equals("n");
    }

    public Guild getGuildPlayerIn(OfflinePlayer p) {
        String id = PersistentData.getString(p, DataTypes.GUILD_ID);
        return GuildData.getGuild(UUID.fromString(id));
    }
}
