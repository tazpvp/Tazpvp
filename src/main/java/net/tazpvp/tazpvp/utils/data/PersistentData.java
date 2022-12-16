package net.tazpvp.tazpvp.utils.data;

import net.tazpvp.tazpvp.achievements.Achievements;
import net.tazpvp.tazpvp.talents.Talents;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import world.ntdi.nrcore.utils.sql.SQLHelper;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

public final class PersistentData {
    /**
     * Name of the stats table
     */
    private static final String NAME = "stats";
    /**
     * Name of the ID Column
     */
    private static final String ID_COLUMN = "ID";


    public static void topKs(UUID uuid) {
        if (getInt(uuid, DataTypes.TOPKILLSTREAK) < LooseData.getKs(uuid)) {
            set(uuid, DataTypes.TOPKILLSTREAK, LooseData.getKs(uuid));
        }
    }

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
            SQLHelper.initializeValues(NAME, "ID, COINS, XP, LEVEL, KILLS, DEATHS, TOP_KS, PRESTIGE, REBIRTH, DUEL_WINS, DIVISION, PLAYTIME, GUILD_ID, TALENTS, ACHIEVEMENTS", "'" + uuid + "'", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", "'a'", "'set'", "'set'");
            setTalents(uuid, new Talents());
            setAchievements(uuid, new Achievements());
        }
    }

    /**
     * Get quantitative data (ints) from the database
     * @param p The targeted player
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static int getInt(OfflinePlayer p, DataTypes dataType) {
        return getInt(p.getUniqueId(), dataType);
    }
    /**
     * Get quantitative data (ints) from the database
     * @param uuid The targeted UUID
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static int getInt(UUID uuid, DataTypes dataType) {
        return getInt(uuid, dataType.getColumnIndex());
    }

    /**
     * Get quantitative data (String) from the database
     * @param p The targeted player
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static String getString(OfflinePlayer p, DataTypes dataType) {
        return getString(p.getUniqueId(), dataType);
    }
    /**
     * Get quantitative data (String) from the database
     * @param uuid The targeted UUID
     * @param dataType The type of data you want to access
     * @return The value of the requested data
     */
    public static String getString(UUID uuid, DataTypes dataType) {
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
        return (Talents) getSerializeObject(uuid, DataTypes.TALENTS);
    }

    public static Achievements getAchievements(@Nonnull final UUID uuid) {
        return (Achievements) getSerializeObject(uuid, DataTypes.ACHIEVEMENTS);
    }

    public static Object getSerializeObject(@Nonnull final UUID uuid, @Nonnull final DataTypes dataTypes) {
        if (getString(uuid, dataTypes.getColumnIndex()).equals("set")) {
            if (dataTypes.equals(DataTypes.TALENTS)) {
                return new Talents();
            } else {
                return new Achievements();
            }
        }
        ByteArrayInputStream stream = new ByteArrayInputStream(Base64.getDecoder().decode(getString(uuid, dataTypes.getColumnIndex())));
        BukkitObjectInputStream data = null;
        try {
            data = new BukkitObjectInputStream(stream);
            Object obj = data.readObject();
            data.close();
            return obj;
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
    public static String getString(@Nonnull final UUID ID, final int columnIndex) {
        return SQLHelper.getString(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnIndex);
    }

    /**
     * Get an Object value of a sql column
     * @param ID the targeted UUID
     * @param columnIndex the index of the column
     * @return the Object value of the requested column
     */
    public static int getInt(@Nonnull final UUID ID, final int columnIndex) {
        return SQLHelper.getInt(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnIndex);
    }

    /**
     * Get an Object value of a sql column
     * @param ID the targeted UUID
     * @param dataTypes the index of the column
     * @return the Object value of the requested column
     */
    public static float getFloat(@Nonnull final UUID ID, final DataTypes dataTypes) {
        return SQLHelper.getFloat(NAME, ID_COLUMN, "'" + ID.toString() + "'", dataTypes.getColumnIndex());
    }

    /**
     * Get an Object value of a sql column
     * @param p the targeted Player
     * @param dataTypes the index of the column
     * @return the Object value of the requested column
     */
    public static float getFloat(@Nonnull final OfflinePlayer p, final DataTypes dataTypes) {
        return getFloat(p.getUniqueId(), dataTypes);
    }
    /*
        ====================== End GET ====================== Start SET ======================
     */

    /**
     * Set a quantitative datatype to a new value
     * @param p The targeted Player
     * @param dataType the datatype
     * @param value the new value for the datatype
     */
    public static void set(@Nonnull final OfflinePlayer p, @Nonnull final DataTypes dataType, final float value) {
        set(p.getUniqueId(), dataType, value);
    }

    /**
     * Set a quantitative datatype to a new value
     * @param ID the targeted UUID
     * @param dataType the datatype
     * @param value the new value for the datatype
     */
    public static void set(@Nonnull final UUID ID, @Nonnull final DataTypes dataType, final float value) {
        if (!dataType.isQuantitative()) {
            Bukkit.getLogger().severe("Data not quantitative in integer form");
        } else if (dataType.equals(DataTypes.PLAYTIMEUNIX)) {
            setValueF(ID, dataType.getColumnName(), value);
        } else {
            setValue(ID, dataType.getColumnName(), (int) value);
            if (dataType.equals(DataTypes.XP)) {
                if (getInt(ID, DataTypes.XP) >= levelFormula((int) getInt(ID, DataTypes.LEVEL))) {
                    add(ID, DataTypes.LEVEL);
                    set(ID, DataTypes.XP, 0);
                    // TODO: Add level up messages/functions
                }
            }
            Player p = Bukkit.getPlayer(ID);
            if (p != null) {
                p.getScoreboard().getTeam(dataType.getColumnName()).setSuffix((int) value + "");
                if (dataType.equals(DataTypes.KILLS) || dataType.equals(DataTypes.DEATHS)) {
                    p.getScoreboard().getTeam("kdr").setSuffix(kdrFormula(getFloat(p, DataTypes.KILLS), getFloat(p, DataTypes.DEATHS)) + "");
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
        setSerializedObject(uuid, talents, DataTypes.TALENTS);
    }

    /**
     * Set the talents object to the column
     * @param p the targeted player
     * @param achievements the talents object
     */
    public static void setAchievements(@Nonnull final OfflinePlayer p, @Nonnull final Achievements achievements) {
        setAchievements(p.getUniqueId(), achievements);
    }

    /**
     * Set the talents object to the column
     * @param uuid the targeted uuid
     * @param achievements the talents object
     */
    public static void setAchievements(@Nonnull final UUID uuid, @Nonnull final Achievements achievements) {
        setSerializedObject(uuid, achievements, DataTypes.ACHIEVEMENTS);
    }

    private static void setSerializedObject(@Nonnull final UUID uuid, @Nonnull final Object object, @Nonnull final DataTypes dataTypes) {
        ByteArrayOutputStream str = new ByteArrayOutputStream();
        BukkitObjectOutputStream data = null;
        try {
            data = new BukkitObjectOutputStream(str);
            data.writeObject(object);
            data.close();
            setValueS(uuid, dataTypes.getColumnName(), Base64.getEncoder().encodeToString(str.toByteArray()));
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
    public static void setValueS(@Nonnull final UUID ID, final String columnName, final String value) {
        SQLHelper.updateValue(NAME, ID_COLUMN, "'" + ID.toString() + "'", columnName, "'" + value + "'");
    }

    /*
        ====================== End SET ====================== Start ADD/REMOVE ======================
     */

    /**
     * Add 1 to a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     */
    public static void add(@Nonnull final OfflinePlayer p, @Nonnull final DataTypes dataType) {
        add(p.getUniqueId(), dataType, 1);
    }

    /**
     * Add a custom amount to a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     * @param amount the desired amount to add
     */
    public static void add(@Nonnull final OfflinePlayer p, @Nonnull final DataTypes dataType, final int amount) {
        add(p.getUniqueId(), dataType, amount);
    }

    /**
     * Add 1 to a quantitative datatype
     * @param ID the targeted UUID
     * @param dataType the Datatype
     */
    public static void add(@Nonnull final UUID ID, @Nonnull final DataTypes dataType) {
        add(ID, dataType, 1);
    }

    /**
     * Add a custom amount to a quantitative datatype
     * @param ID the targeted uuid
     * @param dataType the Datatype
     * @param amount the desired amount to add
     */
    public static void add(@Nonnull final UUID ID, @Nonnull final DataTypes dataType, final int amount) {
        if (dataType.equals(DataTypes.TALENTS) || dataType.equals(DataTypes.ACHIEVEMENTS)) {
            Bukkit.getLogger().severe("CANNOT ADD TO NON INT COLUMNS");
        }
        set(ID, dataType, getInt(ID, dataType) + amount);
    }

    /**
     * Remove 1 from a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     */
    public static void remove(@Nonnull final OfflinePlayer p, @Nonnull final DataTypes dataType) {
        remove(p.getUniqueId(), dataType, 1);
    }

    /**
     * remove a custom amount from a quantitative datatype
     * @param p the targeted player
     * @param dataType the Datatype
     * @param amount the desired amount to remove
     */
    public static void remove(@Nonnull final OfflinePlayer p, @Nonnull final DataTypes dataType, final int amount) {
        remove(p.getUniqueId(), dataType, amount);
    }

    /**
     * Remove 1 from a quantitative datatype
     * @param ID the targeted UUID
     * @param dataType the Datatype
     */
    public static void remove(@Nonnull final UUID ID, @Nonnull final DataTypes dataType) {
        remove(ID, dataType, 1);
    }

    /**
     * remove a custom amount from a quantitative datatype
     * @param ID the targeted uuid
     * @param dataType the Datatype
     * @param amount the desired amount to remove
     */
    public static void remove(@Nonnull final UUID ID, @Nonnull final DataTypes dataType, final int amount) {
        if (dataType.equals(DataTypes.TALENTS) || dataType.equals(DataTypes.ACHIEVEMENTS) || dataType.equals(DataTypes.PLAYTIMEUNIX)) {
            Bukkit.getLogger().severe("CANNOT MINUS TO NON INT COLUMNS");
        }
        set(ID, dataType, getInt(ID, dataType) - amount);
    }

    /*
        ====================== End ADD/REMOVE ====================== Start Formula ======================
     */

    private static int levelFormula(final int level) {
        return (int) ((level * 5) / 3);
    }

    public static float kdrFormula(final float kills, final float deaths) {
        if (kills != 0 && deaths != 0)
            return kills /deaths;
        return 0F;
    }
}

