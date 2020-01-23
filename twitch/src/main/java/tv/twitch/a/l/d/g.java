package tv.twitch.a.l.d;

import java.util.List;

import tv.twitch.android.models.chat.MessageBadge;
import tv.twitch.android.models.chat.MessageToken;

// Source: ChatMessageInterface
public interface g {
    boolean a();

    // UserName
    String b();

    boolean c();

    // UserId
    int d();

    List<MessageToken> e();

    List<MessageBadge> f();

    boolean g();

    String getDisplayName();
}