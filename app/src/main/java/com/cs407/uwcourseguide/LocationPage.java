package com.cs407.uwcourseguide;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationPage extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;
    private LatLng myLocation; // You need to set this variable appropriately

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_page, container, false);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng state1LatLng = getLocationFromAddress("Birge Hall" + " Madison, WI");
        LatLng state2LatLng = getLocationFromAddress("Sterling Hall" + " Madison, WI");
        LatLng state3LatLng = getLocationFromAddress("Van Vleck Hall" + " Madison, WI");

        // Set initial camera position and zoom level
        if (state1LatLng != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(state1LatLng, 14)); // Adjust the zoom level as needed
        }

        // Create a list of custom markers
        List<CustomMarker> customMarkers = new ArrayList<>();
        customMarkers.add(new CustomMarker(state1LatLng, "CS 540: Introduction to Artificial Intelligence", "Location: 145 Birge Hall\nTime: 13:00 - 14:15", BitmapDescriptorFactory.HUE_RED));
        customMarkers.add(new CustomMarker(state2LatLng, "CS 640: Introduction to Computer Networks", "Location: 145 Sterling Hall\nTime: 13:00 - 14:15", BitmapDescriptorFactory.HUE_GREEN));
        customMarkers.add(new CustomMarker(state3LatLng, "CS 407: Foundations of Mobile Systems and Applications", "Location: 145 Bascom Hall\nTime: 13:00 - 14:15", BitmapDescriptorFactory.HUE_BLUE));

        // Pass the LayoutInflater to the CustomInfoWindowAdapter
        CustomInfoWindowAdapter infoWindowAdapter = new CustomInfoWindowAdapter(LayoutInflater.from(requireContext()));

        // Set custom info window adapter
        googleMap.setInfoWindowAdapter(infoWindowAdapter);

        // Add custom markers to the map
        for (CustomMarker marker : customMarkers) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(marker.getPosition())
                    .title(marker.getTitle())
                    .snippet(marker.getSnippet())
                    .icon(BitmapDescriptorFactory.defaultMarker(marker.getColor()));
            googleMap.addMarker(markerOptions);
        }
        //new DrawRouteTask(customMarkers).execute(myLocation);
        displayMyLocation();
        //drawRouteFromCurrentLocationToDestinations(customMarkers);
    }

//    private void drawRouteFromCurrentLocationToDestinations(List<CustomMarker> destinations) {
//        if (myLocation != null && destinations != null && !destinations.isEmpty()) {
//            for (CustomMarker destination : destinations) {
//                // Fetch the route using Directions API
//                new DrawRouteTask(destinations).execute(myLocation, destination.getPosition());
//            }
//        }
//    }
//
//    private class DrawRouteTask extends AsyncTask<Object, Void, DirectionsResult> {
//
//        private List<CustomMarker> destinations;
//
//        public DrawRouteTask(List<CustomMarker> destinations) {
//            this.destinations = destinations;
//        }
//        @Override
//        protected DirectionsResult doInBackground(Object... params) {
//            LatLng myLocation = (LatLng) params[0];
//            LatLng destination = (LatLng) params[1];
//
//            try {
//                GeoApiContext context = new GeoApiContext.Builder().apiKey(getString(R.string.google_maps_key)).build();
//                return DirectionsApi.newRequest(context)
//                        .origin(new com.google.maps.model.LatLng(myLocation.latitude, myLocation.longitude))
//                        .destination(new com.google.maps.model.LatLng(destination.latitude, destination.longitude))
//                        .mode(TravelMode.DRIVING)
//                        .units(Unit.METRIC)
//                        .await();
//            } catch (Exception e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(DirectionsResult result) {
//            if (result != null && result.routes != null && result.routes.length > 0) {
//                List<LatLng> points = new ArrayList<>();
//                for (com.google.maps.model.LatLng latLng : result.routes[0].overviewPolyline.decodePath()) {
//                    points.add(new LatLng(latLng.lat, latLng.lng));
//                }
//
//                // Draw the route on the map
//                mMap.addPolyline(new PolylineOptions().addAll(points).color(Color.BLUE));
//
//                // Adjust camera to fit all markers and route
//                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                builder.include(myLocation);
//                for (CustomMarker destination : destinations) {
//                    builder.include(destination.getPosition());
//                }
//                LatLngBounds bounds = builder.build();
//                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
//            }
//        }
//    }

    private LatLng getLocationFromAddress(String address) {
        Geocoder geocoder = new Geocoder(requireContext());
        List<Address> addresses;
        LatLng latLng = null;

        try {
            addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                android.location.Address location = addresses.get(0);
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLng;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayMyLocation();
            }
        }
    }

    private void displayMyLocation() {
        int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        } else {
            mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(requireActivity(), task -> {
                Location mLastKnownLocation = task.getResult();
                if (task.isSuccessful() && mLastKnownLocation != null) {
                    // Handle the obtained location as needed
                    myLocation = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                }
            });
        }
    }
}
