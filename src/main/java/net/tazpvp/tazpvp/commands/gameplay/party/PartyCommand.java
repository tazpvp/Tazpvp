package net.tazpvp.tazpvp.commands.gameplay.party;

import lombok.NonNull;
import net.tazpvp.tazpvp.objects.PartyObject;
import net.tazpvp.tazpvp.wrappers.PlayerWrapper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import world.ntdi.nrcore.utils.command.simple.Label;
import world.ntdi.nrcore.utils.command.simple.NRCommand;

public class PartyCommand extends NRCommand {

    public PartyCommand() {
        super(new Label("party", null, "team", "p"));
    }

    @Override
    public boolean execute(@NonNull CommandSender sender, @NotNull @NonNull String[] args) {

        if (!(sender instanceof Player p)) return true;

        PlayerWrapper pw = PlayerWrapper.getPlayer(p);

        if (args.length == 1) {
            Player target = getPlayer(args[0]);
            if (target != null) {
                if (pw.getParty() != null) {
                    PartyObject party = pw.getParty();
                    party.invitePlayer(target);
                }
            }
        } else if (args.length == 2) {
            if (args[1].equalsIgnoreCase("leave")) {
                pw.setParty(null);
            } else if (args[1].equalsIgnoreCase("chat")) {
                PartyObject.send(p, "Toggled party chat.");
            }
        } else if (args.length == 3) {
            Player requester = getPlayer(args[2]);
            if (args[1].equalsIgnoreCase("join") && requester != null) {
                if (PartyObject.inviteList.containsKey(p.getUniqueId())) {
                    PartyObject party = PartyObject.inviteList.get(p.getUniqueId());
                    if (party.getLeader().equals(requester.getUniqueId())) {
                        party.addMember(p);
                        PartyObject.inviteList.remove(p.getUniqueId());
                    }
                }
            }
        }
        return super.execute(sender, args);
    }

    private Player getPlayer(String string) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().equalsIgnoreCase(string)) {
                return p;
            }
        }
        return null;
    }


}
