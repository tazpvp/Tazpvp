package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "player_stats")
public class PlayerStatEntity {
    @DatabaseField(id = true)
    private UUID uuid;

    @DatabaseField(columnName = "coins", canBeNull = false)
    private int coins;
    @DatabaseField(columnName = "xp", canBeNull = false)
    private int xp;
    @DatabaseField(columnName = "level", canBeNull = false)
    private int level;
    @DatabaseField(columnName = "mmr", canBeNull = false)
    private int MMR;
    @DatabaseField(columnName = "duelmmr", canBeNull = false)
    private int duelMMR;
    @DatabaseField(columnName = "kills", canBeNull = false)
    private int kills;
    @DatabaseField(columnName = "deaths", canBeNull = false)
    private int deaths;
    @DatabaseField(columnName = "prestige", canBeNull = false)
    private int prestige;
    @DatabaseField(columnName = "playtime", canBeNull = false, defaultValue = "0")
    private long playtime;
}
