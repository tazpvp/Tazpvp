package net.tazpvp.tazpvp.commands.admin.holograms;

import net.tazpvp.tazpvp.enums.CC;
import net.tazpvp.tazpvp.utils.holograms.Hologram;
import net.tazpvp.tazpvp.utils.holograms.HologramUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.joml.Vector3f;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

import java.util.List;

public class HologramCommand extends NRCommand {

    public HologramCommand() {
        super(new Label("hologram", "tazpvp.hologram", "holo"));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(getLabel().getPermission())) {
            sendNoPermission(sender);
            return true;
        }

        if (!(sender instanceof Player p)) {
            sender.sendMessage(CC.RED + "Only players may run this command.");
            return true;
        }

        if (args.length < 1) {
            sendIncorrectUsage(sender, "/hologram <create/remove>");
            return true;
        }

        if (args[0].equalsIgnoreCase("create")) {

            if (args.length < 8) {
                sendIncorrectUsage(p, "/hologram create <id> <background> <alignment> <size> <billboard> <cycle> <text>");
                return true;
            }

            String id = args[1].toLowerCase();
            if (HologramUtil.getAllNames().contains(id)) {
                p.sendMessage(CC.RED + "A hologram with that name already exists! Please choose a different name.");
                return true;
            }

            boolean background;
            switch (args[2].toLowerCase()) {
                case "true":
                    background = true;
                    break;
                case "false":
                    background = false;
                    break;
                default:
                    sender.sendMessage(CC.RED + "Invalid background argument.");
                    return true;
            }

            TextDisplay.TextAlignment alignment;
            switch (args[3].toLowerCase()) {
                case "left":
                    alignment = TextDisplay.TextAlignment.LEFT;
                    break;
                case "center":
                    alignment = TextDisplay.TextAlignment.CENTER;
                    break;
                case "right":
                    alignment = TextDisplay.TextAlignment.RIGHT;
                    break;
                default:
                    sender.sendMessage(CC.RED + "Invalid alignment argument.");
                    return true;
                }

            int size;
            try {
                size = Integer.parseInt(args[4]);
            } catch (NumberFormatException e) {
                sender.sendMessage(CC.RED + "Invalid size argument.");
                return true;
            }

            Display.Billboard billboard;
            switch (args[5].toLowerCase()) {
                case "center":
                    billboard = Display.Billboard.CENTER;
                    break;
                case "horizontal":
                    billboard = Display.Billboard.HORIZONTAL;
                    break;
                case "vertical":
                    billboard = Display.Billboard.VERTICAL;
                    break;
                case "fixed":
                    billboard = Display.Billboard.FIXED;
                    break;
                default:
                    sender.sendMessage(CC.RED + "Invalid billboard argument.");
                    return true;
            }

            boolean shouldCycle;
            switch (args[6].toLowerCase()) {
                case "true":
                    shouldCycle = true;
                    break;
                case "false":
                    shouldCycle = false;
                    break;
                default:
                    sender.sendMessage(CC.RED + "Invalid background argument.");
                    return true;
            }

            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 7; i < args.length; i++) {
                stringBuilder.append(args[i]).append(" ");
            }
            String text = CC.trans(stringBuilder.toString().trim().replace("\\n", "\n"));

            Hologram hologram = new Hologram(id, p.getLocation(), new Vector3f(size, size, size), shouldCycle, alignment, billboard, background, text.split("/split arr/"));
            HologramUtil.addHologram(hologram);
            p.sendMessage(CC.GREEN + "Hologram successfully created!");

        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length != 2) {
                sendIncorrectUsage(p, "/hologram remove <id>");
                return true;
            }

            String id = args[1];
            Entity entityToRemove = HologramUtil.findHologramById(id);

            if (entityToRemove != null) {
                entityToRemove.remove();
                p.sendMessage(CC.GREEN + "Hologram with ID " + id + " has been removed.");
            } else {
                p.sendMessage(CC.RED + "Unable to find hologram with ID " + id);
            }
        } else {
            sendIncorrectUsage(sender, "/hologram <create/remove>");
            return true;
        }
        return true;
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return List.of("create", "remove");
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("remove")) {
                return HologramUtil.getAllNames();
            }
            return List.of("<id: string>");
        }
        if (args.length == 3) {
            return List.of("<background: boolean>", "true", "false");
        }
        if (args.length == 4) {
            return List.of("<alignment>", "left", "right", "center");
        }
        if (args.length == 5) {
            return List.of("<size: int>");
        }
        if (args.length == 6) {
            return List.of("<billboard>", "fixed", "horizontal", "vertical", "center");
        }
        if (args.length == 7) {
            return List.of("<cycle: boolean>", "true", "false");
        }
        if (args.length >= 8) {
            return List.of("\\n", "/split arr/");
        }
        return List.of();
    }
}
