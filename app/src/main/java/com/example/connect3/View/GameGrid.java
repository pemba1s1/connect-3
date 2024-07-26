package com.example.connect3.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.connect3.Model.Grid;
import com.example.connect3.Model.PlayerType;
import com.example.connect3.R;
import com.example.connect3.ViewModel.GameViewModel;
import com.example.connect3.databinding.FragmentGameGridBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameGrid} factory method to
 * create an instance of this fragment.
 */
public class GameGrid extends Fragment {
    private int gridIndex;
    FragmentGameGridBinding binding;
    GameViewModel viewModel;
    public void SetGridIndex (int index) {
        this.gridIndex = index;
    }
    public void setImageResource (PlayerType playerType) {
        if (playerType == PlayerType.PLAYER_1) {
            binding.gridFragment.setImageResource(R.drawable.red);
        } else {
            binding.gridFragment.setImageResource(R.drawable.yellow);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameGridBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);

        viewModel.getGridInfo().observe(getViewLifecycleOwner(), gridInfo -> {
            Grid grid = Objects.requireNonNull(viewModel.getGridInfo().getValue())[gridIndex];
            if (grid == null) {
                binding.gridFragment.setImageDrawable(null);
                return;
            }
            setImageResource(grid.placedBy());
        });

        binding.gridFragment.setOnClickListener(v -> {
            if (Boolean.TRUE.equals(viewModel.getIsPieceMoving().getValue())) return;
            if (!Objects.requireNonNull(viewModel.getWinner().getValue()).isEmpty()) return;
            if (Objects.requireNonNull(viewModel.getGridInfo().getValue())[gridIndex] != null) return;
            viewModel.setIsPieceMoving(true);
            setImageResource(viewModel.getPlayerTurn().getValue());
            binding.gridFragment.setTranslationY(-1700);
            binding.gridFragment.animate().translationY(0).setDuration(1000).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewModel.putPieceInGrid(gridIndex);
                    viewModel.setIsPieceMoving(false);
                }
            });
        });
        return binding.getRoot();
    }
}