package be.garagepoort.staffplusplusnetwork.network.common;

import be.garagepoort.mcioc.IocBeanProvider;
import be.garagepoort.mcioc.TubingConfiguration;
import be.garagepoort.mcsqlmigrations.DatabaseType;
import be.garagepoort.mcsqlmigrations.SqlConnectionProvider;
import be.garagepoort.mcsqlmigrations.helpers.QueryBuilderFactory;

@TubingConfiguration
public class StaffPlusPlusTubingConfiguration {

    @IocBeanProvider
    public static QueryBuilderFactory queryBuilderFactory(SqlConnectionProvider sqlConnectionProvider) {
        return new QueryBuilderFactory(DatabaseType.MYSQL, sqlConnectionProvider);
    }
}
