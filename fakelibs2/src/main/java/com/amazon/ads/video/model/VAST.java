package com.amazon.ads.video.model;

import java.util.ArrayList;
import java.util.List;

public class VAST {
    public static class Ad {
        public List<String> getId() {
            return new ArrayList<>();
        }

        public String getLinearClickThroughURL() {
            return null;
        }

        public InlineType getInLine() {
            return new InlineType();
        }
    }
}
