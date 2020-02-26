package jcommanderProperties;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ServerProperties {
    @Parameter(names = {"--port"})
    private int port;
    @Parameter(names = "--db-properties")
    private String properties;

    public int getPort() {
        return port;
    }

    public String getProperties() {
        return properties;
    }
}
