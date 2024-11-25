import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class UDPServer {
    public static void main(String[] args) {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            Scanner scanner = new Scanner(System.in);

            System.out.println("Server is running and waiting for messages...");

            while (true) {
                // Receive message from client
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                serverSocket.receive(receivePacket);

                String clientMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Client: " + clientMessage);

                // Exit condition
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Client ended the chat. Server shutting down...");
                    break;
                }

                // Server responds to client
                System.out.print("Server: ");
                String serverMessage = scanner.nextLine();
                sendBuffer = serverMessage.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,
                        receivePacket.getAddress(), receivePacket.getPort());
                serverSocket.send(sendPacket);

                if (serverMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Server ended the chat. Shutting down...");
                    break;
                }
            }

            serverSocket.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
