package net.tazpvp.tazpvp.utils.data;

import net.tazpvp.tazpvp.guild.Guild;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import world.ntdi.nrcore.utils.sql.SQLHelper;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
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
        ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(SQLHelper.getString(NAME, ID_COLUMN, "'" + uuid.toString() + "'", 2)));
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

    public static void setGuild(@Nonnull final UUID uuid, @Nonnull final Guild guild) {
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        BukkitObjectOutputStream data = null;
        try {
            data = new BukkitObjectOutputStream(str);
            data.writeObject(guild);
            data.close();
            SQLHelper.updateValue(NAME, ID_COLUMN, "'" + uuid + "'", "guild", "'" + Base64.getEncoder().encodeToString(str.toByteArray()) + "'");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
