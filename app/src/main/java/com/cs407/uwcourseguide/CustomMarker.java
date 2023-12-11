package com.cs407.uwcourseguide;

import com.google.android.gms.maps.model.LatLng;

public class CustomMarker {
    private LatLng position;
    private String title;
    private String snippet;
    private float color; // Marker color (Hue value)

    public CustomMarker(LatLng position, String title, String snippet, float color) {
        this.position = position;
        this.title = title;
        this.snippet = snippet;
        this.color = color;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }

    public String getSnippet() {
        return snippet;
    }

    public float getColor() {
        return color;
    }
}
