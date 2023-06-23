package net.tazpvp.tazpvp.utils.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
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

public final class PlayerRankData {
    private static final Table table;

    static {
        try {
            table = new Table(Tazpvp.getDatabase(), "ranks", Map.entry("id", DataTypes.UUID), new LinkedHashMap<>(
                    Map.of(
                            "premium", DataTypes.BOOLEAN, "rank", DataTypes.TEXT, "prefix", DataTypes.TEXT, "death_particle_material", DataTypes.TEXT, "arrow_particle_material", DataTypes.TEXT
                    )
            )
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Set rank of UUID, doesn't have to already be in table.
     * @param uuid UUID target
     * @param rank Vault Rank
     */
    public static void setRank(@Nonnull final UUID uuid, @Nonnull final Rank rank) {
        try {
            new Row(table, uuid.toString()).update(new Column(table, "rank"), rank.toString());
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
            new Row(table, uuid.toString(), false + "", Rank.DEFAULT.toString(), null, null, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set prefix of row, will do nothing if not already inside of table
     * @param uuid UUID target
     * @param prefix New Prefix
     */
    public static void setPrefix(@Nonnull final UUID uuid, @Nonnull final String prefix) {
        if (hasRank(uuid)) {
            try {
                new Row(table, uuid.toString()).update(new Column(table, "prefix"), prefix);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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

    /**
     * Get the rank of UUID - Will throw runtime exception if not in table
     * @param uuid UUID target
     * @return String of UUID's rank
     */
    public static Rank getRank(@Nonnull final UUID uuid) {
        try {
            return Rank.valueOf((String) new Row(table, uuid.toString()).fetch(new Column(table, "rank")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get prefix of UUID - Will throw runtime exception if not in table
     * @param uuid UUID target
     * @return String of UUID's prefix
     */
    @Nullable
    public static String getPrefix(@Nonnull final UUID uuid) {
        try {
            return (String) new Row(table, uuid.toString()).fetch(new Column(table, "prefix"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if a player has premium status
     * @param offlinePlayer The player to check
     * @return True if the player has premium, else false.
     */
    public static boolean isPremium(@Nonnull final OfflinePlayer offlinePlayer) {
        return isPremium(offlinePlayer.getUniqueId());
    }

    /**
     * Checks if a player has premium status
     * @param uuid The player to check
     * @return True if the player has premium, else false.
     */
    public static boolean isPremium(@Nonnull final UUID uuid) {
        try {
            return (boolean) new Row(table, uuid.toString()).fetch(new Column(table, "premium"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Checks if a player has premium status
     * @param uuid The player to set their premium status
     * @param premium The new value of their premium status
     */
    public static void setPremium(@Nonnull final UUID uuid, final boolean premium) {
        try {
            new Row(table, uuid.toString()).update(new Column(table, "premium"), premium + "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the material of the particles for a certain cosmetic affect
     * @param uuid UUID of player
     * @param particleMaterial PArticle type
     * @param material Material Type, can be null to toggle OFF
     */
    public static void setMaterial(@Nonnull final UUID uuid, @Nonnull final ParticleMaterial particleMaterial, @Nullable final Material material) {
        try {
            new Row(table, uuid.toString()).update(new Column(table, particleMaterial.getColumnName()), material != null ? material.toString() : null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the material used in a particle
     * @param uuid UUID of player
     * @param particleMaterial The material for the correct particle
     * @return The material corresponding to that particle type, can be NULL.
     */
    public static Material getMaterial(@Nonnull final UUID uuid, @Nonnull final ParticleMaterial particleMaterial) {
        try {
            return Material.valueOf((String) new Row(table, uuid.toString()).fetch(new Column(table, particleMaterial.columnName)));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @AllArgsConstructor
    @Getter
    public enum ParticleMaterial {
        ARROW("arrow_particle_material"), DEATH("death_particle_material");
        private final String columnName;
    }
}
