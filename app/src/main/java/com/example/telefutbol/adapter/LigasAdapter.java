package com.example.telefutbol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.telefutbol.R;
import com.example.telefutbol.databinding.ItemLigaBinding;
import com.example.telefutbol.model.Liga;

import java.util.List;

public class LigasAdapter extends RecyclerView.Adapter<LigasAdapter.LigasViewHolder> {

    private List<Liga> ligasList;

    public void setLigasList(List<Liga> ligasList) {
        this.ligasList = ligasList;
        notifyDataSetChanged(); // Notificar cambios al RecyclerView
    }

    @NonNull
    @Override
    public LigasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        ItemLigaBinding binding = ItemLigaBinding.inflate(inflater, parent, false);
        return new LigasViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull LigasViewHolder holder, int position) {
        Liga liga = ligasList.get(position);
        holder.binding.tvLigaID.setText(liga.getIdLeague());
        holder.binding.tvLigaName.setText(liga.getStrLeague());

        // Dividir el campo strLeagueAlternate
        if (liga.getStrLeagueAlternate() != null) {
            String[] alternates = liga.getStrLeagueAlternate().split(",");

            // Asignar el primer nombre alternativo, si existe
            if (alternates.length > 0) {
                holder.binding.tvLigaNameAlt1.setText(alternates[0].trim());
            }

            // Asignar el segundo nombre alternativo, si existe
            if (alternates.length > 1) {
                holder.binding.tvLigaNameAlt2.setText(alternates[1].trim());
            } else {
                holder.binding.tvLigaNameAlt2.setText("N/A");
            }
        } else {
            // En caso de que strLeagueAlternate sea null
            holder.binding.tvLigaNameAlt1.setText("N/A");
            holder.binding.tvLigaNameAlt2.setText("N/A");
        }

    }

    @Override
    public int getItemCount() {
        return ligasList != null ? ligasList.size() : 0;
    }

    public static class LigasViewHolder extends RecyclerView.ViewHolder {

        ItemLigaBinding binding;

        public LigasViewHolder(ItemLigaBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}