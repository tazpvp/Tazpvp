package net.tazpvp.tazpvp.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;
import net.tazpvp.tazpvp.data.entity.GuildEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DatabaseTable(tableName = "guild_member")
public class GuildMemberEntity {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true, columnName = "guild_entity", foreignAutoRefresh = true)
    private GuildEntity guildEntity;

    @DatabaseField(defaultValue = "false")
    private boolean general;
}
