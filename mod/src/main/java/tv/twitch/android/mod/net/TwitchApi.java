package tv.twitch.android.mod.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import tv.twitch.android.mod.models.api.TwitchResponse;
import tv.twitch.android.mod.models.api.TwitchUser;

public interface TwitchApi {
    String CLIENT_ID = "kd1unb4b3q4t58fwlpcbzcbnm76a8fp";
    String CLIENT_ID_HEADER = "Client-ID: " + CLIENT_ID;

    @Headers({CLIENT_ID_HEADER})
    @GET("/helix/users")
    Call<TwitchResponse<TwitchUser>> getUsersInfo(@Query("id") int id);
}
