    package packetapp;

    import com.google.gson.Gson;

    import java.io.IOException;
    import java.net.DatagramPacket;
    import java.net.DatagramSocket;
    import java.net.InetAddress;
    import java.net.NetworkInterface;
    import java.sql.SQLOutput;
    import java.time.Instant;
    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.time.LocalDate;
    import java.util.ArrayList;

    import static spark.Spark.*;

    /**
     * Hello world!
     *
     */
    public class App
    {


        public static void main( String[] args ) throws IOException {

            ArrayList<PacketLog> packedLogs = new ArrayList<>();
            Gson gson = new Gson();

            port(8080);

            // ✅ Add CORS Configuration Here
            options("/*", (request, response) -> {
                String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
                if (accessControlRequestHeaders != null) {
                    response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
                }

                String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
                if (accessControlRequestMethod != null) {
                    response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
                }

                return "OK";
            });

            before((request, response) -> {
                response.header("Access-Control-Allow-Origin", "*");  // Allows requests from any origin
                response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            });

            PacketLog packetLog = new PacketLog();

            LocalTime now = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            String formattedTime = now.format(formatter);
            packetLog.setTime(formattedTime);

            LocalDate today = LocalDate.now();
            // Define the desired date format (dd/MM/yyyy)
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Format the date as a String
            String formattedDate = today.format(formatter2);
            packetLog.setData(formattedDate);

            packetLog.setPacketLength(1);
            packetLog.setBufferSize(2);
            packetLog.setSenderPort(3);
            packetLog.setSenderIp(0);
            packetLog.setData("This is a test log.");

            packedLogs.add(packetLog);

            get("/hello", (req, res) -> "Hello from Spark Java!");

            get("/health", (req, res) -> {
                res.status(200);
                return "OK";
            });

            get("/logs", (req, res) -> {
                res.status(200);
                return gson.toJson(packedLogs);  // Convert ArrayList to JSON
            });


            int port = 9876;
            DatagramSocket socket = new DatagramSocket(port);


            while (true)
            {
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

                PacketLog newPacketLog = new PacketLog();

                LocalTime nowA = LocalTime.now();
                DateTimeFormatter formatterA = DateTimeFormatter.ofPattern("h:mm a");
                String formattedTimeA = nowA.format(formatterA);
                newPacketLog.setTime(formattedTimeA);

                LocalDate todayB = LocalDate.now();
                // Define the desired date format (dd/MM/yyyy)
                DateTimeFormatter formatterB = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                // Format the date as a String
                String formattedDateB = todayB.format(formatterB);
                newPacketLog.setData(formattedDateB);

                newPacketLog.setPacketLength(packetLength);
                newPacketLog.setBufferSize(bufferSize);
                newPacketLog.setSenderPort(senderPort);
                newPacketLog.setSenderIp(1);
                newPacketLog.setData(data);

                packedLogs.add(newPacketLog);

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
    }
