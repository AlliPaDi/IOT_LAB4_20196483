package com.example.telefutbol.services;


import com.example.telefutbol.model.LigasResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SportsAPI {

    // Para obtener todas las ligas
    @GET("/api/v1/json/3/all_leagues.php")
    Call<LigasResponse> getLigas();

    // Para obtener ligas por país
    @GET("/api/v1/json/3/search_all_leagues.php")
    Call<LigasResponse> getLigasPorPais(@Query("c") String country);
}
