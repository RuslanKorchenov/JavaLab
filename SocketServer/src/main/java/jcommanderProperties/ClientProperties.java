package jcommanderProperties;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ClientProperties {
    @Parameter(names = {"--server-port"})
    private int port;
    @Parameter(names = {"--server-ip"})
    private String ip;
    @Parameter(names = "--token-file")
    private String token;

    public String getToken() {
        return token;
    }
    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }
}
