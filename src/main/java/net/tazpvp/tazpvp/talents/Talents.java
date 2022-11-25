package net.tazpvp.tazpvp.talents;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Talents implements Serializable {
    @Getter
    @Setter
    private boolean Revenge, Moist, Resilient, Excavator, Architect, Hunter, Cannibal = false;
    @Getter
    @Setter
    private boolean Agile, Harvester, Necromancer, Blessed, Glider, Proficient, Medic = false;
}
