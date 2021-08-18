package com.example.withearth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;

import com.google.android.gms.maps.OnMapReadyCallback;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        fragmentManager = getFragmentManager();
        mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.googleMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng location = new LatLng(37.591320, 127.022120); // 성신여대 위치
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title("윗어스 본점");
        markerOptions.snippet("친환경, 비건 제품 판매");
        markerOptions.position(location);
        googleMap.addMarker(markerOptions);

        LatLng location1 = new LatLng(37.5881261124091, 127.00742051145853); // 굿데이서울 위치
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.title("굿데이서울");
        markerOptions1.snippet("콩두유크림머핀(3000원)\n");
        markerOptions1.position(location1);
        googleMap.addMarker(markerOptions1);

        LatLng location2 = new LatLng(37.59092627712847, 127.02049273986533); // 투고샐러드 위치
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.title("투고샐러드");
        markerOptions2.snippet("비건 샌드위치(10,000원)\n");
        markerOptions2.position(location2);
        googleMap.addMarker(markerOptions2);

        LatLng location3 = new LatLng(37.591711215146, 127.01681918262305); // 카레경반 위치
        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.title("카레경반");
        markerOptions3.snippet("버섯 카레(9000원)\n");
        markerOptions3.position(location3);
        googleMap.addMarker(markerOptions3);

        LatLng location4 = new LatLng(37.59348121431514, 127.01752710284978); // 샐러디 위치
        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.title("샐러디");
        markerOptions4.snippet("커스터마이징 샐러드\n");
        markerOptions4.position(location4);
        googleMap.addMarker(markerOptions4);

        LatLng location5 = new LatLng(37.591435565544636, 127.02016196809318); // 크레센트 위치
        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.title("카페 크레센트");
        markerOptions5.snippet("레몬파운드(3000원)\n");
        markerOptions5.position(location5);
        googleMap.addMarker(markerOptions5);


        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17)); // 더 확대: 숫자 올리기

    }

}