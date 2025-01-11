package org;

import javax.sound.sampled.*;
import java.net.*;

public class AudioSend {

    public static void main(String[] args) throws Exception {
        int port = 5000;
        InetAddress receiverAddress = InetAddress.getByName("192.168.1.253"); // Replace with receiver's IP

        // Set up the audio input
        AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
        TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
        microphone.open(format);
        microphone.start();

        // Set up the network socket
        DatagramSocket socket = new DatagramSocket();

        byte[] buffer = new byte[1024]; // Audio buffer
        System.out.println("Sending audio...");

        while (true) {
            int bytesRead = microphone.read(buffer, 0, buffer.length);
            DatagramPacket packet = new DatagramPacket(buffer, bytesRead, receiverAddress, port);
            socket.send(packet);
        }
    }
}
