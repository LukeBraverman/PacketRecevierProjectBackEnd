package packetapp;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.*;

public class AudioRecieve {

    public static void main(String[] args) throws LineUnavailableException, IOException {
        int port = 5000;

        // Set up the audio output
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine speakers = (SourceDataLine) AudioSystem.getLine(info);
        speakers.open(format);
        speakers.start();

        // Set up the network socket
        DatagramSocket socket = new DatagramSocket(port);
        byte[] buffer = new byte[1024]; // Audio buffer

        System.out.println("Receiving audio...");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            speakers.write(packet.getData(), 0, packet.getLength());
        }
    }
}
