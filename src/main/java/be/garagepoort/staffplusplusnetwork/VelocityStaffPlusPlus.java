package be.garagepoort.staffplusplusnetwork;

import be.garagepoort.mcsqlmigrations.DatabaseType;
import be.garagepoort.mcsqlmigrations.helpers.QueryBuilderFactory;
import be.garagepoort.staffplusplusnetwork.database.MySQLConnectionProvider;
import be.garagepoort.staffplusplusnetwork.session.PlayerSettingsSqlRepositoryImpl;
import com.google.inject.Inject;
import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

import static be.garagepoort.staffplusplusnetwork.config.ConfigLoader.loadConfig;

@Plugin(id = "staffplusplusnetwork", name = "StaffPlusPlus Network", version = "1.0.0",
    description = "A plugin with network specific commands for staff++", authors = {"Garagepoort"})
public class VelocityStaffPlusPlus {
    private final ProxyServer server;
    private final Logger logger;

    private final Toml CONFIG;

    @Inject
    public VelocityStaffPlusPlus(ProxyServer server, Logger logger, @DataDirectory final Path folder) {
        this.server = server;
        this.logger = logger;
        CONFIG = loadConfig(folder);
        logger.info("StaffPlusPlus network plugin loading!");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        MySQLConnectionProvider mySQLConnectionProvider = new MySQLConnectionProvider(CONFIG);
        QueryBuilderFactory queryBuilderFactory = new QueryBuilderFactory(DatabaseType.MYSQL, mySQLConnectionProvider);
        PlayerSettingsSqlRepositoryImpl playerSettingsSqlRepository = new PlayerSettingsSqlRepositoryImpl(queryBuilderFactory);

        server.getCommandManager().register(CONFIG.getString("commands.personnel"), new PersonnelCommand(server, playerSettingsSqlRepository, CONFIG));
    }

}
