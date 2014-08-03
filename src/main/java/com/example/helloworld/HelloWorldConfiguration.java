package com.example.helloworld;

import com.example.helloworld.core.Template;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.db.DatabaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
public class HelloWorldConfiguration extends Configuration {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldConfiguration.class);
    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }

    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        LOGGER.info("XXX getting db factory");
        LOGGER.info("OLD DB=" + database.getUrl());
        DatabaseConfiguration databaseConfiguration = ExampleDatabaseConfiguration.create(System.getenv("DATABASE_URL"));
        database = databaseConfiguration.getDataSourceFactory(null);
        LOGGER.info("NEW DB=" + database.getUrl());
        return database;
    }

    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        LOGGER.info("XXX setting db factory");
        this.database = dataSourceFactory;
    }
}
