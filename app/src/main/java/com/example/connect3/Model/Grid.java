package com.example.connect3.Model;

public class Grid {
    private final boolean hasPiece;
    private int gridIndex;
    private PlayerType placedBy;
    public Grid (PlayerType playerType) {
        this.hasPiece = true;
        this.placedBy = playerType;
    }
    public void setPlacedBy (PlayerType player) {
        this.placedBy = player;
    }
    public boolean hasPiece () {
        return this.hasPiece;
    }
    public PlayerType placedBy () {
        return this.placedBy;
    }
    public void setGridIndex (int index) {
        this.gridIndex = index;
    }
    public int gridIndex () {
        return this.gridIndex;
    }
}
