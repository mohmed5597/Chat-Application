import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try {
            String userName;
            /* connect to server*/
            Socket socket = new Socket("localhost", 5000);
            System.out.println("connected to server!");

            /*create writer to send text using socket*/
            PrintWriter writer =
                    new PrintWriter(socket.getOutputStream(),true);

            /*create a connection between clients*/
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

           /*Create imput*/
            Scanner imput = new Scanner(System.in);

            /*client recive massage*/
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = reader.readLine();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();

            /*create username*/
            while(true) {
                System.out.print("Enter UserName: ");
                userName = imput.nextLine();
                /*send username*/
                if (Limitechar.UserNameLimit(userName)){
                    writer.println(userName);
                    break;
                }else {
                    System.out.println("User name needs to be between 3 and 20 characters");
                }
            }

            /* get message from user */
            while (true) {
                System.out.print("Message: ");
                String message = imput.nextLine();
                if (Limitechar.messageLimit(message)){
                    writer.println(userName+": "+message);
                }
                else {
                    System.out.println("Messages need to be between 2 and 200 characters");
                }

            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
