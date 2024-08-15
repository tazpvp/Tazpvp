package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.beans.Transient;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DatabaseTable(tableName = "guild")
public class GuildEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false, unique = true)
    private UUID owner;

    @ForeignCollectionField
    private ForeignCollection<GuildMemberEntity> members;

    @DatabaseField(defaultValue = "None.")
    private String description;

    @DatabaseField()
    private String tag;

    @DatabaseField(defaultValue = "OAK_SIGN")
    private String icon;

    @DatabaseField(defaultValue = "false")
    private boolean isPrivate;

    @DatabaseField(defaultValue = "0", canBeNull = false)
    private int kills;

    @DatabaseField(defaultValue = "0", canBeNull = false)
    private int deaths;

    @Override
    @Transient
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof GuildEntity guildEntity) {
            return guildEntity.getId() == id;
        }
        return false;
    }
}
