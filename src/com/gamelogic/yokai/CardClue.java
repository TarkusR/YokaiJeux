package com.gamelogic.yokai;

import java.util.Random;

public class CardClue extends Card {

    /*Classe enfant mais on rajoute le type d'indice
    1 = une couleur
    2 = deux couleur
    3 = trois couleur
    4 = placement
    */

    private String cardType="";
    private String clueType="";

    /*super sinon intellij veux pas :/ */

    public void setClueType(String clueType){
        this.clueType=clueType;
    }

    public CardClue(String cardType) {
        super(cardType);
    }

    @Override
    public String getClueType() {
        return clueType;
    }

    /* Methode */

    /*Getter et Setter*/

    /*on peut recuperer le type de la carte avec cela, ca pourrait faciliter la mise en place du plateau*/

    @Override
    public String getCardType() {
        return cardType;
    }

}


