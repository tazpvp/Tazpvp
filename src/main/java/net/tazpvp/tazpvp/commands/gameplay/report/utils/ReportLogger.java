package net.tazpvp.tazpvp.commands.gameplay.report.utils;

import java.util.UUID;

public record ReportLogger(UUID uuid, String reason, long timestamp) {
}
