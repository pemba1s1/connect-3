package com.example.connect3.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.connect3.Model.GameStatus;
import com.example.connect3.Model.Grid;
import com.example.connect3.Model.Player;
import com.example.connect3.Model.PlayerType;

public class GameViewModel extends ViewModel {
    private Grid[] _gridInfo = new Grid[9];
    final private int[][] winConditions = {
            {0,1,2},
            {3,4,5},
            {6,7,8},
            {0,3,6},
            {1,4,7},
            {2,5,8},
            {2,4,6},
            {0,4,8}
    };
    private Player _player1;
    private Player _player2;
    final private MutableLiveData<Player> player1;
    final private MutableLiveData<Player> player2;
    final private MutableLiveData<PlayerType> playerTurn;
    final private MutableLiveData<String> gameStatusMessage;
    final private MutableLiveData<Grid[]> gridInfo;
    final MutableLiveData<Boolean> isPieceMoving;

    public LiveData<PlayerType> getPlayerTurn () {
        return playerTurn;
    }
    public LiveData<String> getWinner () {
        return gameStatusMessage;
    }
    public LiveData<Grid[]> getGridInfo () { return gridInfo; }
    public LiveData<Boolean> getIsPieceMoving () { return isPieceMoving; }
    public GameViewModel () {
        player1 = new MutableLiveData<Player>();
        player2 = new MutableLiveData<Player>();
        playerTurn = new MutableLiveData<PlayerType>();
        gameStatusMessage = new MutableLiveData<String>();
        isPieceMoving = new MutableLiveData<Boolean>();
        gridInfo = new MutableLiveData<Grid[]>();
        gridInfo.setValue(this._gridInfo);
        player1.setValue(this._player1);
        player2.setValue(this._player2);
        isPieceMoving.setValue(false);
        playerTurn.setValue(PlayerType.PLAYER_1);
        gameStatusMessage.setValue("");
    }

    public void setPlayer(PlayerType player, String name) {
        if (player == PlayerType.PLAYER_1) {
            this._player1 = new Player(name);
            player1.setValue(this._player1);
        } else {
            this._player2 = new Player(name);
            player2.setValue(this._player2);
        }
    }

    public void changePlayerTurn() {
        if (playerTurn.getValue() == PlayerType.PLAYER_1) {
            playerTurn.setValue(PlayerType.PLAYER_2);
        } else {
            playerTurn.setValue(PlayerType.PLAYER_1);
        }
    }

    public void setWinner(GameStatus status) {
        if (status == GameStatus.PLAYER_1_WINS) {
            gameStatusMessage.setValue(String.format("Congrats %s", this._player1.getName()));
        } else if (status == GameStatus.PLAYER_2_WINS) {
            gameStatusMessage.setValue(String.format("Congrats %s", this._player2.getName()));
        } else if (status == GameStatus.DRAW) {
            gameStatusMessage.setValue("It's a draw!");
        }
    }

    public void setIsPieceMoving (boolean isPieceMoving) {
        this.isPieceMoving.setValue(isPieceMoving);
    }

    public void putPieceInGrid (int gridIndex) {
        System.out.println(this._gridInfo.length);
        this._gridInfo[gridIndex] = new Grid(this.playerTurn.getValue());
        gridInfo.setValue(this._gridInfo);
        if (hasGameFinished()) {
            return;
        }
        this.changePlayerTurn();
    }

    public boolean hasGameFinished () {
        boolean gameFinished = true;
        for (int[] winCondition : winConditions) {
            Grid grid1 = _gridInfo[winCondition[0]];
            Grid grid2 = _gridInfo[winCondition[1]];
            Grid grid3 = _gridInfo[winCondition[2]];
            if (grid1 != null && grid2 != null && grid3 != null) {
                if (grid1.placedBy() == grid2.placedBy() && grid2.placedBy() == grid3.placedBy()) {
                    if (grid1.placedBy() == PlayerType.PLAYER_1) {
                        setWinner(GameStatus.PLAYER_1_WINS);
                    } else {
                        setWinner(GameStatus.PLAYER_2_WINS);
                    }
                    return true;
                }
            } else {
                gameFinished = false;
            }
        }
        if (gameFinished) setWinner(GameStatus.DRAW);
        return gameFinished;
    }

    public void playAgain () {
        playerTurn.setValue(PlayerType.PLAYER_1);
        this._gridInfo = new Grid[9];
        gridInfo.setValue(this._gridInfo);
        gameStatusMessage.setValue("");
    }
}
