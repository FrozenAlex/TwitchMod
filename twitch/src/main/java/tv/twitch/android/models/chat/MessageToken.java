package tv.twitch.android.models.chat;


public abstract class MessageToken {
    public static final class EmoticonToken extends MessageToken {
        public final String getText() {
            return "";
        }
    }

    public static final class MentionToken extends MessageToken {
        public final String getText() {
            return "";
        }
    }

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