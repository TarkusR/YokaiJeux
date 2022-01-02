package com.gamelogic.yokai;

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

    private Board board;


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
    public Game(int level, int playerCount, String gameType,Player player) {
        this.level = level;
        this.playerCount = playerCount;
        this.gameType = gameType;
        this.player = player;
        board = new Board(playerCount);
        play();
    }

    public void endgame() {

    }

    /* Méthodes */
    public void play() {
        int i = 0; //compteur
        boolean nomoreclue = false;
        boolean espritsapaises = false;
        while (nomoreclue == false) {

            //Condition sur le nombre d'indice restant
            if(board.deck.isEmpty()==true || board.preparedDeck.isEmpty()==true){
                nomoreclue=true;
            }
            //ajouter une condition sur le compteur i + determiner le joueur qui joue


            if(nomoreclue != true){
                Scanner scanner = new Scanner(System.in);
                System.out.println("1- Les esprits sont apaisés\n2- Les esprits ne sont pas apaisés");
                int choix = scanner.nextInt();
                switch (choix) {
                    case 1:
                        endgame();
                        break;
                    case 2:
                        tour();
                        i += 1;
                        break;
                }
            }
        }
    }

    public void tour() {
        //1- Retourner 2 cartes
        Scanner scanner1 = new Scanner(System.in);

        System.out.println("Rentrer X1 ");
        int X1 = scanner1.nextInt();
        System.out.println("Rentrer Y1 ");
        int Y1 = scanner1.nextInt();
        Position positionCarte1 = new Position(X1, Y1);
        System.out.println("Rentrer X2 ");
        int X2 = scanner1.nextInt();
        System.out.println("Rentrer Y2 ");
        int Y2 = scanner1.nextInt();
        Position positionCarte2 = new Position(X2, Y2);
        board.getCard(positionCarte1);
        while (board.getCard(positionCarte1) == Board.nullCard) {
            System.out.println("position invalide");
            System.out.println("Rentrer X1 ");
            X1 = scanner1.nextInt();
            System.out.println("Rentrer Y1 ");
            Y1 = scanner1.nextInt();
            positionCarte1 = new Position(X1, Y1);
        }
        while (board.getCard(positionCarte2) == Board.nullCard) {
            System.out.println("position invalide");
            System.out.println("Rentrer X2 ");
            X2 = scanner1.nextInt();
            System.out.println("Rentrer Y2 ");
            Y2 = scanner1.nextInt();
            positionCarte2 = new Position(X2, Y2);

        }
        System.out.println("La première carte =" + board.getCard(positionCarte1));
        System.out.println("La deuxième carte =" + board.getCard(positionCarte2));


        //2- Deplacer une carte
        System.out.println("Rentrer X3 ");
        int X3 = scanner1.nextInt();
        System.out.println("Rentrer Y3 ");
        int Y3 = scanner1.nextInt();
        Position positionCarte3 = new Position(X3, Y3);
        System.out.println("Rentrer X4 ");
        int X4 = scanner1.nextInt();
        System.out.println("Rentrer Y4 ");
        int Y4 = scanner1.nextInt();
        Position positionCarte4 = new Position(X4, Y4);
        while (board.getCard(positionCarte3) == Board.nullCard) {
            System.out.println("position invalide");
            System.out.println("Rentrer X3 ");
            X3 = scanner1.nextInt();
            System.out.println("Rentrer Y3 ");
            Y3 = scanner1.nextInt();
            positionCarte3 = new Position(X3, Y3);

        }
        while (board.getCard(positionCarte4) != Board.nullCard) {
            System.out.println("position invalide");
            System.out.println("Rentrer X4 ");
            X4 = scanner1.nextInt();
            System.out.println("Rentrer Y4 ");
            Y4 = scanner1.nextInt();
            positionCarte4 = new Position(X4, Y4);

        }
        board.moveYokai(positionCarte3, positionCarte4);
        //3- Retourner ou poser un indice
        if (board.deck.size() == 0 || board.preparedDeck.size() == 0) {
            if (board.deck.size() == 0) {
                System.out.println("quelle indice souhaitez vous placer");
                int choixIndice = scanner1.nextInt();
                while (choixIndice >= 0 && choixIndice < board.preparedDeck.size()) {
                    System.out.println("Veuiller rentrer un indice compris entre 0 et " + (board.preparedDeck.size() - 1));
                    choixIndice = scanner1.nextInt();
                }
                System.out.println("Où souhaitez vous placer la carte indice");
                System.out.println("Rentrer X5 ");
                int X5 = scanner1.nextInt();
                System.out.println("Rentrer Y5 ");
                int Y5 = scanner1.nextInt();
                Position positionCarte5 = new Position(X5, Y5);
                while (board.getCard(positionCarte5) != Board.nullCard) {
                    System.out.println("position invalide");
                    System.out.println("Rentrer X5 ");
                    X5 = scanner1.nextInt();
                    System.out.println("Rentrer Y5 ");
                    Y5 = scanner1.nextInt();
                    positionCarte5 = new Position(X5, Y5);

                    board.placeClue(board.preparedDeck.get(choixIndice), positionCarte5);
                }
            } else {
                board.prepareClue();
            }
        } else {
            System.out.println("1- Retourner un indice\n2- Déposer un indice");
            int choix1 = scanner1.nextInt();
            switch (choix1) {
                case 1:
                    board.prepareClue();
                    break;
                case 2:
                    System.out.println("quelle indice souhaitez vous placer");
                    int choixIndice = scanner1.nextInt();
                    while (choixIndice >= 0 && choixIndice < board.preparedDeck.size()) {
                        System.out.println("Veuiller rentrer un indice compris entre 0 et " + (board.preparedDeck.size() - 1));
                        choixIndice = scanner1.nextInt();
                    }
                    System.out.println("Où souhaitez vous placer la carte indice");
                    System.out.println("Rentrer X5 ");
                    int X5 = scanner1.nextInt();
                    System.out.println("Rentrer Y5 ");
                    int Y5 = scanner1.nextInt();
                    Position positionCarte5 = new Position(X5, Y5);
                    while (board.getCard(positionCarte5) != Board.nullCard) {
                        System.out.println("position invalide");
                        System.out.println("Rentrer X5 ");
                        X5 = scanner1.nextInt();
                        System.out.println("Rentrer Y5 ");
                        Y5 = scanner1.nextInt();
                        positionCarte5 = new Position(X5, Y5);
                        break;
                    }
            }
        }

    }
}
