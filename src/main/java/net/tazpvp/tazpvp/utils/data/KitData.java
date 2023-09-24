package net.tazpvp.tazpvp.utils.data;

import net.tazpvp.tazpvp.Tazpvp;
import org.checkerframework.checker.units.qual.C;
import world.ntdi.postglam.connection.Database;
import world.ntdi.postglam.data.DataTypes;
import world.ntdi.postglam.sql.module.Column;
import world.ntdi.postglam.sql.module.Row;
import world.ntdi.postglam.sql.module.Table;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class KitData  {
    private static final Table table;

    static {
        try {
            table = new Table(Tazpvp.getDatabase(), "kit", Map.entry("id", DataTypes.UUID), new LinkedHashMap<>(
                    Map.of(
                            "serial", DataTypes.TEXT
                    )
                )
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSerial(@Nonnull final UUID uuid, @Nonnull final String serial) {
        try {
            new Row(table, uuid.toString()).update(new Column(table, "serial"), serial);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSerial(@Nonnull final UUID uuid) {
        try {
            return (String) new Row(table, uuid.toString()).fetch(new Column(table, "serial"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Initialize uuid into table
     * @param uuid UUID Target
     */
    public static void initRank(@Nonnull final UUID uuid) {
        if (hasRank(uuid)) return;

        try {
            new Row(table, uuid.toString(), "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if they are inside rank table
     * @param uuid UUID target
     * @return if player is inside rank table
     */
    public static boolean hasRank(@Nonnull final UUID uuid) {
        try {
            return table.doesRowExist(uuid.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
