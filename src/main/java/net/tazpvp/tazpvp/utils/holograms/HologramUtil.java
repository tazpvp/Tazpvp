package net.tazpvp.tazpvp.utils.holograms;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import net.tazpvp.tazpvp.utils.StringArrayDataType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HologramUtil {
    @Getter
    private static final Map<String, Location> holograms = new HashMap<>();
    @Getter
    private int taskId;

    public HologramUtil() {
        scheduler();
        loadAllHolograms();
    }

    private void scheduler() {
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Tazpvp.getInstance(), () -> {
            if (Bukkit.getOnlinePlayers().isEmpty()) return;

            Bukkit.getWorld("arena").getEntities().forEach(entity -> {
                if (entity instanceof TextDisplay display) {
                    PersistentDataContainer pdc = display.getPersistentDataContainer();
                    if (Boolean.TRUE.equals(pdc.get(Hologram.shouldCycleKey, PersistentDataType.BOOLEAN))) {
                        String[] texts = pdc.get(Hologram.textKey, new StringArrayDataType());
                        if (texts != null) {
                            int index = pdc.getOrDefault(Hologram.indexKey, PersistentDataType.INTEGER, 0);
                            display.setText(texts[index]);
                            index = (index + 1) % texts.length;
                            pdc.set(Hologram.indexKey, PersistentDataType.INTEGER, index);
                        }
                    }
                }
            });
        }, 0, 20 * 30);
    }

    public static void loadAllHolograms() {
        Bukkit.getWorld("arena").getEntities().forEach(entity -> {
            if (entity instanceof TextDisplay display) {
                PersistentDataContainer pdc = display.getPersistentDataContainer();
                if (pdc.has(Hologram.nameKey, PersistentDataType.STRING)) {
                    String name = pdc.get(Hologram.nameKey, PersistentDataType.STRING);
                    if (name != null) {
                        holograms.put(name, display.getLocation());
                    }
                }
            }
        });
    }

    public static void addHologram(Hologram hologram) {
        holograms.put(hologram.getName(), hologram.getDisplay().getLocation());
    }

    public static List<String> getAllNames() {
        return new ArrayList<>(holograms.keySet());
    }

    public static TextDisplay findHologramById(String id) {
        for (Entity e : Bukkit.getWorld("arena").getEntities()) {
            if (e instanceof TextDisplay display) {
                String name = display.getPersistentDataContainer().get(Hologram.nameKey, PersistentDataType.STRING);
                if (name != null && name.equalsIgnoreCase(id)) {
                    return display;
                }
            }
        }
        return null;
    }
}
