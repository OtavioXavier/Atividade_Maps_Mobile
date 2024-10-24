package com.example.atividademapa;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IAtualizarLocalizacao {
    Button link;
    TextView latitude;
    TextView longitude;
    Localizacao localizacao;
    ProgressBar progressLatitude;
    ProgressBar progressLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        link = findViewById(R.id.link_mapa);
        progressLatitude = findViewById(R.id.progress_latitude);
        progressLongitude = findViewById(R.id.progress_longitude);
        localizacao = new Localizacao(this, this);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapaActivity.class);
                startActivity(intent);
//                finish();
            }
        });

    }

    @Override
    public void atualizarLocalizacao(Location location) {
        progressLongitude.setVisibility(View.GONE);
        progressLatitude.setVisibility(View.GONE);
        latitude.setText("Latitude: " + Math.round(location.getLatitude()));
        longitude.setText("Longitude: " + Math.round(location.getLongitude()));
    }
}