package be.garagepoort.staffplusplusnetwork.common;

import java.util.UUID;

public interface SppPlayer {

    UUID getId();

    String getUsername();

    String getServerName();

    boolean hasPermission(String permission);
}
