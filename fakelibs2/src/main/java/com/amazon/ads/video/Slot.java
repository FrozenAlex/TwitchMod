package com.amazon.ads.video;

public class Slot {
    public static final class Builder {
        public final Slot build() {
            return new Slot();
        }

        public final Builder withAdUnit(String str) {
            return this;
        }

        public final Builder withKeyValuePair(String str, String str2) {
            return this;
        }

        public final Builder withMediaType(MediaType mediaType2) {
            return this;
        }

        public final Builder withSlotId(String str) {
            return this;
        }

        public final Builder withSlotSize(String str) {
            return this;
        }
    }

    public enum MediaType {
        video("v");

        private MediaType(String str) {
        }

        final String getValue() {
            return "";
        }
    }
}