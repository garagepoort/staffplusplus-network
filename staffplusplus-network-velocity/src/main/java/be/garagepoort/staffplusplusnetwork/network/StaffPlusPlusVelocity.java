package be.garagepoort.staffplusplusnetwork.network;

import be.garagepoort.mcioc.tubingvelocity.TubingVelocityPlugin;
import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;

@Plugin(id = "staffplusplus-network", name = "StaffPlusPlus Network", version = "1.0.0",
    description = "A plugin with network specific commands for staff++", authors = {"Garagepoort"})
public class StaffPlusPlusVelocity extends TubingVelocityPlugin {

    @Inject
    public StaffPlusPlusVelocity(ProxyServer server, @DataDirectory final Path folder) {
        super(server, folder);
    }

    @Override
    protected void enable() {
    }

    @Override
    public String getName() {
        return "StaffPlusPlusVelocity";
    }
}
