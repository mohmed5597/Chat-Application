import java.time.LocalDateTime;

public class Message {
    private String sender;
    private String receiver;
    private String content;
    private LocalDateTime timestamp;
    private String type;

    public Message(String sender,
                   String receiver,
                   String content,
                   String type){
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    public String toDisplayString(){
        String time = timestamp.format(
                java.time.format.DateTimeFormatter.ofPattern("HH:mm")
        );
        if (type.equals("PRIVATE")){
            return "["+time+"] [private]"+sender+": "+content;
        }
        return "["+time+"] "+sender+": "+content;
    }

    public String getSender(){
        return sender;
    }
    public String getReceiver(){
        return receiver;
    }
    public String getContent(){
        return content;
    }
    public LocalDateTime getTimestamp(){
        return timestamp;
    }
    public String getType(){
        return type;
    }
}
