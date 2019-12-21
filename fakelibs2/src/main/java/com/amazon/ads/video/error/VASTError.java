package com.amazon.ads.video.error;

public enum VASTError implements AmazonVideoAdsError {
    ;

    @Override
    public int getErrorCode() {
        return 0;
    }

    @Override
    public String getErrorDetails() {
        return null;
    }

    @Override
    public String getErrorName() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.HTTP_ERROR;
    }

    @Override
    public boolean isFatal() {
        return true;
    }

    @Override
    public VASTError toVASTError() {
        return null;
    }
}
