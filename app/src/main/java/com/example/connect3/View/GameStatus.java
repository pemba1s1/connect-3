package com.example.connect3.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.connect3.ViewModel.GameViewModel;
import com.example.connect3.databinding.FragmentGameStatusBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameStatus} factory method to
 * create an instance of this fragment.
 */
public class GameStatus extends Fragment {
    FragmentGameStatusBinding binding;
    GameViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGameStatusBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        viewModel.getWinner().observe(getViewLifecycleOwner(), gameStatus -> binding.gameStatusMessage.setText(gameStatus));

        binding.playAgain.setOnClickListener(v -> viewModel.playAgain());
        binding.quitGame.setOnClickListener(v -> requireActivity().finish());

        return binding.getRoot();
    }
}