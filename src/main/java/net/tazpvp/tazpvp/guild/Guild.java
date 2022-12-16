package net.tazpvp.tazpvp.guild;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.data.DataTypes;
import net.tazpvp.tazpvp.utils.data.PersistentData;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guild implements Serializable {
    @Getter
    private final UUID ID;
    @Getter
    private UUID guild_leader;
    @Getter
    private final List<UUID> guild_generals;
    @Getter
    private final List<UUID> guild_members;
    @Getter
    private final String name;
    @Getter
    private String description;
    @Getter
    private String tag;
    @Getter
    private Material icon;
    @Getter
    private boolean show_in_browser;
    @Getter
    private int kills, deaths;

    public Guild(String name, UUID guild_leader) {
        this.ID = UUID.randomUUID();
        this.name = name;
        this.guild_leader = guild_leader;
        this.guild_generals = new ArrayList<>();
        this.guild_members = new ArrayList<>();
        this.kills = 0;
        this.deaths = 0;

        setPlayersGuild(getGuild_leader());
    }

    public void setPlayersGuild(OfflinePlayer p) {
        setPlayersGuild(p.getUniqueId());
    }
    public void setPlayersGuild(UUID p) {
        PersistentData.setValueS(p, DataTypes.GUILD_ID.getColumnName(), getID().toString());
    }
}
