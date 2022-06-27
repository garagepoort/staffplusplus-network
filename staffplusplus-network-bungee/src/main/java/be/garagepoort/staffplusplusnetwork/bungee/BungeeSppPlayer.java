package be.garagepoort.staffplusplusnetwork.bungee;

import be.garagepoort.staffplusplusnetwork.common.SppPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

public class BungeeSppPlayer implements SppPlayer {
    private final ProxiedPlayer player;

    public BungeeSppPlayer(ProxiedPlayer player) {
        this.player = player;
    }

    @Override
    public UUID getId() {
        return player.getUniqueId();
    }

    @Override
    public String getUsername() {
        return player.getName();
    }

    @Override
    public String getServerName() {
        return player.getServer().getInfo().getName();
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }
}
