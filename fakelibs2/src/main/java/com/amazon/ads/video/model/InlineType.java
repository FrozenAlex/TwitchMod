package com.amazon.ads.video.model;

import java.util.ArrayList;
import java.util.List;

public class InlineType {
    public static class Category {
        public String getAuthority() {
            return "";
        }

        public String getValue() {
            return "";
        }

        public void setAuthority(String str) {
        }

        public void setValue(String str) {
        }
    }

    public static class Creatives {
        public List<CreativeInlineType> getCreatives() {
            return null;
        }
    }

    public static class Survey {
        public String getType() {
            return "";
        }

        public String getValue() {
            return "";
        }

        public void setType(String str) {
        }

        public void setValue(String str) {
        }
    }

    public Creatives getCreatives() {
        return new Creatives();
    }
}
