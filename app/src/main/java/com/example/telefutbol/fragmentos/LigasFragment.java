package com.example.telefutbol.fragmentos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.telefutbol.R;
import com.example.telefutbol.adapter.LigasAdapter;
import com.example.telefutbol.databinding.FragmentLigasBinding;
import com.example.telefutbol.model.Liga;
import com.example.telefutbol.model.LigasResponse;
import com.example.telefutbol.services.SportsAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LigasFragment extends Fragment {

    FragmentLigasBinding binding;
    private LigasAdapter ligasAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLigasBinding.inflate(inflater,container,false);

        binding.rvLigas.setLayoutManager(new LinearLayoutManager(getContext()));
        ligasAdapter = new LigasAdapter();
        binding.rvLigas.setAdapter(ligasAdapter);

        // Inicializar Retrofit
        SportsAPI sportsAPI = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SportsAPI.class);

        // Acciona al presionar buscar
        binding.btnBuscar.setOnClickListener(v ->{
            String country = binding.etPais.getText().toString().trim();
            if (country.isEmpty()){
                listarTodasLigas(sportsAPI);
            } else {
                listarLigasPorPais(sportsAPI, country);
            }
        });

        return binding.getRoot();
    }


    private void listarTodasLigas(SportsAPI sportsAPI) {
        Call<LigasResponse> call = sportsAPI.getLigas();
        call.enqueue(new Callback<LigasResponse>() {
            @Override
            public void onResponse(Call<LigasResponse> call, Response<LigasResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Liga> ligasList = response.body().getLeagues();
                    ligasAdapter.setLigasList(ligasList);
                } else {
                    Log.e("API Error", "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(Call<LigasResponse> call, Throwable t) {
                Log.e("API Error", "Error en la solicitud de la API", t);
            }
        });
    }

    private void listarLigasPorPais(SportsAPI sportsAPI, String country){
        Call<LigasResponse> call = sportsAPI.getLigasPorPais(country); // Llamada para ligas por país
        call.enqueue(new Callback<LigasResponse>() {
            @Override
            public void onResponse(Call<LigasResponse> call, Response<LigasResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Liga> ligasList = response.body().getLeagues();
                    ligasAdapter.setLigasList(ligasList);
                } else {
                    Toast.makeText(getContext(), "No se encontraron ligas para este país", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LigasResponse> call, Throwable t) {
                Log.e("API Error", "Error en la solicitud de la API", t);
            }
        });
    }
}