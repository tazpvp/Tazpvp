package net.tazpvp.tazpvp.utils.data;

import net.tazpvp.tazpvp.guild.Guild;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import world.ntdi.nrcore.utils.sql.SQLHelper;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class GuildData {
    /**
     * Name of the stats table
     */
    private static final String NAME = "guilds";
    /**
     * Name of the ID Column
     */
    private static final String ID_COLUMN = "ID";

    public static Guild getGuild(@Nonnull final UUID uuid) {
        ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(SQLHelper.getString(NAME, ID_COLUMN, "'" + uuid + "'", 2)));
        BukkitObjectInputStream data = null;
        try {
            data = new BukkitObjectInputStream(stream);
            Guild obj = (Guild) data.readObject();
            data.close();
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initializeGuild(@Nonnull final UUID uuid, @Nonnull final Guild guild) {
        SQLHelper.initializeValues(NAME, "ID, GUILD", "'" + uuid + "', '" + guildToString(guild) + "'");
    }

    public static void setGuild(@Nonnull final UUID uuid, @Nonnull final Guild guild) {
        setValueS(uuid, guildToString(guild));
    }

    public static String guildToString(@Nonnull final Guild g) {
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        BukkitObjectOutputStream data = null;
        try {
            data = new BukkitObjectOutputStream(str);
            data.writeObject(g);
            data.close();
            return Base64.getEncoder().encodeToString(str.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getString(@Nonnull final UUID uuid) {
        return SQLHelper.getString(NAME, ID_COLUMN, uuid.toString(), 2);
    }

    private static void setValueS(@Nonnull final UUID ID, final String value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", "guild", "'" + value + "'");
    }

    public static List<Guild> getAllGuilds() {
        List<Guild> g = new ArrayList<>();
        SQLHelper.getListOfColumn(NAME, "guild").forEach(guild -> g.add((Guild) guild));
        return g;
    }

    public static void deleteGuild(UUID id) {
        SQLHelper.deleteRow(NAME, ID_COLUMN, id.toString());
    }
}
