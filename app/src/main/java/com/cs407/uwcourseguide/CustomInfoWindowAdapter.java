package com.cs407.uwcourseguide;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private final LayoutInflater inflater;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null; // Use default info window background
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = inflater.inflate(R.layout.custom_info_window, null);

        TextView title = v.findViewById(R.id.info_title);
        TextView snippet = v.findViewById(R.id.info_snippet);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return v;
    }
}
