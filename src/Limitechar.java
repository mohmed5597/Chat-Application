public class Limitechar {

    static boolean UserNameLimit(String userName) {
        return userName.length() >= 3 && userName.length() <= 20;
    }
    static boolean messageLimit(String message){
        return message.length() >= 2 && message.length() <= 200;
    }
}
