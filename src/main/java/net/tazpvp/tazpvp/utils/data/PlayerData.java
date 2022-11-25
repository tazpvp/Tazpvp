package net.tazpvp.tazpvp.utils.data;

import me.rownox.nrcore.utils.sql.SQLHelper;
import net.tazpvp.tazpvp.talents.Talents;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public final class PlayerData {
    /**
     * Name of the stats table
     */
    private static final String NAME = "stats";
    /**
     * Name of the ID Column
     */
    private static final String ID_COLUMN = "ID";

    /**
     * Initialize a player into the database by first checking if they are already inside the db
     * @param p The targeted player
     */
    public static void initPlayer(OfflinePlayer p) {
        initPlayer(p.getUniqueId());
    }

    /**
     * Initialize a player into the database by first checking if they are already inside the db
     * @param uuid The UUID of the player
     */
    public static void initPlayer(UUID uuid) {
        if (!SQLHelper.ifRowExists(NAME, ID_COLUMN, uuid.toString())) {
            SQLHelper.initializeValues(NAME, "ID, COINS, XP, LEVEL, KILLS, DEATHS, TOP_KS, PRESTIGE, REBIRTH, PLAYTIME, TALENTS, ACHIEVEMENTS", "'" + uuid.toString() + "'", "0", "0", "0", "0", "0", "0", "0", "0", "0", "'abc'", "'def'");
            setTalents(uuid, new Talents());
            //set achiefvements when rownox decides to push liek
        }
    }

    /**
     * Get quantitative data (ints) from the database
     * @param p The targeted player
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static int getInt(OfflinePlayer p, QuantitativeData dataType) {
        return getInt(p.getUniqueId(), dataType);
    }
    /**
     * Get quantitative data (ints) from the database
     * @param uuid The targeted UUID
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static int getInt(UUID uuid, QuantitativeData dataType) {
        return getInt(uuid, dataType.getColumnIndex());
    }

    /**
     * Get quantitative data (String) from the database
     * @param p The targeted player
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static String getString(OfflinePlayer p, QuantitativeData dataType) {
        return getString(p.getUniqueId(), dataType);
    }
    /**
     * Get quantitative data (String) from the database
     * @param uuid The targeted UUID
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static String getString(UUID uuid, QuantitativeData dataType) {
        return getString(uuid, dataType.getColumnIndex());
    }

    /**
     * Get the talent object from the serialized column
     * @param p The targeted player
     * @return The Talents object stored in the column
     */
    public static Talents getTalents(@Nonnull final OfflinePlayer p) {
        return getTalents(p.getUniqueId());
    }

    /**
     * Get the talent object from the serialized column
     * @param uuid the targeted uuid
     * @return The Talents object stored in the column
     */
    public static Talents getTalents(@Nonnull final UUID uuid) {
        if (getObject(uuid, QuantitativeData.TALENTS.getColumnIndex()) == null) {
            return new Talents();
        }
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

    /**
     * Get an Object value of a sql column
     * @param ID the targeted UUID
     * @param columnIndex the index of the column
     * @return the Object value of the requested column
     */
    private static Object getObject(@Nonnull final UUID ID, final int columnIndex) {
        return SQLHelper.getObject(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnIndex);
    }

    /**
     * Get an Object value of a sql column
     * @param ID the targeted UUID
     * @param columnIndex the index of the column
     * @return the Object value of the requested column
     */
    private static String getString(@Nonnull final UUID ID, final int columnIndex) {
        return SQLHelper.getString(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnIndex);
    }

    /**
     * Get an Object value of a sql column
     * @param ID the targeted UUID
     * @param columnIndex the index of the column
     * @return the Object value of the requested column
     */
    private static int getInt(@Nonnull final UUID ID, final int columnIndex) {
        return SQLHelper.getInt(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnIndex);
    }

    /**
     * Get an Object value of a sql column
     * @param ID the targeted UUID
     * @param columnIndex the index of the column
     * @return the Object value of the requested column
     */
    private static float getFloat(@Nonnull final UUID ID, final int columnIndex) {
        return SQLHelper.getFloat(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnIndex);
    }

    /**
     * Set a quantitative datatype to a new value
     * @param p The targeted Player
     * @param dataType the datatype
     * @param value the new value for the datatype
     */
    public static void set(@Nonnull final OfflinePlayer p, @Nonnull final QuantitativeData dataType, final float value) {
        set(p.getUniqueId(), dataType, value);
    }

    /**
     * Set a quantitative datatype to a new value
     * @param ID the targeted UUID
     * @param dataType the datatype
     * @param value the new value for the datatype
     */
    public static void set(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType, final float value) {
        if (dataType.equals(QuantitativeData.TALENTS) || dataType.equals(QuantitativeData.ACHIEVEMENTS)) {
            Bukkit.getLogger().severe("CANNOT STORE INT AS JAVA CLASS IDIOT");
            return;
        } else if (dataType.equals(QuantitativeData.PLAYTIMEUNIX)) {
            setValueF(ID, dataType.getColumnName(), value);
        } else {
            setValue(ID, dataType.getColumnName(), (int) value);
            if (dataType.equals(QuantitativeData.XP)) {
                if (getInt(ID, QuantitativeData.XP) >= levelFormula((int) getInt(ID, QuantitativeData.LEVEL))) {
                    add(ID, QuantitativeData.LEVEL);
                    set(ID, QuantitativeData.XP, 0);
                    // TODO: Add level up messages/functions
                }
            }
            Player p = Bukkit.getPlayer(ID);
            if (p != null) {
                p.getScoreboard().getTeam(dataType.getColumnName()).setSuffix(value + "");
                if (dataType.equals(QuantitativeData.KILLS) || dataType.equals(QuantitativeData.DEATHS)) {
                    p.getScoreboard().getTeam("kdr").setSuffix(kdrFormula((int) getInt(p, QuantitativeData.KILLS), (int) getInt(p, QuantitativeData.DEATHS)) + "");
                }
            }
        }
    }

    /**
     * Set the talents object to the column
     * @param p the targeted player
     * @param talents the talents object
     */
    public static void setTalents(@Nonnull final OfflinePlayer p, @Nonnull final Talents talents) {
        setTalents(p.getUniqueId(), talents);
    }

    /**
     * Set the talents object to the column
     * @param uuid the targeted uuid
     * @param talents the talents object
     */
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

    /**
     * Set the value of a int in a column
     * @param ID the targeted UUID
     * @param columnName the column name
     * @param value the new value
     */
    private static void setValue(@Nonnull final UUID ID, final String columnName, final int value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnName, value);
    }

    /**
     * Set the value of a float in a column
     * @param ID the targetd UUID
     * @param columnName the column name
     * @param value the new value
     */
    private static void setValueF(@Nonnull final UUID ID, final String columnName, final float value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnName, value);
    }

    /**
     * Set the value of a String in a column
     * @param ID the targeted UUID
     * @param columnName the column name
     * @param value the new value
     */
    private static void setValueS(@Nonnull final UUID ID, final String columnName, final String value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnName, "'" + value + "'");
    }

    /**
     * Add 1 to a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     */
    public static void add(@Nonnull final OfflinePlayer p, @Nonnull final QuantitativeData dataType) {
        add(p.getUniqueId(), dataType, 1);
    }

    /**
     * Add a custom amount to a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     * @param amount the desired amount to add
     */
    public static void add(@Nonnull final OfflinePlayer p, @Nonnull final QuantitativeData dataType, final int amount) {
        add(p.getUniqueId(), dataType, amount);
    }

    /**
     * Add 1 to a quantitative datatype
     * @param ID the targeted UUID
     * @param dataType the Datatype
     */
    public static void add(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType) {
        add(ID, dataType, 1);
    }

    /**
     * Add a custom amount to a quantitative datatype
     * @param ID the targeted uuid
     * @param dataType the Datatype
     * @param amount the desired amount to add
     */
    public static void add(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType, final int amount) {
        if (dataType.equals(QuantitativeData.TALENTS) || dataType.equals(QuantitativeData.ACHIEVEMENTS)) {
            Bukkit.getLogger().severe("CANNOT ADD TO NON INT COLUMNS");
        }
        set(ID, dataType, getInt(ID, dataType) + amount);
    }

    /**
     * Remove 1 from a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     */
    public static void remove(@Nonnull final OfflinePlayer p, @Nonnull final QuantitativeData dataType) {
        remove(p.getUniqueId(), dataType, 1);
    }

    /**
     * remove a custom amount from a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     * @param amount the desired amount to remove
     */
    public static void remove(@Nonnull final OfflinePlayer p, @Nonnull final QuantitativeData dataType, final int amount) {
        remove(p.getUniqueId(), dataType, amount);
    }

    /**
     * Remove 1 from a quantitative datatype
     * @param ID the targeted UUID
     * @param dataType the Datatype
     */
    public static void remove(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType) {
        remove(ID, dataType, 1);
    }

    /**
     * remove a custom amount from a quantitative datatype
     * @param ID the targeted uuid
     * @param dataType the Datatype
     * @param amount the desired amount to remove
     */
    public static void remove(@Nonnull final UUID ID, @Nonnull final QuantitativeData dataType, final int amount) {
        if (dataType.equals(QuantitativeData.TALENTS) || dataType.equals(QuantitativeData.ACHIEVEMENTS) || dataType.equals(QuantitativeData.PLAYTIMEUNIX)) {
            Bukkit.getLogger().severe("CANNOT MINUS TO NON INT COLUMNS");
        }
        set(ID, dataType, getInt(ID, dataType) - amount);
    }

    private static int levelFormula(final int level) {
        return (int) ((level * 5) / 3);
    }

    public static float kdrFormula(final int kills, final int deaths) {
        if (kills != 0 && deaths != 0)
            return kills/deaths;
        return 0F;
    }
}

