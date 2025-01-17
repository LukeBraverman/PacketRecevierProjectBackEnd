    package packetapp;

    import packetapp.HTTPendpoint.HTTPSetUp;
    import packetapp.UDPendpoint.UDPPortListener;

    import java.io.IOException;

    public class App
    {


        public static void main( String[] args ) throws IOException {

            // set up HTTP endpoint to retrieve Log Database
            HTTPSetUp httpSetUp = new HTTPSetUp(8080);

            // Open a UDP port to receive packet information and save in Log Database
            UDPPortListener.listenOnPort(9876);

        }
    }
