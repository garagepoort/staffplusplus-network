package be.garagepoort.staffplusplusnetwork.velocity;

import be.garagepoort.mcioc.tubingvelocity.TubingVelocityPlugin;
import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

import java.nio.file.Path;

@Plugin(id = "staffplusplusnetwork", name = "StaffPlusPlus Network", version = "1.0.0",
    description = "A plugin with network specific commands for staff++", authors = {"Garagepoort"})
public class VelocityStaffPlusPlus extends TubingVelocityPlugin {

    @Inject
    public VelocityStaffPlusPlus(ProxyServer server, @DataDirectory final Path folder) {
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
