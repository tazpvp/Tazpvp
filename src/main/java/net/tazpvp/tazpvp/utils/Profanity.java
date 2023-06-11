package net.tazpvp.tazpvp.utils;

import net.tazpvp.tazpvp.Tazpvp;
import world.ntdi.nrcore.utils.profanity.ProfanityFilter;

public class Profanity {
    public static String censor(String string) {
        ProfanityFilter filter = new ProfanityFilter();
        filter.buildDictionaryTree(Tazpvp.getInstance().getDataFolder().getAbsolutePath() + "/badwords_en_US.txt");

        return filter.filterBadWords(string);
    }

    public boolean containsNaughtyVocabulary(String phrase) {
        phrase = phrase.toLowerCase()
                .replaceAll("1", "i")
                .replaceAll("!", "i")
                .replaceAll("@", "a")
                .replaceAll("#", "")
                .replaceAll("\\$", "s")
                .replaceAll("%", "")
                .replaceAll("3", "e")
                .replaceAll("2", "s")
                .replaceAll("4", "a")
                .replaceAll("\\^", "")
                .replaceAll("\\.", "")
                .replaceAll("\\-", "")
                .replaceAll("6", "g")
                .replaceAll("7", "t")
                .replaceAll("&", "b")
                .replaceAll("8", "b")
                .replaceAll("0", "o")
                .replaceAll(" ", "")
                .replaceAll("\\*", "")
                .replaceAll("~", "")
                .replaceAll("`", "");

        return false; // TODO: Finish
    }
}
