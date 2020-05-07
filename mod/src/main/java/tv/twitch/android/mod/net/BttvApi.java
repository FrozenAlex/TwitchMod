package tv.twitch.android.mod.net;


import java.util.List;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tv.twitch.android.mod.models.api.BttvChannelResponse;
import tv.twitch.android.mod.models.api.BttvEmoteResponse;


public interface BttvApi {
    @GET("/3/cached/emotes/global")
    Call<List<BttvEmoteResponse>> getGlobalEmotes();

    @GET("/3/cached/users/twitch/{id}")
    Call<BttvChannelResponse> getChannelEmotes(@Path("id") int channelId);
}
