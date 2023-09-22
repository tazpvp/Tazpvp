package net.tazpvp.tazpvp.utils.objects.bosses.zorg.attacks;

import net.tazpvp.tazpvp.utils.enums.CC;
import net.tazpvp.tazpvp.utils.objects.bosses.CustomBoss;
import net.tazpvp.tazpvp.utils.objects.bosses.attacks.Attack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;
import java.util.Random;

public class SummonUndeadAttack implements Attack {
    private final Random random = new Random();
    @Override
    public void attack(final CustomBoss boss) {
        for (int i = 0; i < 2; i++) {
            final Location spawnLocWithinRadius = randomLocationWithinRadius(boss.getBoss().getLocation(), 4);

            spawnUndead(spawnLocWithinRadius);
        }
    }

    private void spawnUndead(final Location spawnLocation) {
        final Zombie zombie = (Zombie) spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.ZOMBIE);
        zombie.setCustomName(CC.AQUA + "" + CC.BOLD + "Undead Slave");
        zombie.setCustomNameVisible(true);
        applyRandomItem(zombie.getEquipment());
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

    private Location randomLocationWithinRadius(final Location targetLocation, final int maxDistance) {
        final int radius = random.nextInt(1, maxDistance);
        int x = random.nextInt(radius);
        int z = (int) Math.sqrt(Math.pow(radius, 2) - Math.pow(x, 2));

        if (random.nextBoolean()) {
            x *= -1;
        }
        if (random.nextBoolean()) {
            z *= -1;
        }

        final int newX = targetLocation.getBlockX() + x;
        final int newZ = targetLocation.getBlockZ() + z;
        final Location spawnLocation = new Location(targetLocation.getWorld(), newX, targetLocation.getY(), newZ);

        if (!spawnLocation.getBlock().getType().isAir()) {
            return randomLocationWithinRadius(targetLocation, 4);
        }

        return spawnLocation;
    }
}
