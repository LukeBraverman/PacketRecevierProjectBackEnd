package packetapp.UDPendpoint;

import packetapp.DataAccessLayer.LogDataBase;
import packetapp.Model.PacketLog;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UDPPortListener {

    public static void listenOnPort(int portToListenTo) throws IOException {

        int port = portToListenTo;
        DatagramSocket socket = new DatagramSocket(port);

        while (true)
        {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            System.out.println("Waiting for packets...");
            socket.receive(packet);

            // Extract details
            InetAddress senderIP = packet.getAddress();
            System.out.println("SenderIPTEST" + senderIP.getHostAddress());
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
            newPacketLog.setSenderIp(senderIP.getHostAddress());
            newPacketLog.setData(data);

            LogDataBase.addPacketLog(newPacketLog);

            // Display detailed packet info to console
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

            System.out.println("\n------------------------------------");
        }
    }
}
