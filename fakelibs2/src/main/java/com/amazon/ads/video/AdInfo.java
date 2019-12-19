package com.amazon.ads.video;

import com.amazon.ads.video.model.VAST;

public class AdInfo {
    public VAST.Ad getAd() {
        return new VAST.Ad();
    }

    public int getAdPodSize() {
        return 0;
    }

    public int getAdPosition() {
        return 0;
    }

    public String getAppInstallButtonText() {
        return "";
    }

    public String getAppInstallStoreID() {
        return "";
    }

    public String getAppInstallStoreIconURL() {
        return "";
    }

    public String getAppInstallTitle() {
        return "";
    }

    public long getDuration() {
        return 0;
    }


    public boolean isAppInstallAd() {
        return false;
    }

    public void setAdPodSize(int i) {
    }

    public void setAdPosition(int i) {
    }
}
