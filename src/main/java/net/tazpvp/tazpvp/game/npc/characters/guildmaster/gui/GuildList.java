package net.tazpvp.tazpvp.game.npc.characters.guildmaster.gui;

import net.tazpvp.tazpvp.data.entity.GuildEntity;
import net.tazpvp.tazpvp.data.services.GuildService;
import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.ntdi.nrcore.utils.gui.Button;
import world.ntdi.nrcore.utils.gui.GUI;
import world.ntdi.nrcore.utils.item.builders.ItemBuilder;

import java.util.List;

public class GuildList extends GUI {
    private final List<GuildEntity> guilds;
    private int currentPage;
    private static final int ROWS = 5;
    private static final int GUILDS_PER_PAGE = ROWS * 7;

    public GuildList(Player player, GuildService guildService) {
        super("Guild List", ROWS + 1);
        this.guilds = guildService.getAllGuildsSorted();
        this.currentPage = 0;
        updateGUI(player);
        open(player);
    }

    private void updateGUI(Player player) {
        clear();
        fillBorder();
        addGuildItems();
        addNavigationButtons(player);
        update();
    }

    private void fillBorder() {
        Button borderItem = Button.createBasic(ItemBuilder.of(Material.BLACK_STAINED_GLASS_PANE).build());

        for (int i = 0; i < 9; i++) {
            addButton(borderItem, i);
            addButton(borderItem, (ROWS + 1) * 9 - 1 - i);
        }
        for (int i = 1; i < ROWS; i++) {
            addButton(borderItem, i * 9);
            addButton(borderItem, i * 9 + 8);
        }
    }

    private void addGuildItems() {
        int startIndex = currentPage * GUILDS_PER_PAGE;
        int endIndex = Math.min(startIndex + GUILDS_PER_PAGE, guilds.size());

        for (int i = startIndex; i < endIndex; i++) {
            GuildEntity guild = guilds.get(i);
            ItemStack guildItem = createGuildItem(guild);
            int slot = (i - startIndex) % GUILDS_PER_PAGE + 10;
            if (slot % 9 == 8) slot += 2;
            addButton(Button.createBasic(guildItem), slot);
        }
    }

    private ItemStack createGuildItem(GuildEntity guild) {
        return ItemBuilder.of(Material.valueOf(guild.getIcon()))
                .name(CC.GREEN + guild.getName())
                .build();
    }

    private void addNavigationButtons(Player player) {
        if (currentPage > 0) {
            addButton(Button.create(
                    ItemBuilder.of(Material.ARROW).name(CC.GREEN + "Previous Page").build(),
                    e -> {
                        currentPage--;
                        updateGUI(player);
                    }
            ), (ROWS + 1) * 9 - 9);
        }

        if ((currentPage + 1) * GUILDS_PER_PAGE < guilds.size()) {
            addButton(Button.create(
                    ItemBuilder.of(Material.ARROW).name(CC.GREEN + "Next Page").build(),
                    e -> {
                        currentPage++;
                        updateGUI(player);
                    }
            ), (ROWS + 1) * 9 - 1);
        }

        addButton(Button.create(
                ItemBuilder.of(Material.BARRIER).name(CC.RED + "Close").build(),
                e -> player.closeInventory()
        ), (ROWS + 1) * 9 - 5);
    }
}