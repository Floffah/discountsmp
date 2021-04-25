package dev.floffah.discountsmp.discountsmp.store;

import dev.floffah.discountsmp.discountsmp.DiscountSMP;

import java.io.File;
import java.nio.file.Paths;

@Deprecated
public class EntityStore {
    DiscountSMP main;

    public EntityStore(DiscountSMP main) {
        this.main = main;
    }

    public void load() {
        File data = Paths.get(main.getDataFolder().toPath().toString(), "data").toFile();
        File[] files = data.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.getName().endsWith(".yml")) {
                    Entity e = Entity.loadFromFile(this, f);
                }
            }
        }
    }
}
