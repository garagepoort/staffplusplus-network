package be.garagepoort.staffplusplusnetwork.session;

import net.shortninja.staffplusplus.vanish.VanishType;

import java.util.UUID;

public class PlayerSettings {

    private final UUID uuid;
    private final VanishType vanishType;
    private final boolean staffMode;

    public PlayerSettings(UUID uuid, VanishType vanishType, boolean staffMode) {
        this.uuid = uuid;
        this.vanishType = vanishType;
        this.staffMode = staffMode;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isVanished() {
        return vanishType == VanishType.TOTAL || vanishType == VanishType.PLAYER;
    }
}
