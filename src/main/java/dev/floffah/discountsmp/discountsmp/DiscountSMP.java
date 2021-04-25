package dev.floffah.discountsmp.discountsmp;

import dev.floffah.discountsmp.discountsmp.commands.Disc;
import dev.floffah.discountsmp.discountsmp.commands.Tellall;
import dev.floffah.discountsmp.discountsmp.commands.Toggles;
import dev.floffah.discountsmp.discountsmp.config.Config;
import dev.floffah.discountsmp.discountsmp.listeners.Blocks;
import dev.floffah.discountsmp.discountsmp.listeners.Players;
import dev.floffah.discountsmp.discountsmp.store.PlayerData;
import net.luckperms.api.LuckPerms;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class DiscountSMP extends JavaPlugin {
    public LuckPerms lp;
    Disc discmd;
    Tellall tacmd;
    Toggles toggles;
    Config conf;
    Blocks blocks;
    Players plyrs;
    @Deprecated
    Map<UUID, PlayerData> pd = new HashMap<>();

    @Override
    public void onEnable() {
        if (!Paths.get(getDataFolder().toPath().toString(), "data").toFile().exists()) {
            Paths.get(getDataFolder().toPath().toString(), "data").toFile().mkdirs();
        }
        conf = new Config(this);

        RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null) {
            getLogger().severe("Could not hook into LuckPerms. Disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        lp = provider.getProvider();

        discmd = new Disc(this);
        tacmd = new Tellall(this);
        toggles = new Toggles(this);
        plyrs = new Players(this);
        blocks = new Blocks(this);
        getCommand("disc").setExecutor(discmd);
        getCommand("disc").setTabCompleter(discmd);
        getCommand("tellall").setExecutor(tacmd);
        getCommand("tellall").setTabCompleter(tacmd);
        getCommand("toggle").setExecutor(toggles);
        getCommand("toggle").setTabCompleter(toggles);
        getServer().getPluginManager().registerEvents(plyrs, this);
        getServer().getPluginManager().registerEvents(blocks, this);

        getLogger().info("Enabled DiscountSMP");
    }

    public PlayerData getPlayer(UUID uuid) {
        if (pd.containsKey(uuid)) {
            return pd.get(uuid);
        } else {
            PlayerData p = new PlayerData(getServer().getPlayer(uuid));
            pd.put(uuid, p);
            return p;
        }
    }

    public Map<UUID, PlayerData> getPd() {
        return pd;
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled DiscountSMP");
    }
}
