package tv.twitch.android.models.chat;


/* compiled from: MessageToken.kt */
public abstract class MessageToken {

    /* compiled from: MessageToken.kt */
    public static final class BitsToken extends MessageToken { // TODO: fix
    }

    /* compiled from: MessageToken.kt */
    public static final class EmoticonToken extends MessageToken {
        public final String getText() {
            return "";
        }
    }

    /* compiled from: MessageToken.kt */
    public static final class MentionToken extends MessageToken {
        public final String getText() {
            return "";
        }
    }

    /* compiled from: MessageToken.kt */
    public static final class TextToken extends MessageToken {
        public final String getText() {
            return "";
        }
    }


    public static final class UrlToken extends MessageToken {
        public final String getUrl() {
            return "";
        }
    }
}