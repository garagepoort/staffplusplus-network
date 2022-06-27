package be.garagepoort.staffplusplusnetwork.network.common;

import java.util.UUID;

public interface SppPlayer {

    UUID getId();

    String getUsername();

    String getServerName();

    boolean hasPermission(String permission);
}
