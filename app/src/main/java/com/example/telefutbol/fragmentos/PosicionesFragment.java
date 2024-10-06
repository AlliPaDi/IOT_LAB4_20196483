package com.example.telefutbol.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.telefutbol.R;
import com.example.telefutbol.adapter.PosicionesAdapter;
import com.example.telefutbol.databinding.FragmentPosicionesBinding;
import com.example.telefutbol.model.Equipo;
import com.example.telefutbol.model.EquipoResponse;
import com.example.telefutbol.services.SportsAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PosicionesFragment extends Fragment {

    FragmentPosicionesBinding binding;
    private PosicionesAdapter posicionesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPosicionesBinding.inflate(inflater, container, false);

        binding.rvPosiciones.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar Retrofit
        SportsAPI sportsAPI = new Retrofit.Builder()
                .baseUrl("https://www.thesportsdb.com/api/v1/json/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SportsAPI.class);

        // Acciona al presionar buscar
        binding.btnBuscar.setOnClickListener(v ->{
            String ligaId = binding.etLigaID.getText().toString().trim();
            String temporada = binding.etTemporada.getText().toString().trim();
            if (!ligaId.isEmpty() && !temporada.isEmpty()){
                obtenerPosiciones(sportsAPI, ligaId, temporada);
            } else {
                // Mostrar error si uno o ambos campos están vacíos
                if (ligaId.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, ingrese el ID de la liga", Toast.LENGTH_SHORT).show();
                }
                if (temporada.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, ingrese la temporada", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return binding.getRoot();
    }

    private void obtenerPosiciones(SportsAPI sportsAPI, String ligaId, String temporada) {
        Call<EquipoResponse> call = sportsAPI.getPosicionesLiga(ligaId, temporada);
        call.enqueue(new Callback<EquipoResponse>() {
            @Override
            public void onResponse(Call<EquipoResponse> call, Response<EquipoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Equipo> equipos = response.body().getTable();

                    if (equipos != null && !equipos.isEmpty()) {
                        // Inicializar el adaptador con la nueva lista de equipos
                        posicionesAdapter = new PosicionesAdapter(equipos, getContext());
                        binding.rvPosiciones.setAdapter(posicionesAdapter); // Establece el nuevo adaptador

                        // Notificar al adaptador que los datos han cambiado
                        posicionesAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No se encontraron resultados para la liga y la temporada proporcionadas.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Error al cargar las posiciones", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EquipoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error en la solicitud: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}