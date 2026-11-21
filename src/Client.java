import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
     static void main(String[] args){
        try {
            /* connect to server*/
            Socket socket = new Socket("localhost", 5000);

            /*create writer to send text using socket*/
            PrintWriter writer =
                    new PrintWriter(socket.getOutputStream(),true);

            /*create a connection between clients*/
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream() ) );

           /*Create input*/
            Scanner scanner = new Scanner(System.in);

            //username
            System.out.println("/quit to exit program /msg to send private messages");
            System.out.print("Enter your username: ");
            String username= scanner.nextLine();
            writer.println(username);

            //wait server response
            String response = reader.readLine();
            if (response == null || response.startsWith("ERROR:")){
                System.out.println(response);
                socket.close();
                return;
            }
            System.out.println(response);

            /*client receive thread message*/
            Thread receiveThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) !=null){
                        System.out.println("\n"+message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server");
                }
            });
            receiveThread.start();

            /*create username*/
            while(true) {
                System.out.print("Message: ");
                String message = scanner.nextLine();
                writer.println(message);
                /*send username*/
                if (message.equals("/quit")){
                    socket.close();
                    break;
                }
            }

        }catch (IOException e){
            System.out.println("could not connect to server");
        }
    }
}
