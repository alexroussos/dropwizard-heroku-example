package com.example.helloworld;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

// TODO Make this not so ugly
public class ExampleDatabaseConfiguration implements DatabaseConfiguration {
    final static Logger logger = LoggerFactory.getLogger(ExampleDatabaseConfiguration.class);
    private static DatabaseConfiguration databaseConfiguration;

    public static DatabaseConfiguration create(String databaseUrl) {
        if (databaseUrl == null) {
            throw new IllegalArgumentException("The DATABASE_URL environment variable must be set before running the app " +
                    "example: DATABASE_URL=\"postgres://username:password@host:5432/dbname\"");
        }
        DatabaseConfiguration databaseConfiguration = null;
        try {
            URI dbUri = new URI(databaseUrl);
            final String user = dbUri.getUserInfo().split(":")[0];
            final String password = dbUri.getUserInfo().split(":")[1];
            final String url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            databaseConfiguration = new DatabaseConfiguration() {
                DataSourceFactory dataSourceFactory;
                @Override
                public DataSourceFactory getDataSourceFactory(Configuration configuration) {
                    if (dataSourceFactory!= null) {
                        return dataSourceFactory;
                    }
                    DataSourceFactory dsf = new DataSourceFactory();
                    dsf.setUser(user);
                    dsf.setPassword(password);
                    dsf.setUrl(url);
                    dsf.setDriverClass("org.postgresql.Driver");
                    dataSourceFactory = dsf;
                    return dsf;
                }
            };
        } catch (URISyntaxException e) {
            logger.info(e.getMessage());
        }
        return databaseConfiguration;
    }

    @Override
    public DataSourceFactory getDataSourceFactory(Configuration configuration) {
        if (databaseConfiguration == null) {
            throw new IllegalStateException("You must first call DatabaseConfiguration.create(dbUrl)");
        }
        return databaseConfiguration.getDataSourceFactory(null);
    }
}