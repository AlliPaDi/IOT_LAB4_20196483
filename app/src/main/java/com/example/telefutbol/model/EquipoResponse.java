package com.example.telefutbol.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class EquipoResponse {
    @SerializedName("table")
    private List<Equipo> table;

    public List<Equipo> getTable() {
        return table;
    }

    public void setTable(List<Equipo> table) {
        this.table = table;
    }
}