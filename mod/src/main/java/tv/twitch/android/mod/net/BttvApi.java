package tv.twitch.android.mod.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tv.twitch.android.mod.models.api.BttvResponse;

public interface BttvApi {
    @GET("/2/emotes")
    Call<BttvResponse> getGlobalEmotes();

    @GET("/2/channels/{id}")
    Call<BttvResponse> getChannelEmotes(@Path("id") String channelId);
}
