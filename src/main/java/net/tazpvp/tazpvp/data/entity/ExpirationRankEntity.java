package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DatabaseTable(tableName = "expiring_rank")
public class ExpirationRankEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = "user_rank", foreignAutoRefresh = true)
    private UserRankEntity userRankEntity;

    @DatabaseField(foreign = true, columnName = "game_rank")
    private GameRankEntity gameRankEntity;

    @DatabaseField(canBeNull = false)
    private long expirationTimestamp;
}
