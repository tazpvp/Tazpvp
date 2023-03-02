package net.tazpvp.tazpvp.utils.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.boss.BossBar;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class CombatTag {

    @Getter
    private Queue<UUID> attackers;
    @Getter
    @Setter
    private int countdown;
    @Getter
    @Setter
    private BossBar bar;

    public CombatTag() {
        this.attackers = new LinkedList<>();
    }


}
