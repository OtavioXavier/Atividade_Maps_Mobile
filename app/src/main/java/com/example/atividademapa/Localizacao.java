package com.example.atividademapa;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

public class Localizacao {
    private LocationManager locationManager;
    private LocationProvider locationProvider;
    private static final int REQUEST_LOCATION = 1;
    private IAtualizarLocalizacao activity;

    public Localizacao(Context context, IAtualizarLocalizacao activity) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        obtemPermissao(context);
        this.activity = activity;
    }

    private void obtemPermissao(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            locationProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
            iniciarAtualizacoes(context);
        } else {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);
        }
    }


    public void resultadoPedidoPermissao(int requestCode, String[] permissions, int[] grantResults, Context context) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                obtemPermissao(context);
            } else {
                Toast.makeText(context, "Sem permissão", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private void iniciarAtualizacoes(Context context) {
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(locationProvider.getName(), 1000, 0.1f,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(@NonNull Location location) {
                        activity.atualizarLocalizacao(location);
                    }
                });
    }

}
