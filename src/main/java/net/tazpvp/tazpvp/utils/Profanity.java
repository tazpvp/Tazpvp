package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.Tazpvp;
import world.ntdi.nrcore.utils.profanity.ProfanityFilter;

public class Profanity {
    public static String censor(String string) {
        ProfanityFilter filter = new ProfanityFilter();
        filter.buildDictionaryTree(Tazpvp.getInstance().getDataFolder().getAbsolutePath() + "/badwords_en_US.txt");

        return filter.filterBadWords(string);
    }
}
