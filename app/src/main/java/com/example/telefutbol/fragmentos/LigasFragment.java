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
import com.example.telefutbol.model.LigasCountry;
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
        Call<LigasCountry> call = sportsAPI.getLigasPorPais(country); // Llamada para ligas por país
        call.enqueue(new Callback<LigasCountry>() {
            @Override
            public void onResponse(Call<LigasCountry> call, Response<LigasCountry> response) {
                if (response.isSuccessful()) {
                    LigasCountry ligasResponse = response.body();
                    if (ligasResponse != null && ligasResponse.getCountries() != null && !ligasResponse.getCountries().isEmpty()) {
                        List<Liga> ligasList = ligasResponse.getCountries();
                        ligasAdapter.setLigasList(ligasList);
                        ligasAdapter.notifyDataSetChanged();
                        Log.d("API Success", "Se encontraron " + ligasList.size() + " ligas para " + country);
                    } else {
                        // No se encontraron ligas o la lista está vacía
                        Toast.makeText(getContext(), "No se encontraron ligas para este país", Toast.LENGTH_SHORT).show();
                        ligasAdapter.setLigasList(new ArrayList<>()); // Limpiar el RecyclerView
                        Log.d("API Result", "No se encontraron ligas para " + country);
                    }
                } else {
                    // Error en la respuesta de la API
                    Toast.makeText(getContext(), "Error en la respuesta de la API", Toast.LENGTH_SHORT).show();
                    Log.e("API Error", "Error en la respuesta de la API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<LigasCountry> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la solicitud de la API", Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Error en la solicitud de la API", t);
            }
        });
    }

}