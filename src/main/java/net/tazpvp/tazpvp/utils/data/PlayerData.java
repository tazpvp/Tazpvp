package net.tazpvp.tazpvp.utils.data;

import me.rownox.nrcore.utils.sql.SQLHelper;
import org.bukkit.OfflinePlayer;
import org.bukkit.util.io.BukkitObjectInputStream;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public final class PlayerData {
    private static final String NAME = "stats";

    public static float get(OfflinePlayer p, QuantitativeData dataType) {
        return get(p.getUniqueId(), dataType);
    }
    public static float get(UUID uuid, QuantitativeData dataType) {
        return (float) getObject(uuid, dataType.getColumnIndex());
    }

    public static Talents getTalents(OfflinePlayer p) {
        return getTalents(p.getUniqueId());
    }

    public static Talents getTalents(UUID uuid) {
        ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode((String) getObject(uuid, QuantitativeData.TALENTS.getColumnIndex())));
        BukkitObjectInputStream data = null;
        try {
            data = new BukkitObjectInputStream(stream);
            Talents talents = (Talents) data.readObject();
            data.close();
            return talents;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getObject(@Nonnull final UUID ID, final int columnIndex) {
        return SQLHelper.getObject(NAME, "ID", ID.toString(), columnIndex);
    }

    public static void set(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType, final float value) {

    }

    private static void setValue(@Nonnull final UUID ID, final String columnName, final float value) {
        SQLHelper.updateValue(NAME, "ID", "'" + ID.toString() + "'", columnName, value);
    }
}

