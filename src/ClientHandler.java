import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean registered;

    private String userName;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            //create a reader that read text from socket
            reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream() ) );

            /*Clients send messages to each other*/
            writer = new PrintWriter(socket.getOutputStream(),true);

            /*first message is client name*/
            userName = reader.readLine();

            if (userName == null|| !userName.matches("[a-zA-Z0-9_]{3,20}")){
                sendMessage("ERROR: invalid name");
                return;
            }
            if (!Server.registerClient(userName,this)){
                sendMessage("ERROR: Username already taken.");
                return;
            }
            registered = true;

            sendMessage("ok: welcome "+userName);

            Server.broadcast(userName+" joined");

            //receive messages continuously
            String message;

            /*print messages from clients*/
            while ((message = reader.readLine()) != null) {

                //logout
                if (message.equals("/quit")){
                    break;
                }
                handleMessage(message);

                }

        }catch (IOException e){
            System.out.println(userName +" disconnected");
        }finally {
            if (registered){
            Server.removeClient(this);
            }
            try {
                socket.close();
            }catch (IOException e){
                System.out.println("could not remove");
            }
        }
    }
    private void handleMessage(String message){
        if (message.equals("/users")){
            showOnLineUsers();
        } else if (message.startsWith("/msg")) {
            privateMessage(message);
        }else {
            Message chatMessage = new Message(
                    userName,null,message,"PUBLIC"
            );
            Server.broadcastMessage(chatMessage);
        }
    }
    //public message
    private void showOnLineUsers(){
        StringBuilder users = new StringBuilder();
        users.append("online users: ");
                for(ClientHandler clint : Server.clients.values()){
                    users.append(clint.getUserName()).append(" ");
                }
                sendMessage(users.toString());
    }
    //private message
    private void privateMessage(String message){
        String[] parts = message.split(" ", 3);
        if(parts.length < 3){
            sendMessage("Usage: /msg username message");
            return;
        }
        String tagetUsername = parts[1];
        String privateText = parts[2];
        ClientHandler target = Server.findClient(tagetUsername);
        if (target == null){
            sendMessage("User not found ");
            return;
        }
        Message privateMessage = new Message(
                userName,tagetUsername,privateText,"PRIVATE"
        );
        target.sendMessage(privateMessage.toDisplayString());
        sendMessage("message sent to " + tagetUsername);
    }

    public void sendMessage(String message){
        if (writer !=null){
            writer.println(message);
        }
    }
    public String getUserName(){
        return userName;
    }

}
