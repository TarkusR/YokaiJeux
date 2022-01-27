package com.gamelogic.yokai;

import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import Main.GameManager;

import javax.swing.*;

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
    public boolean noIndiceLeft =false ;
    private CardClue[][]clueGrid;
    public static final Card nullCard = new Card("Null");
    public static final CardClue nullCardClue = new CardClue("Null");
    public static final int SIZE = 10;
    private static int clueDraw=0;
    public int[][][] position;
    public int[][][] positionClue;
    private List<JLabel> labels;

    private Set<Card> neighbours = new HashSet<>();

    /*Getter et Setter pour ceux-ci*/

    public Card[][] getGrid() {
        return grid;
    }

    public Card getCard(Position position) {
        return grid[position.getY()][position.getX()];
    }

    // debug
    private void printGrid() {
        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid.length; j++) {

                //debugage
                System.out.print("|");
                if (grid[i][j] != nullCard) {
                    //debugage
                    System.out.print(grid[i][j].getCardType());
                    //UI


                    //mettre carte face cachée

                } else {
                    //debugage
                    System.out.print(" ");
                    //UI


                    //gm.ui.createMovableObject(4,posX,posY,150 ,150,"/res/gamePanel/carteTexture/carteVide.png");
                    //mettre carte vide
                }
                //debugage
                System.out.print("|");


            }

            System.out.println();

        }
    }

    private void findNeighbours(int x, int y, int pastX, int pastY) {
        if ((x + 1 != pastX || y != pastY) && x + 1 < SIZE) {
            if (grid[y][x+1] != nullCard) {
                if (!neighbours.contains(grid[y][x+1])) {
                    neighbours.add(grid[y][x+1]);
                    findNeighbours(x+1, y, pastX, pastY);
                }
            }
        }

        if ((x - 1 != pastX || y != pastY) && x - 1 >= 0) {
            if (grid[y][x-1] != nullCard) {
                if (!neighbours.contains(grid[y][x-1])) {
                    neighbours.add(grid[y][x - 1]);
                    findNeighbours(x - 1, y, pastX, pastY);
                }
            }
        }

        if ((x != pastX || y + 1 != pastY) && y + 1 < SIZE) {
            if (grid[y+1][x] != nullCard) {
                if (!neighbours.contains(grid[y+1][x])) {
                    neighbours.add(grid[y + 1][x]);
                    findNeighbours(x, y + 1, pastX, pastY);
                }
            }
        }

        if ((x != pastX || y - 1 != pastY) && y - 1 >= 0) {
            if (grid[y-1][x] != nullCard) {
                if (!neighbours.contains(grid[y-1][x])) {
                    neighbours.add(grid[y - 1][x]);
                    findNeighbours(x, y - 1, pastX, pastY);
                }
            }
        }
    }

    /* Methode */
    public boolean moveYokai(Position pastPosition, Position newPosition) {
        // les mouvements proposés à la fonction sont toujours dans la grille
        // et sur un emplacement vide

        // en-dessous : FAUX ; à changer
        // Cette fonction n'est appliquée que sur des positions valides,
        // les appels étant déclenchés par des choix au niveau de l'interface graphique,
        // permis uniquement sur des zones valides.

        boolean validMove = false;

        int newX = newPosition.getY();
        int newY = newPosition.getX();
        int pastX =pastPosition.getY();
        int pastY = pastPosition.getX();

        neighbours.clear();

        findNeighbours(newX, newY, pastX, pastY);

        if (neighbours.size() < 15)
            return false;

        // mouvement

        Card temp = grid[pastY][pastX];
        grid[pastY][pastX] = nullCard;
        grid[newY][newX] = temp;
        System.out.println();
        printGrid();


        return true;
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
            for (int i = 0; i <= amounts[amountColors]; i++) {
                List<String> newClue;

                do {
                    Collections.shuffle(colors);
                    newClue = colors.subList(0, amountColors);
                } while (clues.contains(newClue));
                Collections.sort(newClue);
                tempDeck.add(String.join("+", newClue));
            }
        }

        // mélange du deck
        Collections.shuffle(tempDeck);

        deck = new Vector<CardClue>();

        for (int i = 0; i < tempDeck.size(); i++) {
            CardClue clue = new CardClue(tempDeck.get(i));
            clue.setClueType(tempDeck.get(i));
            deck.add(clue);
        }

        System.out.println(tempDeck);
    }

    public Board(int playerCount, GameManager gm) {
        // initialisation de la grille
        gm.ui.window.setSize(1900,1000);
        grid = new Card[SIZE][SIZE];
        position = new int[SIZE+1][SIZE+1][2];
        clueGrid = new CardClue[2][5];


        // cartes null



        for (var row : grid) {
            Arrays.fill(row, nullCard);
        }

        var yokais = new Card[16];

        for (int i = 0; i < 16; i+=4) {
            yokais[i]   = new Card("Ka");
            yokais[i+1] = new Card("Ki");
            yokais[i+2] = new Card("O");
            yokais[i+3] = new Card("Ro");
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
        prepareClue();

        // Creation grille carte indice

        for(int i =0; i< 2;i++){
            for(int j= 0; j<5;j++ ){
                if((i+j)>=clueGrid.length){
                    clueGrid[i][j] = deck.get(i+j);
                }else{
                    clueGrid[i][j]= nullCardClue;
                }
            }
        }
        positionClue = new int[clueGrid.length][clueGrid[1].length][2];
    }

 public void createGridUI(GameManager gm){
     int posX = 1300;
     int posY =750;
     int incr = 4;
     int incr2= 0;
     labels = new ArrayList<>();
     gm.ui.createObject(4,1425,400,100,150,"/res/gamePanel/carteTexture/deckIndice.png","drawClue");
     for(int i=0;i<2;i++){

         for(int j = 0; j< 5;j++){

            if(clueGrid[i][j]!= nullCard){
                //Debugage
                //System.out.println();
                //UI
                System.out.println(i);
                System.out.println("yes");
                positionClue[i][j][0]= posX;
                positionClue[i][j][1]=posY;
                System.out.println(deck.get(i+j).getClueType());
                incr++;
                switch(deck.get(i+j).getClueType()){

                    case "bleu" :labels.add(gm.ui.createMovableObject(4,posX,posY,100,100,"/res/gamePanel/carteTexture/blue.png",gm.ui.dragAndDropClue));break;
                    case "bleu+rouge" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuRouge.png",gm.ui.dragAndDropClue));break;
                    case "bleu+rouge+vert" :labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuRougeVert.png",gm.ui.dragAndDropClue));break;
                    case "bleu+vert" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuVert.png",gm.ui.dragAndDropClue));break;
                    case "bleu+vert+violet" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuVertViolet.png",gm.ui.dragAndDropClue));break;
                    case "bleu+violet" :labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuViolet.png",gm.ui.dragAndDropClue));break;
                    case "bleu+violet+rouge" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuVioletRouge.png",gm.ui.dragAndDropClue));break;
                    case "rouge" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/rouge.png",gm.ui.dragAndDropClue));break;
                    case "vert" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/vert.png",gm.ui.dragAndDropClue)); break;
                    case "rouge+vert": labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/vertRouge.png",gm.ui.dragAndDropClue));break;
                    case "vert+violet" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/vertViolet.png",gm.ui.dragAndDropClue));break;
                    case "rouge+vert+violet" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/vertVioletRouge.png",gm.ui.dragAndDropClue));break;
                    case "violet" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/violet.png",gm.ui.dragAndDropClue));break;
                    case "rouge+violet": labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/violetRouge.png",gm.ui.dragAndDropClue));break;
                }


            }else{


            }

            posX+=100;
         }
         posX = 1300;
         posY-=100;


     }
     for(int i = 0 ;i< labels.size();i++){
         labels.get(i).setVisible(false);
     }



     posX = 1200;
     posY =0 ;

     for(int i =0; i< grid.length;i++){

         for(int j = 0; j< grid.length;j++){

             //debugage
             System.out.print("|");
             if(grid[i][j] != nullCard){
                 //debugage
                 System.out.print(grid[i][j].getCardType());
                 //UI

                 position[i][j][0] = posX;
                 position[i][j][1] = posY;
                 gm.ui.createMovableObject(4,posX,posY,80 ,80,"/res/gamePanel/carteTexture/carteFaceCachee.png",gm.ui.dragAndDropBoard);

                 //mettre carte face cachée

             }else{
                 //debugage
                 System.out.print(" ");
                 //UI


                 position[i][j][0] = posX;
                 position[i][j][1] = posY;
                 //gm.ui.createMovableObject(4,posX,posY,150 ,150,"/res/gamePanel/carteTexture/carteVide.png");
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

    public void drawClue(){
        if(labels.size()>clueDraw){
            labels.get(clueDraw).setVisible(true);
            clueDraw++;
            return;
        }else{
            noIndiceLeft = true;
            return;
        }

    }
}


