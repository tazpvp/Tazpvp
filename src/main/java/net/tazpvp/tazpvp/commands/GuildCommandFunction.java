package net.tazpvp.tazpvp.commands;

import net.tazpvp.tazpvp.guild.Guild;
import net.tazpvp.tazpvp.utils.data.GuildData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.command.CommandCore;
import world.ntdi.nrcore.utils.command.CommandFunction;

public class GuildCommandFunction extends CommandCore implements CommandFunction {

    public GuildCommandFunction() {
        super("guild", "tazpvp.guild", "g");
        setDefaultFunction(this);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof Player p) {
            Guild g = new Guild("my favoiret guild", p.getUniqueId());
            GuildData.setGuild(g.getID(), g);
        }
    }
}