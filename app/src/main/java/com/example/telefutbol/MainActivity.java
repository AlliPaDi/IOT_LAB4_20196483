package com.example.telefutbol;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.provider.Settings;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import com.example.telefutbol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // botones
        binding.btnIngresar.setOnClickListener(v -> {

            if(isConnected()){
                Intent intent = new Intent(this, AppActivity.class);
                startActivity(intent);
            } else {
                showNoConnectedDialog();
            }
        });
    }


    // validar conexión a internet
    public boolean isConnected(){

        // verificamos el estado de conectividad (si tenemos o no internet)
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            // obtenemos info específica sobre la conexión de red activa
            NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
            if (capabilities != null) {
                return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
            }
        }
        return false;
    }

    // configuración del Dialog
    private void showNoConnectedDialog(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("Sin conexión a Internet")
                .setMessage("Para acceder, por favor activa tu red Wifi o datos móviles.")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Configuración", (dialog, which) -> {
                    // Ir a configuración
                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                })
                .setCancelable(false)
                .show();
    }

    // Pausar y reanudar viewFlipper
    @Override
    protected void onPause() {
        super.onPause();
        binding.viewFlipper.stopFlipping();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.viewFlipper.startFlipping();
    }

}