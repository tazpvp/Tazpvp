package net.tazpvp.tazpvp.guild;

import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.GuildData;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.OfflinePlayer;

import java.util.*;

public final class GuildUtils {

    public static boolean isInGuild(OfflinePlayer p) {
        return !PersistentData.getString(p, DataTypes.GUILD_ID).equals("n");
    }

    public static Guild getGuildPlayerIn(OfflinePlayer p) {
        String id = PersistentData.getString(p, DataTypes.GUILD_ID);
        return GuildData.getGuild(UUID.fromString(id));
    }

    public static LinkedHashMap<UUID, Integer> getSortedGuilds() {
        LinkedHashMap<UUID, Integer> map  = new LinkedHashMap<>();
        for (Guild g : GuildData.getAllGuilds()) {
            map.put(g.getID(), g.getKills());
        }
        return sortByValue(map);
    }

    public static LinkedHashMap<UUID, Integer> sortByValue(HashMap<UUID, Integer> hm) {
        List<Map.Entry<UUID, Integer> > list = new LinkedList<>(hm.entrySet());
        list.sort(Map.Entry.<UUID,Integer>comparingByValue().reversed());

        LinkedHashMap<UUID, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<UUID, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
