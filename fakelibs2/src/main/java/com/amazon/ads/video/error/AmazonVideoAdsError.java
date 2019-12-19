package com.amazon.ads.video.error;

public interface AmazonVideoAdsError {

    public enum ErrorType {
        APP_ERROR("AppError"),
        VIEWABILITY_ERROR("ViewabilityError"),
        VAST_ERROR("VASTError"),
        PLAYER_ERROR("MediaPlayerError"),
        PLAYBACK_ERROR("PlaybackError"),
        HTTP_ERROR("HttpError");

        private ErrorType(String str) {
        }

        public final String toString() {
            return "";
        }
    }

    int getErrorCode();

    String getErrorDetails();

    String getErrorName();

    ErrorType getErrorType();

    boolean isFatal();

    String toString();

    VASTError toVASTError();
}
