package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "user_achievements")
public class UserAchievementEntity {
    @DatabaseField(id = true)
    private UUID uuid;

    @DatabaseField(foreign = true, columnName = "adept")
    private AchievementEntity adept;

    @DatabaseField(foreign = true, columnName = "agile")
    private AchievementEntity agile;

    @DatabaseField(foreign = true, columnName = "merchant")
    private AchievementEntity merchant;

    @DatabaseField(foreign = true, columnName = "bowling")
    private AchievementEntity bowling;

    @DatabaseField(foreign = true, columnName = "legend")
    private AchievementEntity legend;

    @DatabaseField(foreign = true, columnName = "gamble")
    private AchievementEntity gamble;

    @DatabaseField(foreign = true, columnName = "superior")
    private AchievementEntity superior;

    @DatabaseField(foreign = true, columnName = "craftsman")
    private AchievementEntity craftsman;

    @DatabaseField(foreign = true, columnName = "charm")
    private AchievementEntity charm;

    @DatabaseField(foreign = true, columnName = "rehab")
    private AchievementEntity rehab;

    @DatabaseField(foreign = true, columnName = "zorgin")
    private AchievementEntity zorgin;

    @DatabaseField(foreign = true, columnName = "grinder")
    private AchievementEntity grinder;

    @DatabaseField(foreign = true, columnName = "gladiator")
    private AchievementEntity gladiator;

    @DatabaseField(foreign = true, columnName = "harvester")
    private AchievementEntity harvester;

    @DatabaseField(foreign = true, columnName = "speedrunner")
    private AchievementEntity speedrunner;

    @DatabaseField(foreign = true, columnName = "artisan")
    private AchievementEntity artisan;

    @DatabaseField(foreign = true, columnName = "error")
    private AchievementEntity error;

    @DatabaseField(foreign = true, columnName = "skilled")
    private AchievementEntity skilled;


}
