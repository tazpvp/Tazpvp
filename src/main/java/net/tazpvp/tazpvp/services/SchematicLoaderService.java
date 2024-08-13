package net.tazpvp.tazpvp.services;

import org.bukkit.Location;

import java.nio.file.Path;

public interface SchematicLoaderService {
    void loadSchematic(Path schematicFile, Location location);
}
