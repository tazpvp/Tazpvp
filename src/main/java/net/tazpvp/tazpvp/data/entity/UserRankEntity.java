package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DatabaseTable(tableName = "user_ranks")
public class UserRankEntity {
    @DatabaseField(id = true)
    private UUID uuid;

    @ForeignCollectionField
    private ForeignCollection<ExpirationRankEntity> ranks;


    @DatabaseField(columnName = "death_particle")
    private String deathParticle;
    @DatabaseField(columnName = "arrow_particle")
    private String arrowParticle;
}
