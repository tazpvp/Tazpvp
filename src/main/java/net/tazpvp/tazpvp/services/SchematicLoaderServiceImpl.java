package net.tazpvp.tazpvp.services;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class SchematicLoaderServiceImpl implements SchematicLoaderService{
    @Override
    public void loadSchematic(final Path schematicFile, final Location location) {
        if (!schematicFile.toFile().exists()) {
            throw new NullPointerException("Schematic does not exist at " + schematicFile.toAbsolutePath());
        }

        if (location.getWorld() == null) {
            throw new NullPointerException("World is NULL in location");
        }

        try (ClipboardReader reader = ClipboardFormats.findByFile(schematicFile.toFile()).getReader(new FileInputStream(schematicFile.toFile()))) {
            Clipboard clipboard = reader.read();
            World world = BukkitAdapter.adapt(location.getWorld());
            EditSession editSession = WorldEdit.getInstance().newEditSession(world);

            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BukkitAdapter.asBlockVector(location))
                    .ignoreAirBlocks(true)
                    .build();

            Operations.complete(operation);
        } catch (IOException | WorldEditException e) {
            throw new RuntimeException(e);
        }
    }
}
