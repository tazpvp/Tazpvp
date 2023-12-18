package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@DatabaseTable(tableName = "game_ranks")
public class GameRankEntity {
    @DatabaseField(id = true, generatedId = true)
    private int id;

    @DatabaseField(unique = true)
    private String name;

    @DatabaseField(width = 1024)
    private String prefix;

    @DatabaseField
    private int hierarchy;

    @ForeignCollectionField
    private ForeignCollection<PermissionEntity> permissions;
}
