package com.softuni.hotornot;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.softuni.hotornot.api.Api;
import com.softuni.hotornot.api.models.CurrentWeather;
import com.softuni.hotornot.api.models.DailyForecast;
import com.softuni.hotornot.api.models.HourlyForecast;
import com.softuni.hotornot.api.models.helper_models.Forecast;
import com.softuni.hotornot.databinding.FragmentActiveWeatherBinding;
import com.softuni.hotornot.databinding.FragmentDetailWeatherBinding;
import com.softuni.hotornot.databinding.IncludeWeatherCardBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailWeatherFragment extends Fragment {

    private static final int MY_PERMISSIONS_REQUEST_GPS = 4;
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    private FragmentDetailWeatherBinding binding;
    private FusedLocationProviderClient mFusedLocationClient;
    private Location location;

    private OnFragmentInteractionListener mListener;

    public DetailWeatherFragment() {
        // Required empty public constructor
    }

    public static DetailWeatherFragment newInstance() {
        DetailWeatherFragment fragment = new DetailWeatherFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail_weather, container, false);
        setupViews();
        return binding.getRoot();
    }

    private void setupViews() {
        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshSelected();
            }
        });

        binding.recView.setLayoutManager(new LinearLayoutManager(getContext()));

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_GPS);
        } else {
            getLocationAndRefresh();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocationAndRefresh() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            DetailWeatherFragment.this.location = location;
                            onRefreshSelected();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_GPS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocationAndRefresh();
                }
                return;
            }
        }
    }

    private void onRefreshSelected() {
        updateData();
    }

    private void updateData() {
        if(location != null) {
            Api.getInstance().getHourlyForecast(location.getLatitude(), location.getLongitude(), new Api.DataListener<HourlyForecast>() {

                @Override
                public void onSuccess(HourlyForecast data) {
                    updateHourlyWeather(data);
                    binding.swiperefresh.setRefreshing(false);
                }

                @Override
                public void onError() {
                    binding.swiperefresh.setRefreshing(false);
                    Toast.makeText(getContext(), "Error while updating hourly weather", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void updateHourlyWeather(HourlyForecast data) {
        binding.recView.setAdapter(new ForecastAdapter(data));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
