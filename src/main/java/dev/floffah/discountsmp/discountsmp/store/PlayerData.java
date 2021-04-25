package dev.floffah.discountsmp.discountsmp.store;

import org.bukkit.entity.Player;

@Deprecated
public class PlayerData {
    public boolean isMechanic = false;
    Player player;

    public PlayerData(Player player) {
        this.player = player;
    }

    public void setMechanic(boolean mechanic) {
        isMechanic = mechanic;
    }

    public Player getPlayer() {
        return player;
    }

    public void destroy() {

    }
}
