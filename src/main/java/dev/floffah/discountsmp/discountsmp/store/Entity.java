package dev.floffah.discountsmp.discountsmp.store;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Deprecated
public class Entity {
    EntityStore store;

    Boolean fromFile = false;
    File file;
    YamlConfiguration data;

    public Entity(EntityStore store) {
        this.store = store;
    }

    public static Entity loadFromFile(EntityStore store, String path) {
        return loadFromFile(store, Paths.get(path).toFile());
    }

    public static Entity loadFromFile(EntityStore store, File file) {
        Entity e = new Entity(store);
        e.fromFile = true;
        e.file = file;
        e.data = YamlConfiguration.loadConfiguration(file);

        return e;
    }

    public static Entity createAtFile(EntityStore store, File file, EntityType type) throws IOException {
        YamlConfiguration dat = new YamlConfiguration();

        dat.save(file);

        return loadFromFile(store, file);
    }

    public enum EntityType {
        Particle
    }
}
