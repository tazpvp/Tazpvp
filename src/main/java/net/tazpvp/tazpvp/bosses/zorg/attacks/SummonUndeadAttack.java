package net.tazpvp.tazpvp.bosses.zorg.attacks;

import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.bosses.CustomBoss;
import net.tazpvp.tazpvp.bosses.attacks.Attack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.entity.ZombieVillager;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SummonUndeadAttack implements Attack {
    private final Random random = new Random();
    public static List<Zombie> undeadList = new ArrayList<>();
    private final int MAX_DISTANCE = 4;
    @Override
    public void attack(final CustomBoss boss) {
        for (int i = 0; i < 2; i++) {
            final Location spawnLocWithinRadius = randomLocationWithinRadius(boss.getBoss().getLocation());

            spawnUndead(spawnLocWithinRadius);
        }
    }

    private void spawnUndead(final Location spawnLocation) {
        if (undeadList.size() < 5) {
            final Zombie zombie = (Zombie) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
            undeadList.add(zombie);
            zombie.setCustomName(CC.AQUA + "" + CC.BOLD + "Undead Slave");
            zombie.setCustomNameVisible(true);
            applyRandomItem(zombie.getEquipment());
        }
    }

    private void applyRandomItem(final EntityEquipment equipment) {
        final List<Material> armors = List.of(Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS);

        final Material randomMaterial = armors.get(random.nextInt(armors.size() - 1));
        final ItemStack item = ItemBuilder.of(randomMaterial).enchantment(Enchantment.VANISHING_CURSE, 1).build();

        if (randomMaterial.name().endsWith("CHESTPLATE")) {
            equipment.setChestplate(item);
        } else if (randomMaterial.name().endsWith("HELMET")) {
            equipment.setHelmet(item);
        } else if (randomMaterial.name().endsWith("LEGGINGS")) {
            equipment.setLeggings(item);
        } else if (randomMaterial.name().endsWith("BOOTS")) {
            equipment.setBoots(item);
        }
    }

    private Location randomLocationWithinRadius(final Location targetLocation) {
        final int radius = random.nextInt(MAX_DISTANCE) + 1;
        double angle = random.nextDouble() * 2 * Math.PI;

        int x = (int) (Math.cos(angle) * radius);
        int z = (int) (Math.sin(angle) * radius);

        final int newX = targetLocation.getBlockX() + x;
        final int newZ = targetLocation.getBlockZ() + z;
        final Location spawnLocation = new Location(targetLocation.getWorld(), newX, targetLocation.getY(), newZ);

        if (spawnLocation.getBlock().getType().isSolid()) {
            return spawnLocation;
        }

        return randomLocationWithinRadius(targetLocation);
    }
}
