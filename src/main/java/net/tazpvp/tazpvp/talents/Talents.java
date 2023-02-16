package net.tazpvp.tazpvp.talents;

import lombok.Getter;
import lombok.Setter;
import net.tazpvp.tazpvp.events.DataHolder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Talents extends DataHolder implements Serializable {

    public Talents() {
        super(new HashMap<>(Map.ofEntries(
                    Map.entry("Revenge", false),
                    Map.entry("Moist", false),
                    Map.entry("Resilient", false),
                    Map.entry("Excavator", false),
                    Map.entry("Architect", false),
                    Map.entry("Hunter", false),
                    Map.entry("Cannibal", false),
                    Map.entry("Agile", false),
                    Map.entry("Harvester", false),
                    Map.entry("Necromancer", false),
                    Map.entry("Blessed", false),
                    Map.entry("Glider", false),
                    Map.entry("Proficient", false),
                    Map.entry("Medic", false)
            ))
        );
    }
}
