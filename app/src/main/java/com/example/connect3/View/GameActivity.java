package com.example.connect3.View;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.connect3.Model.PlayerType;
import com.example.connect3.R;
import com.example.connect3.ViewModel.GameViewModel;
import com.example.connect3.databinding.AcitvityGameBinding;

public class GameActivity extends AppCompatActivity {
    private AcitvityGameBinding binding;
    GameViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AcitvityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        Intent intObj = getIntent();
        viewModel.setPlayer(PlayerType.PLAYER_1, intObj.getStringExtra("player1"));
        viewModel.setPlayer(PlayerType.PLAYER_2, intObj.getStringExtra("player2"));

        for (int i = 8; i >= 0; i--) {
            FragmentContainerView fragmentLayout = (FragmentContainerView) binding.gameBoard.getChildAt(i);
            GameGrid grid = new GameGrid();
            grid.SetGridIndex(i);
            getSupportFragmentManager().beginTransaction()
                    .replace(fragmentLayout.getId(), grid)
                    .commit();
        }
        viewModel.getWinner().observe(this, gameStatus -> {
            if (gameStatus.isEmpty()) {
                RemoveGameStatusFragment();
            } else {
                CreateGameStatusBoard();
            }
        });
    }
    private void CreateGameStatusBoard () {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.gameStatusFragmentContainer, new GameStatus())
                .commit();
    }

    private void RemoveGameStatusFragment () {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.gameStatusFragmentContainer);
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }
}