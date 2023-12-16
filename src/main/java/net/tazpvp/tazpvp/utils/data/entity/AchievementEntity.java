package net.tazpvp.tazpvp.utils.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@DatabaseTable(tableName = "achievements")
public class AchievementEntity {
    @DatabaseField(id = true)
    private UUID uuid;

    @DatabaseField(defaultValue = "false")
    private boolean adept;
    @DatabaseField(defaultValue = "false")
    private boolean merchant;
    @DatabaseField(defaultValue = "false")
    private boolean bowling;
    @DatabaseField(defaultValue = "false")
    private boolean legend;
    @DatabaseField(defaultValue = "false")
    private boolean gamble;
    @DatabaseField(defaultValue = "false")
    private boolean superior;
    @DatabaseField(defaultValue = "false")
    private boolean craftsman;
    @DatabaseField(defaultValue = "false")
    private boolean charm;
    @DatabaseField(defaultValue = "false")
    private boolean rehab;
    @DatabaseField(defaultValue = "false")
    private boolean zorgin;
    @DatabaseField(defaultValue = "false")
    private boolean grinder;
    @DatabaseField(defaultValue = "false")
    private boolean gladiator;
    @DatabaseField(defaultValue = "false")
    private boolean harvester;
    @DatabaseField(defaultValue = "false")
    private boolean speedrunner;
    @DatabaseField(defaultValue = "false")
    private boolean artisan;
    @DatabaseField(defaultValue = "false")
    private boolean error;
    @DatabaseField(defaultValue = "false")
    private boolean skilled;

}
