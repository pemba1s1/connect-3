package com.example.connect3.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.connect3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.playGame.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            String player1 = String.valueOf(binding.player1.getText());
            String player2 = String.valueOf(binding.player2.getText());
            boolean inputError = false;

            if (player1.isEmpty()) {
                binding.player1.setError("This field cannot be empty");
                binding.player1Error.setVisibility(View.VISIBLE);
                inputError = true;
            } else {
                binding.player1.setError(null);
                binding.player1Error.setVisibility(View.GONE);
            }
            if (player2.isEmpty()) {
                binding.player2.setError("This field cannot be empty");
                binding.player2Error.setVisibility(View.VISIBLE);
                inputError = true;
            } else {
                binding.player2.setError(null);
                binding.player2Error.setVisibility(View.GONE);
            }

            if (inputError) return;

            intent.putExtra("player1", player1);
            intent.putExtra("player2", player2);
            startActivity(intent);
        });
    }

}