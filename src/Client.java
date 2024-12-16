import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;
    ChatServer server;
    String clientName;

    Scanner in;
    PrintStream out;

    public Client(Socket socket, ChatServer server, String clientName){

        this.socket = socket;
        this.server = server;
        this.clientName = clientName;
        // запускаем поток
        new Thread(this).start();
    }

    public void pollMsg(String msg)
    {
        out.println(msg);
    }



    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat server! " + clientName);

            String input = in.nextLine();

            while (!input.equals("bye")) {

                server.pushMsg(clientName + ": " + input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}