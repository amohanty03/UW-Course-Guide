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
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocationPage extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 12;
    private LatLng myLocation; // You need to set this variable appropriately

    private ScheduleViewModel scheduleViewModel;

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


        // Initialize Room Database and ViewModel
        AppDatabase db = Room.databaseBuilder(getContext().getApplicationContext(),
                AppDatabase.class, "schedule-database").build();
        ScheduleDao scheduleDao = db.scheduleDao();
        ScheduleViewModelFactory viewModelFactory = new ScheduleViewModelFactory(scheduleDao);
        scheduleViewModel = new ViewModelProvider(this, viewModelFactory).get(ScheduleViewModel.class);

        // Fetch and handle schedule data
        scheduleViewModel.getSchedules().observe(getViewLifecycleOwner(), this::handleScheduleData);

        return view;
    }


    private void handleScheduleData(List<ScheduleEntity> schedules) {
        for (ScheduleEntity schedule : schedules) {
            String locationName = schedule.getLocation() + " Madison, WI";
            LatLng locationLatLng = getLocationFromAddress(locationName);

            if (locationLatLng != null) {
                String title = schedule.getClassName();
                String snippet = "Room: " + schedule.getRoomNumber() + " " + schedule.getLocation()
                        + "\nTime: " + schedule.getTime() + "\nDays: " + schedule.getDays();

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(locationLatLng)
                        .title(title)
                        .snippet(snippet);

                mMap.addMarker(markerOptions);
            }
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Initialize default camera position if needed
        // For example, centering on a default location like Madison, WI
        LatLng defaultLocation = new LatLng(43.0731, -89.4012); // Latitude and longitude of Madison, WI
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 14)); // Adjust the zoom level as needed

        // Pass the LayoutInflater to the CustomInfoWindowAdapter
        CustomInfoWindowAdapter infoWindowAdapter = new CustomInfoWindowAdapter(LayoutInflater.from(requireContext()));

        // Set custom info window adapter
        googleMap.setInfoWindowAdapter(infoWindowAdapter);

        // The dynamic markers will be added in handleScheduleData method
        // when the schedule data is observed from the ViewModel

        displayMyLocation();
    }

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
