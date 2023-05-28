package net.tazpvp.tazpvp.utils.data;

import java.util.UUID;
import java.util.WeakHashMap;

public final class LooseData {
    private static final WeakHashMap<UUID, Integer> ks = new WeakHashMap<>();

    private static final WeakHashMap<UUID, Integer> chatCount = new WeakHashMap<>();
    private static final WeakHashMap<UUID, Integer> mineCount = new WeakHashMap<>();

    public static int getExpLeft(UUID uuid) {
        float level = PersistentData.getInt(uuid, DataTypes.LEVEL);
        return Math.round((float) (level * 1.94) + 40);
    }

    public static int getKs(UUID uuid) {
        if (ks.containsKey(uuid)) return ks.get(uuid);
        return 0;
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
        if (chatCount.containsKey(p)) {
            return chatCount.get(p);
        }
        return 0;
    }

    public static void setMineCount(UUID p, int val) {
        mineCount.put(p, val);
    }

    public static Integer getMineCount(UUID p) {
        if (mineCount.containsKey(p)) {
            return mineCount.get(p);
        }
        return 0;
    }

    public static void resetKs(UUID uuid) {
        PersistentData.topKs(uuid);
        ks.put(uuid, 0);
    }

}

