package tukano.servers.rest;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import tukano.discovery.Discovery;

import java.net.InetAddress;
import java.net.URI;
import java.util.logging.Logger;

public class RestBlobsServer {
    private static Logger Log = Logger.getLogger(RestShortsServer.class.getName());

    static {
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    public static final int PORT = 5678;
    public static final String SERVICE = "blobs";
    public static final String SERVER_URI_FMT = "http://%s:%s/rest";

    public static void main(String[] args) {

        try {
            ResourceConfig config = new ResourceConfig();
            config.register(  RestBlobsResource.class );

            String ip = InetAddress.getLocalHost().getHostAddress();
            String serverURI = String.format(SERVER_URI_FMT, ip, PORT);
            JdkHttpServerFactory.createHttpServer(URI.create(serverURI), config);

            Discovery discovery = Discovery.getInstance();
            discovery.announce(SERVICE, serverURI);

            Log.info(String.format("%s Server ready @ %s\n", SERVICE, serverURI));

        } catch (Exception e) {
            Log.severe(e.getMessage());
        }
    }
}