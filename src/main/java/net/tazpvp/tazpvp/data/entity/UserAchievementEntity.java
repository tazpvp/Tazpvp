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

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "adept")
    private AchievementEntity adept;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "agile")
    private AchievementEntity agile;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "merchant")
    private AchievementEntity merchant;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "bowling")
    private AchievementEntity bowling;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "legend")
    private AchievementEntity legend;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "gamble")
    private AchievementEntity gamble;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "superior")
    private AchievementEntity superior;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "craftsman")
    private AchievementEntity craftsman;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "charm")
    private AchievementEntity charm;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "rehab")
    private AchievementEntity rehab;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "zorgin")
    private AchievementEntity zorgin;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "grinder")
    private AchievementEntity grinder;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "gladiator")
    private AchievementEntity gladiator;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "harvester")
    private AchievementEntity harvester;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "speedrunner")
    private AchievementEntity speedrunner;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "artisan")
    private AchievementEntity artisan;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "error")
    private AchievementEntity error;

    @DatabaseField(foreign = true, foreignAutoCreate = true, columnName = "skilled")
    private AchievementEntity skilled;


}
