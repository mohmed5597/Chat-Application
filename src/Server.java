import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    static ArrayList<ClientHandler> clients = new ArrayList<>();
    static void broadcast(String message){
        for(ClientHandler client : clients){
            client.sendMessage(message);
        }
    }
    /*to remove a client when disconnect*/
    static void removeClient(ClientHandler client){
        clients.remove(clients);
    }
    public static void main(String[] args){
        try {
            /*create server*/
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server Online");

            /*Clients and handler creator*/
            while (true) {
                /*create a socket*/
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                /*create a handler */
                ClientHandler handler = new ClientHandler(socket);

                /*add client to list*/
                clients.add(handler);

                /*create clients threads*/
                Thread thread = new Thread(handler);
                thread.start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
