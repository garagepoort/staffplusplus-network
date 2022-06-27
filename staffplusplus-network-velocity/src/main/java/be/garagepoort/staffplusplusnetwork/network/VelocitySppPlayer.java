package be.garagepoort.staffplusplusnetwork.network;

import be.garagepoort.staffplusplusnetwork.network.common.SppPlayer;
import com.velocitypowered.api.proxy.Player;

import java.util.UUID;

public class VelocitySppPlayer implements SppPlayer {

    private final Player player;

    public VelocitySppPlayer(Player player) {
        this.player = player;
    }

    @Override
    public UUID getId() {
        return player.getUniqueId();
    }

    @Override
    public String getUsername() {
        return player.getUsername();
    }

    @Override
    public String getServerName() {
        return player.getCurrentServer().map(s -> s.getServerInfo().getName()).orElse("Unknown");
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }
}
