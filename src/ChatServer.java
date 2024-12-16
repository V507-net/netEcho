import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {

    ServerSocket server;

    ArrayList<Client> clients = new ArrayList<>();
    int numClients = 0;



    public ChatServer() throws IOException {
        // создаем серверный сокет на порту 1234
        server = new ServerSocket(1234);
    }

    public void pushMsg(String msg) {

        for (Client client : clients)
        {
            client.pollMsg(msg);
        }
    }

    public void run() throws IOException {

        while(true) {

            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = server.accept();
            
            System.out.println("Client connected!");
            clients.add(new Client(socket, this, "Client" + numClients));
            numClients++;

        }

    }

    public static void main(String[] args) throws IOException {

        new ChatServer().run();

    }


}
