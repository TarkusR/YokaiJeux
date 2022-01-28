package com.gamelogic.yokai;

import java.util.*;

import Event.DragAndDrop.DragAndDropClue;
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
    private List<JLabel> labelsYokai;
    public boolean apaiser = false;
    private Set<Card> neighbours = new HashSet<>();
    private Map<String, Set<Card>> family = new HashMap<>();

    private int returned;

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
    private void findNeighbours(int x, int y, int pastX, int pastY, boolean same) {
        Card c;

        if ((x + 1 != pastX || y != pastY) && x + 1 < SIZE) {
            c = grid[y][x+1];
            String type = c.getCardType();

            if (c != nullCard) {
                if (same) {
                    if (c.getCardType().equals(type) && !family.get(type).contains(c)) {
                        family.get(type).add(c);
                        findNeighbours(x + 1, y, pastX, pastY, true);
                    }
                } else {
                    if (!neighbours.contains(c)) {
                        neighbours.add(c);
                        findNeighbours(x + 1, y, pastX, pastY, false);
                    }
                }
            }
        }

        if ((x - 1 != pastX || y != pastY) && x - 1 >= 0) {
            c = grid[y][x-1];
            String type = c.getCardType();

            if (c != nullCard) {
                if (same) {
                    if (c.getCardType().equals(type) && !family.get(type).contains(c)) {
                        family.get(type).add(c);
                        findNeighbours(x - 1, y, pastX, pastY, true);
                    }
                } else {
                    if (!neighbours.contains(c)) {
                        neighbours.add(c);
                        findNeighbours(x - 1, y, pastX, pastY, false);
                    }
                }
            }
        }

        if ((x != pastX || y + 1 != pastY) && y + 1 < SIZE) {
            c = grid[y+1][x];
            String type = c.getCardType();

            if (c != nullCard) {
                if (same) {
                    if (c.getCardType().equals(type) && !family.get(type).contains(c)) {
                        family.get(type).add(c);
                        findNeighbours(x, y + 1, pastX, pastY, true);
                    }
                } else {
                    if (!neighbours.contains(c)) {
                        neighbours.add(c);
                        findNeighbours(x, y + 1, pastX, pastY, false);
                    }
                }
            }
        }

        if ((x != pastX || y - 1 != pastY) && y - 1 >= 0) {
            c = grid[y-1][x];
            String type = c.getCardType();

            if (c != nullCard) {
                if (same) {
                    if (c.getCardType().equals(type) && !family.get(type).contains(c)) {
                        family.get(type).add(c);
                        findNeighbours(x, y - 1, pastX, pastY, true);
                    }
                } else {
                    if (!neighbours.contains(c)) {
                        neighbours.add(c);
                        findNeighbours(x, y - 1, pastX, pastY, false);
                    }
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

        findNeighbours(newX, newY, pastX, pastY, false);

        if (neighbours.size() < 15)
            return false;

        // mouvement
        labelsYokai.add((newY*SIZE)+newX,labelsYokai.get((pastY*SIZE)+pastX));
        labelsYokai.remove(((pastY*SIZE)+pastX));
        Card temp = grid[pastY][pastX];
        grid[pastY][pastX] = nullCard;
        grid[newY][newX] = temp;
        System.out.println();
        printGrid();
        System.out.println(apaise());

        turnIndice();

        return true;
    }

    public boolean apaise() {
        String[] types = {"Ka", "Ki", "O", "Ro"};

        boolean breaking;

        for (String type : types) {
            family.get(type).clear();

            breaking = false;

            for (int x = 0; x < SIZE; x++) {
                for (int y = 0; y < SIZE; y++) {
                    if (grid[y][x].getCardType().equals(type)) {
                        findNeighbours(x, y,-2, -2, true);

                        if (family.get(type).size() < 3)
                            return false;

                        breaking = true;
                        break;
                    }
                }
                if (breaking)
                    break;
            }
        }

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
                }while (clues.contains(newClue));

                Collections.sort(newClue);
                Collections.sort(newClue);
                tempDeck.add(String.join("+", newClue));
            }
        }
        // mélange du deck
        Collections.shuffle(tempDeck);

        deck = new Vector<CardClue>();

        for (int i = 0; i < tempDeck.size(); i++) {
            CardClue clue = new CardClue(tempDeck.get(i));
            System.out.println(tempDeck.get(i));
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
        clueGrid = new CardClue[2][6];

        family.put("Ka", new HashSet<>());
        family.put("Ki", new HashSet<>());
        family.put("O", new HashSet<>());
        family.put("Ro", new HashSet<>());

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
        playerCount = gm.ui.playerAmount;
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

        for(int i = 0; i< 2;i++){
            for(int j= 1; j<=5;j++ ){
                if(((i*5)+j)<=deck.size()){
                    System.out.println(deck.size());
                    System.out.println((i*5)+j);
                    clueGrid[i][j] = deck.get((i*5)+j-1);
                }else{
                    clueGrid[i][j]= nullCardClue;
                }
            }
        }
        positionClue = new int[clueGrid.length][clueGrid[1].length][2];

    }

 public void createGridUI(GameManager gm){
     int posX = 1250;
     int posY =750;
     int incr = 4;
     int incr2= 0;
     labels = new ArrayList<>();
     labelsYokai = new ArrayList<>();
     gm.ui.createObject(4,1425,400,100,150,"/res/gamePanel/carteTexture/deckIndice.png","drawClue");
     for(int i=0;i<2;i++){

         for(int j = 0; j<=5;j++){

            if(clueGrid[i][j] != nullCard && clueGrid[i][j]!= null){
                //Debugage
                //System.out.println();
                //UI
                gm.ui.dragAndDropClue=new DragAndDropClue(gm);
                System.out.println(clueGrid.length);
                positionClue[i][j][0]= posX;
                positionClue[i][j][1]=posY;
                incr++;
                switch(clueGrid[i][j].getClueType()){

                    case "bleu" :labels.add(gm.ui.createMovableObject(4,posX,posY,100,100,"/res/gamePanel/carteTexture/blue.png",gm.ui.dragAndDropClue));break;
                    case "bleu+rouge" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuRouge.png",gm.ui.dragAndDropClue));break;
                    case "bleu+rouge+vert" :labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuRougeVert.png",gm.ui.dragAndDropClue));break;
                    case "bleu+vert" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuVert.png",gm.ui.dragAndDropClue));break;
                    case "bleu+vert+violet" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuVertViolet.png",gm.ui.dragAndDropClue));break;
                    case "bleu+violet" :labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuViolet.png",gm.ui.dragAndDropClue));break;
                    case "bleu+rouge+violet" : labels.add(gm.ui.createMovableObject(4,posX,posY,100 ,100,"/res/gamePanel/carteTexture/bleuVioletRouge.png",gm.ui.dragAndDropClue));break;
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
         posX = 1250;
         posY-=100;


     }
     for(int i = 0 ;i< labels.size();i++){
         labels.get(i).setVisible(false);
     }
     System.out.println(labels.size());



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
                 labelsYokai.add(gm.ui.createMovableObject(4,posX,posY,80 ,80,"/res/gamePanel/carteTexture/carteFaceCachee.png",gm.ui.dragAndDropBoard));
                 //mettre carte face cachée

             }else{
                 //debugage
                 System.out.print(" ");
                 //UI


                 position[i][j][0] = posX;
                 position[i][j][1] = posY;
                 labelsYokai.add(null);
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
     turnBegin();
    }

    public void drawClue(){
        if (!gm.game.canMoveClue)
            return;

            labels.get(clueDraw).setVisible(true);
            prepareClue();

            System.out.println(clueDraw);
            clueDraw++;

            turnEnd();
    }

    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public boolean returnCard(int i , int j) throws InterruptedException {
        Card card = grid[i][j];

        if (card.isReturned) {
            labelsYokai.get((i*SIZE)+j).setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/gamePanel/carteTexture/carteFaceCachee.png"))));
            card.isReturned=false;
            returned++;
        } else {
            //System.out.println(j+" "+i);
            //System.out.println(labelsYokai.size());
            switch(card.getCardType()){
                case "Ka" : labelsYokai.get((i*SIZE)+j).setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/gamePanel/carteTexture/carteVerte.png"))));break;
                case "Ro" : labelsYokai.get((i*SIZE)+j).setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/gamePanel/carteTexture/carteViolette.png"))));break;
             //   case "Ki" : labelsYokai.get((i*SIZE)+j).setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/gamePanel/carteTexture/carteRouge.png"))));break;
                case "O" : labelsYokai.get((i*SIZE)+j).setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/gamePanel/carteTexture/carteBleu.png"))));break;
            }

            card.isReturned=true;

        }

        if (returned == 2) {
            return true;
        }

        return false;
    }

/*
    public void play() {
        // TODO : if indices restants (voir règles)
        while (!ending) {
            System.out.println("Joueur " + gm.game.tour);
            beginTurn(); 
        }
    }
    */

    public void turnBegin() {
        //System.exit(0);

        System.out.println("Joueur " + gm.game.tour);

        System.exit(0);
        
        gm.ui.labelsUI.get(0).setVisible(true); // bouton apaisé
        gm.ui.labelsUI.get(1).setVisible(true); // question : "apaisé ?"
        gm.ui.labelsUI.get(2).setVisible(true); // bouton non
    }

    public void endGame() {
        gm.ui.labelsUI.get(0).setVisible(false); // bouton apaisé
        gm.ui.labelsUI.get(1).setVisible(false); // question : "apaisé ?"
        gm.ui.labelsUI.get(2).setVisible(false); // bouton non

        gm.game.ending = true;

        if (apaise()) {
            System.out.println("GAGNE");
        } else {
            System.out.println("PERDU");
        }
    }

    // Voir 2 cartes
    public void turnCheck() {
        gm.ui.labelsUI.get(0).setVisible(false); // bouton apaisé
        gm.ui.labelsUI.get(1).setVisible(false); // question : "apaisé ?"
        gm.ui.labelsUI.get(2).setVisible(false); // bouton non

        gm.game.canCheckCard = true;
        returned = 0;
    }

    // Déplacer une carte
    public void turnMove() {
        gm.game.canCheckCard = false;
        gm.game.canMoveCard = true;
    }

    // Révéler indice ou en placer un
    public void turnIndice() {
        gm.game.canMoveCard = false;
        gm.game.canMoveClue = true;
    }

    public void turnEnd() {
        gm.game.canMoveClue = false;
        gm.game.tour++;

        if(labels.size()==clueDraw) {
            endGame();
        } else {
            gm.game.tour++;
            turnBegin();
        }
    }
}


