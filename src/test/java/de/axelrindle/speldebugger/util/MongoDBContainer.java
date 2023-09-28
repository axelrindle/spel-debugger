package de.axelrindle.speldebugger.util;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

public class MongoDBContainer extends GenericContainer<MongoDBContainer> {

    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("mongo");

    private static final String DEFAULT_TAG = "6";

    private static final int MONGODB_INTERNAL_PORT = 27017;

    private static final String MONGODB_DATABASE_NAME_DEFAULT = "speldebugger";

    public MongoDBContainer() {
        super(DEFAULT_IMAGE_NAME.withTag(DEFAULT_TAG));

        this.setExposedPorts(Collections.singletonList(MONGODB_INTERNAL_PORT));

        this.addEnv("MONGO_INITDB_ROOT_USERNAME", MONGODB_DATABASE_NAME_DEFAULT);
        this.addEnv("MONGO_INITDB_ROOT_PASSWORD", MONGODB_DATABASE_NAME_DEFAULT);
        this.addEnv("MONGO_INITDB_DATABASE", MONGODB_DATABASE_NAME_DEFAULT);

        this.waitStrategy = Wait.forLogMessage("(?i).*Waiting for connections.*", 1);
    }

    public String getConnectionString() {
        var env = getEnvMap();

        return String.format(
                "mongodb://%s:%s@%s:%d/%s?authSource=admin",
                env.get("MONGO_INITDB_ROOT_USERNAME"),
                env.get("MONGO_INITDB_ROOT_PASSWORD"),
                getHost(), getMappedPort(MONGODB_INTERNAL_PORT),
                env.get("MONGO_INITDB_DATABASE")
        );
    }
}
