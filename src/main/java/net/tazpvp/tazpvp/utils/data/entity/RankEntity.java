package net.tazpvp.tazpvp.utils.data.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "ranks")
public class RankEntity {
    @DatabaseField(id = true)
    private UUID uuid;
    @DatabaseField
    private boolean premium;
    @DatabaseField
    private String rank;
    @DatabaseField
    private String prefix;
    @DatabaseField(columnName = "death_particle")
    private String deathParticle;
    @DatabaseField(columnName = "arrow_particle")
    private String arrowParticle;
    @DatabaseField(columnName = "time_rank_updated")
    private long timeRankedUpdated;
}
