package net.tazpvp.tazpvp.utils.data;

import me.rownox.nrcore.utils.sql.SQLHelper;
import net.tazpvp.tazpvp.talents.Talents;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public final class PlayerData {
    private static final String NAME = "stats";
    private static final String ID_COLUMN = "ID";

    public static float get(OfflinePlayer p, QuantitativeData dataType) {
        return get(p.getUniqueId(), dataType);
    }
    public static float get(UUID uuid, QuantitativeData dataType) {
        return (float) getObject(uuid, dataType.getColumnIndex());
    }

    public static Talents getTalents(@Nonnull final  OfflinePlayer p) {
        return getTalents(p.getUniqueId());
    }

    public static Talents getTalents(@Nonnull final UUID uuid) {
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
        return SQLHelper.getObject(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnIndex);
    }

    public static void set(@Nonnull final OfflinePlayer p, @Nonnull final QuantitativeData dataType, final float value) {
        set(p.getUniqueId(), dataType, value);
    }

    public static void set(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType, final float value) {
        if (dataType.equals(QuantitativeData.TALENTS)) {
            Bukkit.getLogger().severe("CANNOT STORE INT AS JAVA CLASS IDIOT");
            return;
        } else if (dataType.equals(QuantitativeData.PLAYTIMEUNIX)) {
            setValueF(ID, dataType.getColumnName(), value);
        } else {
            setValue(ID, dataType.getColumnName(), (int) value);
        }
    }

    public static void setTalents(@Nonnull final OfflinePlayer p, @Nonnull final Talents talents) {
        setTalents(p.getUniqueId(), talents);
    }

    public static void setTalents(@Nonnull final UUID uuid, @Nonnull final Talents talents) {
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        BukkitObjectOutputStream data = null;
        try {
            data = new BukkitObjectOutputStream(str);
            data.writeObject(talents);
            data.close();
            setValueS(uuid, QuantitativeData.TALENTS.getColumnName(), Base64.getEncoder().encodeToString(str.toByteArray()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void setValue(@Nonnull final UUID ID, final String columnName, final int value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnName, value);
    }

    private static void setValueF(@Nonnull final UUID ID, final String columnName, final float value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnName, value);
    }

    private static void setValueS(@Nonnull final UUID ID, final String columnName, final String value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnName, "'" + value + "'");
    }

    public static void add(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType) {
        add(ID, dataType, 1);
    }

    public static void add(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType, final int amount) {
        if (dataType.equals(QuantitativeData.TALENTS)) {
            Bukkit.getLogger().severe("CANNOT ADD TO NON INT COLUMNS");
        }
        set(ID, dataType, get(ID, dataType) + amount);
    }

    public static void remove(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType) {
        remove(ID, dataType, 1);
    }

    public static void remove(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType, final int amount) {
        if (dataType.equals(QuantitativeData.TALENTS) || dataType.equals(QuantitativeData.PLAYTIMEUNIX)) {
            Bukkit.getLogger().severe("CANNOT MINUS TO NON INT COLUMNS");
        }
        set(ID, dataType, get(ID, dataType) - amount);
    }
}

