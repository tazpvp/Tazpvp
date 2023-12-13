package net.tazpvp.tazpvp.utils.data.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@DatabaseTable(tableName = "kit")
public class KitEntity {
    @DatabaseField(id = true)
    private UUID uuid;

    @DatabaseField(width = 10_000)
    private String serial;
}
