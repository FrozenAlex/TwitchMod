package tv.twitch.a.k.g;

import java.util.List;

import tv.twitch.android.models.chat.MessageToken;

/**
 * Source: ChatMessageInterface
 */
public interface g {
    boolean a();

    /**
     * @return Username
     */
    String b();

    boolean c();

    /**
     * @return User ID
     */
    int d();

    List<MessageToken> e();

    List<Object> f();

    boolean g();

    String getDisplayName();
}