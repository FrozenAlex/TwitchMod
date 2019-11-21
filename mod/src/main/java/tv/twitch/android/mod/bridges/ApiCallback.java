package tv.twitch.android.mod.bridges;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {
    public enum FailReason {
        UNKNOWN,
        UNSUCCESSFUL,
        NULL_BODY
    }

    public void onFailure(Call<T> bVar, Throwable th) {
        th.printStackTrace();
        onRequestFail(FailReason.UNKNOWN);
    }

    public abstract void onRequestSuccess(T t);

    public abstract void onRequestFail(FailReason reason);

    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            if (response.body() == null) {
                onRequestFail(FailReason.NULL_BODY);
                return;
            }
            onRequestSuccess(response.body());
        }

        onRequestFail(FailReason.UNSUCCESSFUL);
    }

    public abstract void fetch();
}
