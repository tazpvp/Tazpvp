package net.tazpvp.tazpvp.item;

import lombok.Getter;
import net.tazpvp.tazpvp.Tazpvp;
import org.bukkit.NamespacedKey;

public final class ItemIndex {
    @Getter
    private static NamespacedKey key = new NamespacedKey(Tazpvp.getInstance(), "customItemID");
}
