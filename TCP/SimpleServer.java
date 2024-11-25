import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is listening on port 5000");
            
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ServerThread(socket).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true)) {
                BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String text,text1;

            do {
                text = reader.readLine();
                System.out.println("Received: " + text);
                System.out.print("Enter message: ");
                text1 = consoleReader.readLine();
                writer.println("Response: " + text1);
            } while (!text.equals("bye"));

            socket.close();
        } catch (IOException ex) {
            System.out.println("ServerThread exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}