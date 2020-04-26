package tv.twitch.android.mod.bridges;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallback<T> implements Callback<T> {
    public enum FailReason {
        UNKNOWN,
        NOT_FOUND,
        UNSUCCESSFUL,
        NULL_BODY
    }

    public void onFailure(Call<T> call, Throwable th) {
        th.printStackTrace();
        onRequestFail(call, FailReason.UNKNOWN);
    }

    protected void retry(Call<T> call) {
        Call<T> clone = call.clone();
        clone.enqueue(this);
    }

    public abstract void onRequestSuccess(T t);

    public abstract void onRequestFail(Call<T> call, FailReason reason);

    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 404) {
            onRequestFail(call, FailReason.NOT_FOUND);
            return;
        }

        if (!response.isSuccessful()) {
            onRequestFail(call, FailReason.UNSUCCESSFUL);
            return;
        }
        if (response.body() == null) {
            onRequestFail(call, FailReason.NULL_BODY);
            return;
        }
        onRequestSuccess(response.body());
    }

    public abstract void fetch();
}
