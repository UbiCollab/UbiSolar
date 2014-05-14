package com.sintef_energy.ubisolar.configuration;

/**
 * Created by thb on 12.02.14.
 */
import com.yammer.dropwizard.config.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class ServerConfiguration extends Configuration {
    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database;

    public ServerConfiguration() {
        database = new DatabaseConfiguration();
    }
    
    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }
}
