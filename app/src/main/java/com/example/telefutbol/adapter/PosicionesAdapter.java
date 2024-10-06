package com.example.telefutbol.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

import com.example.telefutbol.databinding.ItemPosicionBinding;
import com.example.telefutbol.model.Equipo;

import java.util.List;

public class PosicionesAdapter extends RecyclerView.Adapter<PosicionesAdapter.PosicionViewHolder>  {

    private List<Equipo> equipos;

    private Context context;

    // Constructor para pasar la lista de equipos
    public PosicionesAdapter(List<Equipo> equipos, Context context) {
        this.equipos = equipos;
        this.context = context;
    }

    @NonNull
    @Override
    public PosicionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemPosicionBinding binding = ItemPosicionBinding.inflate(inflater, parent, false);
        return new PosicionViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PosicionViewHolder holder, int position) {
        Equipo equipo = equipos.get(position);
        holder.binding.tvRanking.setText(String.valueOf(equipo.getIntRank()));
        holder.binding.tvTeamName.setText(equipo.getStrTeam());
        holder.binding.tvRecord.setText(equipo.getRecord());
        holder.binding.tvGoals.setText(equipo.getGoalStats());

        // Cargar la imagen del badge (usando Glide)
        Glide.with(holder.binding.getRoot().getContext())
                .load(equipo.getStrBadge())
                .into(holder.binding.ivTeamBadge);
    }

    @Override
    public int getItemCount() {
        return equipos.size();
    }

    public static class PosicionViewHolder extends RecyclerView.ViewHolder {
        ItemPosicionBinding binding;

        public PosicionViewHolder(ItemPosicionBinding binding) {

            super(binding.getRoot());
            this.binding = binding;

        }

    }
}
