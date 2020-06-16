package tv.twitch.chat;

import java.util.Date;
import java.util.HashMap;

public class ChatMessageInfo {
    //public ChatMessageBadge[] badges;
    public String displayName;
    //public ChatMessageFlags flags = new ChatMessageFlags();
    public HashMap<String, String> messageTags = new HashMap<>();
    public String messageType;
    public int nameColorARGB;
    public int numBitsSent;
    public int timestamp;
    //public ChatMessageToken[] tokens;
    public int userId;
    public ChatUserMode userMode = new ChatUserMode();
    public String userName;


    public Date dateFromTimestamp() {
        return new Date(((long) this.timestamp) * 1000);
    }
}
