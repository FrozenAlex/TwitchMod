package tv.twitch.a.k.g;

import java.util.List;

import tv.twitch.android.models.chat.MessageToken;

/**
 * Source: ChatMessageInterface
 */
public interface g {
    boolean a();

    // UserName
    String b();

    boolean c();

    // UserId
    int d();

    List<MessageToken> e();

    List<Object> f();

    boolean g();

    String getDisplayName();
}