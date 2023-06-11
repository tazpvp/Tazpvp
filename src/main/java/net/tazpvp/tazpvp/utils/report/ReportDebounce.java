package net.tazpvp.tazpvp.utils.report;

import java.util.UUID;

public record ReportDebounce(UUID uuid, long timestamp) {
    public boolean canReport() {
        return System.currentTimeMillis() - timestamp > 1000 * 60 * 30;
    }
}
