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
@DatabaseTable(tableName = "punishments")
public class PunishmentEntity {
    @DatabaseField(id = true)
    private UUID uuid;

    @DatabaseField
    private long timestamp;

    @DatabaseField(columnName = "punishment_type")
    private String punishmentType;

    @DatabaseField
    private String reason;
}
