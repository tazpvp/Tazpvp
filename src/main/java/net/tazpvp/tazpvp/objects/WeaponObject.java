package net.tazpvp.tazpvp.objects;

import org.bukkit.Material;

public abstract class WeaponObject {
    private final Material material;

    public WeaponObject(Material material) {
        this.material = material;
    }

    abstract void critical();

}
