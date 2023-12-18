package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "achievements")
public class AchievementEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user_achievements")
    private UserAchievementEntity userAchievementEntity;

    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean completed;
    @DatabaseField(defaultValue = "false", canBeNull = false)
    private boolean collected;


}
