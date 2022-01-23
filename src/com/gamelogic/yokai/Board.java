package com.gamelogic.yokai;

import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
import Main.GameManager;

public class Board {
    /* Classe représentant le plateau de jeu et en charge de la gestion de ses cartes.
     *
     * Inclut :
     * - une grille de cartes de taille (arbitraire) SIZExSIZE, SIZE constante paire définie ci-après
     * - une liste de cartes indices pas encore préparées
     * - une liste des cartes indices préparées mais pas encore placées
     *
     * La grille peut contenir 3 sortes de Card, identifiées par leur attribut cardType :
     * - une carte yokai
     * - une carte indice : représente le placement d'une carte indice par dessus une carte yokai, et contient celle-ci
     * - une carte null   : représente l'absence de carte
     */


    /*Le tableau de jeu est un tableau de carte (on peut aussi y mettre les classe enfant de carte) et si la tile est vide les 2 seul attributs importants*/
    GameManager gm;
    public List<CardClue> deck;
    public List<CardClue> preparedDeck;
    private Card[][] grid;
    public static final Card nullCard = new Card("Null");
    private static final int SIZE = 10;
    public int[][] position;

    /*Getter et Setter pour ceux-ci*/

    public Card[][] getGrid() {
        return grid;
    }

    public Card getCard(Position position) {
        return grid[position.getY()][position.getX()];
    }

    /* Methode */
    public void moveYokai(Position pastPosition, Position newPosition) {
        // Cette fonction n'est appliquée que sur des positions valides,
        // les appels étant déclenchés par des choix au niveau de l'interface graphique,
        // permis uniquement sur des zones valides.

        int newX = newPosition.getX();
        int newY = newPosition.getY();
        int pastX = pastPosition.getX();
        int pastY = pastPosition.getY();

        Card temp = grid[pastY][pastX];
        grid[pastY][pastX] = nullCard;
        grid[newY][newX] = temp;
    }

    public void placeClue(CardClue clue, Position position) {
        // Cette fonction n'est appliquée que sur des positions valides,
        // les appels étant déclenchés par des choix au niveau de l'interface graphique,
        // permis uniquement sur des zones valides.
        // Elle vient donc se placer sur une carte non nulle et sans indice.

        int x = position.getX();
        int y = position.getY();

        grid[y][x].setClue(clue);
    }

    public void prepareClue() {
        // pop from deck into preparedDeck
        preparedDeck.add(deck.remove(0));
    }

    private void createDeck(int oneColors, int twoColors, int threeColors) {
        // en français pour les noms des fichiers à l'avenir
        var colors = new ArrayList<>(List.of("rouge", "vert", "violet", "bleu"));
        int[] amounts = {oneColors, twoColors, threeColors};

        var tempDeck = new Vector<String>();

        // au vu du nombre d'exécutions de ce code et la taille de la liste,
        // la mélanger jusqu'à une trentaine fois est négligemment inefficace mais simplifie le code
        // assurant de ne pas répéter une couleur dans une même carte
        for (int amountColors = 1; amountColors < 3; amountColors++) {
            var clues = new Vector<List<String>>();
            for (int i = 0; i < amounts[amountColors]; i++) {
                List<String> newClue;

                do {
                    Collections.shuffle(colors);
                    newClue = colors.subList(0, amountColors);
                } while (clues.contains(newClue));

                tempDeck.add(String.join(",", newClue));
            }
        }

        // mélange du deck
        Collections.shuffle(tempDeck);

        deck = new Vector<CardClue>();

        for (int i = 0; i < tempDeck.size(); i++) {
            deck.add(new CardClue(tempDeck.get(i)));
        }
    }

    public Board(int playerCount, GameManager gm) {
        // initialisation de la grille
        gm.ui.window.setSize(1900,1000);
        int posX = 425;
        int posY =0 ;
        grid = new Card[SIZE][SIZE];
        position = new int[SIZE * SIZE][2];
        // cartes null

        for (var row : grid) {
            Arrays.fill(row, nullCard);
        }

        var yokais = new Card[16];

        for (int i = 0; i < 16; i+=4) {
            yokais[i]   = new Card("Kappa");
            yokais[i+1] = new Card("Kitsune");
            yokais[i+2] = new Card("Oni");
            yokais[i+3] = new Card("Rokurobi");
        }

        Collections.shuffle(Arrays.asList(yokais));

        // yokais centrés

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[SIZE/2 - 2 + i][SIZE/2 - 2 + j] = yokais[i*4 + j];
            }
        }

        // initialisation du deck de cartes indices

        switch (playerCount) {
            case 2:
                createDeck(2,3,2);
                break;
            case 3:
                createDeck(2,4,3);
                break;
            case 4:
                createDeck(3,4,3);
                break;
        }

        preparedDeck = new Vector<CardClue>();

        for(int i =0; i< grid.length;i++){

            for(int j = 0; j< grid.length;j++){

                //debugage
                System.out.print("|");
                if(grid[i][j].getCardType()!= nullCard.getCardType()){
                    //debugage
                    System.out.print("x");
                    //UI
                    position[i+j][0] = posX;
                    position[i+j][1]= posY;
                    gm.ui.createMovableObject(4,posX,posY,150 ,150,"/res/gamePanel/carteTexture/carteFaceCachee.png");

                    //mettre carte face cachée

                }else{
                    //debugage
                    System.out.print(" ");
                    //UI
                    position[i+j][0] = posX;
                    position[i+j][1] = posY;
                    gm.ui.createMovableObject(4,posX,posY,150 ,150,"/res/gamePanel/carteTexture/carteVide.png");
                    //mettre carte vide
                }
                //debugage
                System.out.print("|");
                posX+=100;
            }
            posX=425;
            posY+=100;
            System.out.println();
        }
        }
}
