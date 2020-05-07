package tv.twitch.android.mod.net;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tv.twitch.android.mod.models.api.FfzResponse;


public interface FfzApi {
    @GET("/v1/room/id/{id}")
    Call<FfzResponse> getChannelEmotes(@Path("id") int channelId);
}

