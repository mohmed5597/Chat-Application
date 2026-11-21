import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    //make a map for user
    static ConcurrentHashMap<String,ClientHandler> clients =
            new ConcurrentHashMap<>();


     static void main(String[] args){
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

                /*create clients threads*/
                Thread thread = new Thread(handler);
                thread.start();
            }
        }catch (IOException e){
            System.out.println("couldn't connect client");
        }
    }
    //add new user
    static boolean registerClient(String userName, ClientHandler client){
        String key = userName.toLowerCase();
        return clients.putIfAbsent(key,client) == null;
    }
    //remove user
    static void removeClient(ClientHandler client){
        if (client.getUserName() != null){
            clients.remove(client. getUserName().toLowerCase(),client);
            broadcast(client.getUserName()+" left tha chat");
        }
    }
    //send message
    static void broadcastMessage(Message message){
         String formattedMessage = message.toDisplayString();
         for(ClientHandler client : clients.values()){
             client.sendMessage(formattedMessage);
         }
    }

    static void broadcast(String message){
        for (ClientHandler client : clients.values()){
            client.sendMessage(message);
        }
    }
    //find user
    static ClientHandler findClient(String userName){
        return clients.get(userName.toLowerCase());
    }
}
