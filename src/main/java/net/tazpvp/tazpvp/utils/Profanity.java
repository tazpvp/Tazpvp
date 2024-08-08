package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.enums.CC;
import org.bukkit.entity.Player;
import world.ntdi.nrcore.utils.profanity.ProfanityFilter;
import world.ntdi.postglam.data.Tuple;

import java.util.List;

public class Profanity {
    public static Tuple<Boolean, List<String>> censorPhrase(String string) {
        List<String> badWords = ProfanityFilter.badWordsFound(string);

        return new Tuple<>(!badWords.isEmpty(), badWords);
    }

    public static boolean sayNoNo(Player p, String string) {
        Tuple<Boolean, List<String>> censorResult = censorPhrase(string);

        if (censorResult.getA()) {
            StringBuilder sb = new StringBuilder();
            censorResult.getB().forEach(s -> sb.append(s).append(", "));

            p.sendMessage(CC.RED + "I'm sorry, but we do not allow the use of the word(s): " + sb + "in this server.");
            return true;
        }
        return false;
    }
}
