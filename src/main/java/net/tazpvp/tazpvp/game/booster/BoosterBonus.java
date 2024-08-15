package net.tazpvp.tazpvp.game.booster;

import java.util.List;

public record BoosterBonus(double base, List<Double> percentMultipliers, double result) {
    public String prettyPercentMultiplier() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (final double multi : percentMultipliers) {
            stringBuilder.append("+").append((int) (multi * 100)).append("%").append(" ");
        }

        return percentMultipliers.isEmpty() ? "" : "(" + stringBuilder.toString().trim() + ")";
    }

    @Override
    public String toString() {
        return "BoosterBonus{" +
                "base=" + base +
                ", percentMultipliers=" + percentMultipliers +
                ", result=" + result +
                '}';
    }
}
