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
    private AchievementEntity adeptAchievementEntity;

    @DatabaseField(foreign = true, columnName = "agile")
    private AchievementEntity agileAchievementEntity;

    @DatabaseField(foreign = true, columnName = "merchant")
    private AchievementEntity merchantAchievementEntity;

    @DatabaseField(foreign = true, columnName = "bowling")
    private AchievementEntity bowlingAchievementEntity;

    @DatabaseField(foreign = true, columnName = "legend")
    private AchievementEntity legendAchievementEntity;

    @DatabaseField(foreign = true, columnName = "gamble")
    private AchievementEntity gambleAchievementEntity;

    @DatabaseField(foreign = true, columnName = "superior")
    private AchievementEntity superiorAchievementEntity;

    @DatabaseField(foreign = true, columnName = "craftsman")
    private AchievementEntity craftsmanAchievementEntity;

    @DatabaseField(foreign = true, columnName = "charm")
    private AchievementEntity charmAchievementEntity;

    @DatabaseField(foreign = true, columnName = "rehab")
    private AchievementEntity rehabAchievementEntity;

    @DatabaseField(foreign = true, columnName = "zorgin")
    private AchievementEntity zorginAchievementEntity;

    @DatabaseField(foreign = true, columnName = "grinder")
    private AchievementEntity grinderAchievementEntity;

    @DatabaseField(foreign = true, columnName = "gladiator")
    private AchievementEntity gladiatorAchievementEntity;

    @DatabaseField(foreign = true, columnName = "harvester")
    private AchievementEntity harvesterAchievementEntity;

    @DatabaseField(foreign = true, columnName = "speedrunner")
    private AchievementEntity speedrunnerAchievementEntity;

    @DatabaseField(foreign = true, columnName = "artisan")
    private AchievementEntity artisanAchievementEntity;

    @DatabaseField(foreign = true, columnName = "error")
    private AchievementEntity errorAchievementEntity;

    @DatabaseField(foreign = true, columnName = "skilled")
    private AchievementEntity skilledAchievementEntity;


}
