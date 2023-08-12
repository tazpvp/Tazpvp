package net.tazpvp.tazpvp.utils.objects;

import lombok.Getter;
import net.tazpvp.tazpvp.utils.functions.ChatFunctions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import world.ntdi.nrcore.utils.ChatUtils;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

public class Zorg {
    @Getter
    private final WitherSkeleton zorg;


    public Zorg(final Location location) {
        this.zorg = (WitherSkeleton) location.getWorld().spawnEntity(location, EntityType.WITHER_SKELETON);
        setup();
    }

    private void setup() {
        final EntityEquipment equipment = this.zorg.getEquipment();
        equipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        equipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        equipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        equipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));
        equipment.setItemInMainHand(new ItemBuilder().item(new ItemStack(Material.DIAMOND_SWORD)).enchantment(Enchantment.KNOCKBACK, 2).build());
        this.zorg.setCustomName(ChatFunctions.gradient("#FFADED", "Zorg", true));
    }
    public void shoot() {
        final RayTraceResult result = this.zorg.getWorld().rayTraceEntities(
                this.zorg.getEyeLocation().add(this.zorg.getLocation().getDirection()),
                this.zorg.getEyeLocation().getDirection(),
                10,
                entity -> {
                    if (entity.getUniqueId().equals(this.zorg.getUniqueId())) {
                        return false;
                    } else if (entity.getType() == EntityType.PLAYER) {
                        return true;
                    }
                    return false;
                }
        );

        if (result == null || result.getHitEntity() == null) {
            return;
        }

        Player target = (Player) result.getHitEntity();

        this.zorg.getWorld().spawnParticle(Particle.SONIC_BOOM, zorg.getLocation(), 1);

    };

    public void despawn() {
        this.zorg.remove();
    }

}
