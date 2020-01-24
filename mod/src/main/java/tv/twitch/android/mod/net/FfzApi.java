package tv.twitch.android.mod.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tv.twitch.android.mod.models.api.FfzBadges;
import tv.twitch.android.mod.models.api.FfzGlobalResponse;
import tv.twitch.android.mod.models.api.FfzResponse;

public interface FfzApi {
    @GET("/v1/set/global")
    Call<FfzGlobalResponse> getGlobalEmotes();

    @GET("/v1/room/{name}")
    Call<FfzResponse> getChannelEmotes(@Path("name") String channelName);

    @GET("/v1/room/id/{twitch_id}")
    Call<FfzResponse> getChannelEmotes(@Path("twitch_id") int channelId);

    @GET("/v1/badges")
    Call<FfzBadges> getBadges();
}

