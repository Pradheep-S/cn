import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPClient {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress serverAddress = InetAddress.getByName("localhost");
            byte[] sendBuffer;
            byte[] receiveBuffer = new byte[1024];

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Client sends message
                System.out.print("Client: ");
                String clientMessage = scanner.nextLine();
                sendBuffer = clientMessage.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 9876);
                clientSocket.send(sendPacket);

                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client ended the chat.");
                    break;
                }

                // Receive message from server
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                clientSocket.receive(receivePacket);

                String serverResponse = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server: " + serverResponse);

                if (serverResponse.equalsIgnoreCase("exit")) {
                    System.out.println("Server ended the chat. Client shutting down...");
                    break;
                }
            }

            clientSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
