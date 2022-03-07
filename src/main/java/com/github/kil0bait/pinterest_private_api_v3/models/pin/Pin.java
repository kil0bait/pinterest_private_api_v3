package com.github.kil0bait.pinterest_private_api_v3.models.pin;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

public class Pin {
    private String id;
    private String type;
    private String image_large_url;
    private PixelSize image_large_size_pixels;

    @JsonProperty("images")
    private Map<String, BoardPinVersions> imageVersions;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getImage_large_url() {
        return image_large_url;
    }

    public PixelSize getImage_large_size_pixels() {
        return image_large_size_pixels;
    }

    public Map<String, BoardPinVersions> getImageVersions() {
        return imageVersions;
    }

    public static class BoardPinVersions {
        private int width;
        private int height;
        private String url;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getUrl() {
            return url;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BoardPinVersions that = (BoardPinVersions) o;
            return width == that.width && height == that.height && Objects.equals(url, that.url);
        }

        @Override
        public int hashCode() {
            return Objects.hash(width, height, url);
        }
    }

    public static class PixelSize {
        private int width;
        private int height;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pin pin = (Pin) o;
        return id.equals(pin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
