package net.tazpvp.tazpvp.utils;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import world.ntdi.postglam.data.Tuple;

public class PDCUtil {
    @Getter
    private final static NamespacedKey npcKey = new NamespacedKey(Tazpvp.getInstance(), "npc-key");

    public static void setPDC(Entity e, NamespacedKey key, String value) {
        e.getPersistentDataContainer().set(key, PersistentDataType.STRING, value);
    }

    public static Tuple<Boolean, String> hasPDC(Entity e, NamespacedKey key) {
        PersistentDataContainer container  = e.getPersistentDataContainer();

        if (container.has(key, PersistentDataType.STRING)) {
            return new Tuple<>(true, (container.get(key, PersistentDataType.STRING)));
        }
        return new Tuple<>(false, "");
    }

}
