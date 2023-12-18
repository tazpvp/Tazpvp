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
@DatabaseTable(tableName = "talents")
public class TalentEntity {
    @DatabaseField(id = true)
    private UUID uuid;

    @DatabaseField(defaultValue = "false")
    private boolean agile;
    @DatabaseField(defaultValue = "false")
    private boolean architect;
    @DatabaseField(defaultValue = "false")
    private boolean blessed;
    @DatabaseField(defaultValue = "false")
    private boolean cannibal;
    @DatabaseField(defaultValue = "false")
    private boolean glide;
    @DatabaseField(defaultValue = "false")
    private boolean medic;
    @DatabaseField(defaultValue = "false")
    private boolean excavator;
    @DatabaseField(defaultValue = "false")
    private boolean hunter;
    @DatabaseField(defaultValue = "false")
    private boolean moist;
    @DatabaseField(defaultValue = "false")
    private boolean proficient;
    @DatabaseField(defaultValue = "false")
    private boolean resilient;
    @DatabaseField(defaultValue = "false")
    private boolean revenge;
    @DatabaseField(defaultValue = "false")
    private boolean necromancer;
    @DatabaseField(defaultValue = "false")
    private boolean harvester;
}
