package net.tazpvp.tazpvp.commands.gameplay.report.utils;

import java.util.UUID;

public record ReportDebounce(UUID uuid, long timestamp) {
    public boolean canReport() {
        return System.currentTimeMillis() - timestamp > 1000 * 60 * 30;
    }
}
