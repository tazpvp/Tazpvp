package net.tazpvp.tazpvp.utils.data;

import java.util.UUID;
import java.util.WeakHashMap;

public final class LooseData {
    private static final WeakHashMap<UUID, Integer> ks = new WeakHashMap<>();

    private static final WeakHashMap<UUID, Integer> chatCount = new WeakHashMap<>();

    public static int getKs(UUID uuid) {
        return ks.get(uuid);
    }

    public static void addKs(UUID uuid) {
        if (ks.containsKey(uuid)) {
            ks.put(uuid, getKs(uuid) + 1);
        } else {
            ks.put(uuid, 1);
        }
    }

    public static void setChatCount(UUID p, int val) {
        chatCount.put(p, val);
    }

    public static Integer getChatCount(UUID p) {
        return chatCount.get(p);
    }

    public static void resetKs(UUID uuid) {
        ks.put(uuid, 0);
    }

}
