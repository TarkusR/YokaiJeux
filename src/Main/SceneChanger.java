package Main;

import com.gamelogic.yokai.Board;
import com.gamelogic.yokai.Game;
import jdk.swing.interop.SwingInterOpUtils;

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
    }

    public void showScores(){

    }

    public void showNewGame(){
        gm.ui.bgPanel[2].setVisible(false);
        gm.ui.bgPanel[3].setVisible(true);
    }

    public void showMPNewGame(){
        gm.ui.bgPanel[3].setVisible(false);
        gm.ui.bgPanel[2].setVisible(true);
    }

    public void showGamePanel(){
        gm.ui.bgPanel[3].setVisible(false);
        gm.ui.bgPanel[4].setVisible(true);
        gm.game = new Game(gm.ui.difficulty,gm.ui.playerAmount,gm.ui.gameType,gm.ui.scoreName,gm);
        gm.game.board.createGridUI(this.gm);

    }
}
