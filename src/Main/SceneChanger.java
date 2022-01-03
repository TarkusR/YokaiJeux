package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SceneChanger {
    GameManager gm;

    public SceneChanger(GameManager gm){
        this.gm = gm;
    }

    public void moveToFront(){
        for(int i=1;i<=gm.ui.textCounter;i++){
            gm.ui.messageText[i].setFont(new Font("Pristina",Font.BOLD,26));
            gm.ui.messageText[i].setFont(new Font("Pristina",Font.PLAIN,26));
        }
    }

    public void deleteText(){
        for(int i=1;i<=gm.ui.textCounter;i++){
            gm.ui.messageText[i].selectAll();
            gm.ui.messageText[i].replaceSelection("");
        }
    }

    public void showIntro(){
        gm.ui.bgPanel[1].setVisible(true);
        gm.ui.bgPanel[2].setVisible(false);
        gm.ui.createTextObject(498,600,196,30,"Cliquer pour continuer","pressEnter", Color.black);
    }

    public void showMenuPrincipale(){
        gm.ui.bgPanel[1].setVisible(false);
        gm.ui.bgPanel[2].setVisible(true);
        deleteText();
        gm.ui.createTextObject(75,600,150,30,"Nouvelle Partie","newGame",Color.black);
        gm.ui.createTextObject(75,650,140,30,"Scores","scores",Color.black);
        gm.ui.createTextObject(75,700,140,30,"Quitter","quit",Color.black);
    }

    public void showScores(){

    }

    public void showNewGame(){
        gm.ui.bgPanel[2].setVisible(false);
        gm.ui.bgPanel[3].setVisible(true);
        deleteText();


    }
}
