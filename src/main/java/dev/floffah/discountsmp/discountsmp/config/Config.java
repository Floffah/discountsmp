package dev.floffah.discountsmp.discountsmp.config;

import dev.floffah.discountsmp.discountsmp.DiscountSMP;
import dev.floffah.util.config.ConfigProvider;

public class Config extends ConfigProvider {
    DiscountSMP main;

    ConfigValues val;

    public Config(DiscountSMP main) {
        super(main, "config.yml");
        this.main = main;
    }

    @Override
    public void load() {
        val = new ConfigValues();
        val.entities = this.conf.getInt("entities");
    }

    static class ConfigValues {
        int entities;
    }
}
