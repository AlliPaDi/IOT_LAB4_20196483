package com.example.telefutbol.fragmentos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.telefutbol.R;
import com.example.telefutbol.databinding.FragmentResultadosBinding;

public class ResultadosFragment extends Fragment {


    FragmentResultadosBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResultadosBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
}