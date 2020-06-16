package tv.twitch.android.mod.bridges;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tv.twitch.android.mod.models.api.FailReason;
import tv.twitch.android.mod.utils.Logger;


public abstract class ApiCallback<T> implements Callback<T> {
    private static final int MAX_RETRIES = 3;

    private int retryCount = 0;


    public void onFailure(Call<T> call, Throwable callThrowable) {
        Logger.debug("retryCount=" + retryCount);

        if (retryCount++ < MAX_RETRIES) {
            Logger.debug("Next try...");
            try {
                retry(call);
                return;
            } catch (Throwable cloneThrowable) {
                cloneThrowable.printStackTrace();
            }
        }

        retryCount = 0;
        callThrowable.printStackTrace();
        onRequestFail(call, FailReason.EXCEPTION);
    }

    protected final void retry(Call<T> call) {
        call.clone().enqueue(this);
    }

    public abstract void onRequestSuccess(T t);

    public abstract void onRequestFail(Call<T> call, FailReason failReason);

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
