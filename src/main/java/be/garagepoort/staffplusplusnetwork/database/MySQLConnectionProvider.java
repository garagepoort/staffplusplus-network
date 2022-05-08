package be.garagepoort.staffplusplusnetwork.database;

import be.garagepoort.mcsqlmigrations.DatabaseType;
import be.garagepoort.mcsqlmigrations.SqlConnectionProvider;
import com.moandjiezana.toml.Toml;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MySQLConnectionProvider implements SqlConnectionProvider {

    private final Toml config;
    private HikariDataSource datasource;

    public MySQLConnectionProvider(Toml config) {
        this.config = config;
    }

    public DataSource getDatasource() {
        if (datasource == null) {
            getDataSource();
        }
        return datasource;
    }

    @Override
    public List<String> getMigrationPackages() {
        return List.of();
    }

    @Override
    public DatabaseType getDatabaseType() {
        return DatabaseType.MYSQL;
    }

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return getDatasource().getConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException("Failed to connect to the database", e);
        }
    }

    private void getDataSource() {
        if (datasource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl("jdbc:mysql://" + config.getString("storage.host") + ":" + config.getLong("storage.port") + "/" + config.getString("storage.database") + "?autoReconnect=true&useSSL=" + config.getBoolean("storage.ssl-enabled") + "&allowMultiQueries=true&allowPublicKeyRetrieval=true");
            hikariConfig.setUsername(config.getString("storage.user"));
            hikariConfig.setPassword(config.getString("storage.password"));
            hikariConfig.setMaximumPoolSize(config.getLong("storage.max-pool-size").intValue());
            hikariConfig.setLeakDetectionThreshold(5000);
            hikariConfig.setAutoCommit(true);
            hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
            hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            datasource = new HikariDataSource(hikariConfig);
        }
    }
}
