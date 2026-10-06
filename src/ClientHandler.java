import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter writer;
    private String userName;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    public void sendMessage(String message){
        writer.println(message);
    }
    @Override
    public void run(){
        try {
            /*Clients send massages to each other*/
            writer = new PrintWriter(socket.getOutputStream(),true);
            /*create a reader that read text from socket*/
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            /*first massege is client name*/
            userName = reader.readLine();

            System.out.println(userName+" joined the chat");
            Server.broadcast(userName+" joined the chat");

            /*print messages from clients*/
            while (true) {
                String message = reader.readLine();

                /*if client disconnect*/
                if (message == null){
                    Server.removeClient(this);
                    break;
                }
                System.out.println(message);
                Server.broadcast(message);
                }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
