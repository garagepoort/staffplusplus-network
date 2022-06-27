package be.garagepoort.staffplusplusnetwork.network.common.session;

import net.shortninja.staffplusplus.vanish.VanishType;

import java.util.UUID;

public class PlayerSession {

    private final UUID uuid;
    private String name;
    private VanishType vanishType = VanishType.NONE;

    public PlayerSession(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public VanishType getVanishType() {
        return vanishType;
    }

    public boolean isVanished() {
        return vanishType == VanishType.TOTAL || vanishType == VanishType.PLAYER;
    }

}
