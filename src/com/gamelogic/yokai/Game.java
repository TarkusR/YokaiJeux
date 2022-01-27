package com.gamelogic.yokai;

import Main.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    /*Niveau de la partie*/
    public int level;

    /* Nombre de joueur */
    public int playerCount;

    public Player player;

    /* Type de partie (en ligne ou local) */
    public String gameType;

    public Board board;


    /* Getter et Setter */
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }


    /* Constructeur */
    public Game(int level, int playerCount, String gameType, Player player, GameManager gm) {
        this.level = level;
        this.playerCount = playerCount;
        this.gameType = gameType;
        this.player = player;
        this.board = new Board(playerCount,gm);

    }

    public void endgame() {

    }

    /* Méthodes */

}
