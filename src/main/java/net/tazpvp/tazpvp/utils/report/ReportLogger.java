package net.tazpvp.tazpvp.utils.report;

import java.util.UUID;

public record ReportLogger(UUID uuid, String reason, long timestamp) {
}
