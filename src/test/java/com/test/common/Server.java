package com.test.common;

public class Server {

    private final String host;
    private final int port;

    public Server(final String host, final int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getAppURL() {
        return String.format("%s:%d", host, port);
    }

    private static Server httpEndpoint = new Server("http://localhost", 8080);
    private static Server appFrontEnd = new Server("http://localhost", 5000);

    public static Server getHTTPEndpoint() {
        return httpEndpoint;
    }

    public static Server getAppFrontEnd() {
        return appFrontEnd;
    }

}
