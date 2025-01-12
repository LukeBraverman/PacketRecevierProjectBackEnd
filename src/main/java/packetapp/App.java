    package packetapp;

    import java.io.IOException;
    import java.net.DatagramPacket;
    import java.net.DatagramSocket;
    import java.net.InetAddress;
    import java.net.NetworkInterface;
    import java.sql.SQLOutput;
    import java.time.Instant;

    import static spark.Spark.*;

    /**
     * Hello world!
     *
     */
    public class App
    {
        public static void main( String[] args ) throws IOException {

            port(8080);

            get("/hello", (req, res) -> "Hello from Spark Java!");

            int port = 9876;
            DatagramSocket socket = new DatagramSocket(port);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Waiting for packets...");
            socket.receive(packet);

            // Extract details
            InetAddress senderIP = packet.getAddress();
            int senderPort = packet.getPort();
            int packetLength = packet.getLength();
            int bufferSize = packet.getData().length;
            int offset = packet.getOffset();
            String data = new String(packet.getData(), 0, packet.getLength());

            // Display detailed packet info
            System.out.println("\n📦 Packet Received!");
            System.out.println("🔹 Sender IP: " + senderIP);
            System.out.println("🔹 Sender Port: " + senderPort);
            System.out.println("🔹 Packet Length: " + packetLength + " bytes");
            System.out.println("🔹 Buffer Size: " + bufferSize + " bytes");
            System.out.println("🔹 Offset: " + offset);
            System.out.println("🔹 Data (String): " + data);
            System.out.print("🔹 Data (Hex): ");
            for (int i = 0; i < packetLength; i++) {
                System.out.printf("%02X ", buffer[i]);
            }
            Instant timestamp = Instant.now();
            System.out.println("🕒 Timestamp: " + timestamp);
            int actualSize = packet.getLength();
            System.out.println("📏 Packet Size: " + actualSize + " bytes");
            System.out.println("📦 Buffer Size: " + bufferSize + " bytes");

            if (data.startsWith("GET") || data.startsWith("POST")) {
                System.out.println("🌐 Protocol: HTTP Request");
            }

            if (packet.getPort() == 53) {
                System.out.println("🌐 Protocol: DNS Query");
            }

//            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(packet.getAddress());
//            System.out.println("🌐 Interface: " + networkInterface.getDisplayName());

            System.out.println("\n------------------------------------");

        }
    }
