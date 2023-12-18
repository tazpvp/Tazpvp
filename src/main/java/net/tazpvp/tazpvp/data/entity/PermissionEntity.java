package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.field.DatabaseField;
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
@DatabaseTable(tableName = "permissions")
public class PermissionEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = "game_rank", foreignAutoRefresh = true)
    private GameRankEntity gameRankEntity;

    @DatabaseField
    private String permission;
}
