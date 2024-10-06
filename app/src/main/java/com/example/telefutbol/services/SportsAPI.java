package com.example.telefutbol.services;


import com.example.telefutbol.model.LigasCountry;
import com.example.telefutbol.model.LigasResponse;
import com.example.telefutbol.model.EquipoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsAPI {

    // Para obtener todas las ligas
    @GET("all_leagues.php")
    Call<LigasResponse> getLigas();

    // Para obtener ligas por país
    @GET("search_all_leagues.php")
    Call<LigasCountry> getLigasPorPais(@Query("c") String country);

    @GET("lookuptable.php")
    Call<EquipoResponse> getPosicionesLiga(@Query("l") String idLiga, @Query("s") String temporada);

}
