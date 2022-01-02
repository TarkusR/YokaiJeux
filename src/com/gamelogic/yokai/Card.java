package com.gamelogic.yokai;

public class Card {
    /*la une carte est elle a coté (utile pour la logique de victoire)*/

    private boolean isCardNear = false;

    /*Type de carte*/

    private String cardType;

    private CardClue clue;
    public boolean isFixed = false;

    public boolean isCardNear() {
        return isCardNear;
    }

    public void setCardNear(boolean cardNear) {
        isCardNear = cardNear;
    }

    public void setClue(CardClue clue) {
        this.clue = clue;
        isFixed = true;
    }

    public String getCardType() {
        return cardType;
    }

    public Card(String cardType) {
        this.cardType = cardType;
    }
}

