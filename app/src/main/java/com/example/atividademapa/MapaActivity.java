package com.example.atividademapa;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.atividademapa.databinding.ActivityMapaBinding;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback, IAtualizarLocalizacao {

    private GoogleMap mMap;
    private ActivityMapaBinding binding;
    private Localizacao localizacao;
    private LatLng minhaLocalizacao = new LatLng(0,0);
    private Marker marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        localizacao = new Localizacao(this, this);

        binding = ActivityMapaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        minhaLocalizacao = minhaLocalizacao.equals(new LatLng(0,0)) ? new LatLng(-34, 151) : minhaLocalizacao;
        marcador = mMap.addMarker(new MarkerOptions().position(minhaLocalizacao).title("Minha Localização"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, 15));
    }

    @Override
    public void atualizarLocalizacao(Location location) {
        minhaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());

        if (mMap != null) {
            marcador.setPosition(minhaLocalizacao);
            float zoomAtual = mMap.getCameraPosition().zoom;

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, zoomAtual), 1000, null);

        }
    }
}