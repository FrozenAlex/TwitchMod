package tv.twitch.android.mod.bridges;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {
    public enum FailReason {
        UNKNOWN,
    }

    public void onFailure(Call<T> bVar, Throwable th) {
        th.printStackTrace();
        onRequestFail(FailReason.UNKNOWN);
    }

    public abstract void onRequestSuccess(T t);

    public abstract void onRequestFail(FailReason reason);

    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onRequestSuccess(response.body());
            return;
        }

        onRequestFail(FailReason.UNKNOWN);
    }
}
