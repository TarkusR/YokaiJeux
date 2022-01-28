package Main;

import Event.DragAndDrop.DragAndDropBoard;
import Event.DragAndDrop.DragAndDropClue;
import com.gamelogic.yokai.Player;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class UI {
    public static GameManager gm;
    public DragAndDropBoard dragAndDropBoard;
    public DragAndDropClue dragAndDropClue;
    public JFrame window ;
    public JTextArea[] messageText= new JTextArea[20];
    public JPanel bgPanel[] = new JPanel[20];
    public JLabel bgLabel[]= new JLabel[50];
    public int textCounter= 0;
    public int playerAmount= 2;
    public int difficulty = 1;
    public String gameType = "null";
    public Player scoreName = new Player("");
    public List<JLabel> labelsUI;
    private List<JLabel> labels;

    public UI(GameManager gm){
        this.gm=gm;
        generateScene();
        window.setVisible(true);
    }

    public void createWindow(){
        window = new JFrame();
        window.setSize(1200,900);
        window.setTitle("Yokai");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/res/icon.png")));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.black);
        window.setLayout(null);
        window.setResizable(false);
    }

    public void createTextObject(int boundPosX, int boundPosY, int boundWidth, int boundHeight, String textArea,String command, Color bgColor){
        this.textCounter++;
        final JTextArea messageText = new JTextArea(textArea);
        messageText.setBounds(boundPosX,boundPosY,boundWidth,boundHeight);
        messageText.setBackground(bgColor);
        messageText.setForeground(Color.white);
        messageText.setEditable(false);
        messageText.setLineWrap(true);
        messageText.setWrapStyleWord(true);
        messageText.setFont(new Font("Pristina",Font.PLAIN,26));
        messageText.setHighlighter(null);
        messageText.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                switch(command){
                    case "pressEnter" : gm.sChanger.showMenuPrincipale(); break;
                    case "newGame": gm.sChanger.showNewGame(); break;
                    case "scores" :  break;
                    case "options":  break;
                    case "quit" :  window.dispose(); break;
                    case "playerSelect" : break;
                    //case "drawClue": gm.evMP.createUIClue();break;
                }
            }
            public void mousePressed(MouseEvent e) {

            }
            public void mouseReleased(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {
                messageText.setFont(new Font("Pristina",Font.BOLD,26));
            }
            public void mouseExited(MouseEvent e) {
                messageText.setFont(new Font("Pristina",Font.PLAIN,26));
            }
        });
        this.messageText[textCounter]=messageText;
        window.add(this.messageText[textCounter]);
        window.setResizable(false);
    }

    public void createBackground(int bgNum,String bgFilename, Color bgColor){
        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setOpaque(true);
        bgPanel[bgNum].setBounds(0,0,1200,900);
        bgPanel[bgNum].setBackground(bgColor);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);
        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0,0,1200,900);
        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(bgFilename)));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public void createGridBackground(int bgNum,String bgFilename, Color bgColor){
        bgPanel[bgNum] = new JPanel(new GridLayout(16,16,10,10));
        bgPanel[bgNum].setOpaque(true);
        bgPanel[bgNum].setBounds(0,0,1900,1000);
        bgPanel[bgNum].setBackground(bgColor);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);
        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0,0,1900,1000);
        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(bgFilename)));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public JLabel createObject(int bgNumber,int objx,int objy, int objWidth,int objHeight, String objFileName,String command){

        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objx,objy,objWidth,objHeight);

        ImageIcon objectIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(objFileName)));
        objectLabel.setIcon(objectIcon);

        objectLabel.addMouseListener(new MouseListener() {

            public void mouseClicked(MouseEvent e) {
                switch(command){
                    case "null": break;
                    case "pressEnter" : gm.sChanger.showMenuPrincipale(); break;
                    case "newGame": gm.sChanger.showNewGame(); break;
                    case "scores" :  break;
                    case "options":  break;
                    case "quit" :  window.dispose(); break;
                    case "playerSelect" : break;
                    case "backNewGameMP" : gm.sChanger.showMPNewGame(); break;

                    case "2P" : radioChange("player", 2);break;
                    case "3P" : radioChange("player", 3);break;
                    case "4P" : radioChange("player", 4);break;

                    case "facile"    : radioChange("difficulty", 1);break;
                    case "normal"    : radioChange("difficulty", 2);break;
                    case "difficile" : radioChange("difficulty", 3);break;
                    case "expert"    : radioChange("difficulty", 4);break;

                    case "validNewGame" : gm.sChanger.showGamePanel();break;
                    case "drawClue":gm.game.board.drawClue();break;
                    case "apaiser" : gm.game.board.endGame();break;
                    case "non": gm.game.board.turnCheck();break;

                }
            }

            public void mousePressed(MouseEvent e) {

            }
            public void mouseReleased(MouseEvent e) {

            }
            public void mouseEntered(MouseEvent e) {

            }
            public void mouseExited(MouseEvent e) {

            }
        });
        bgPanel[bgNumber].add(objectLabel);
        bgPanel[bgNumber].add(bgLabel[bgNumber]);

        return objectLabel;
    }

    public JLabel createMovableObject(int bgNumber, int objx, int objy, int objWidth, int objHeight, String objFileName, MouseAdapter dragDropController){
        dragAndDropBoard=new DragAndDropBoard(gm);

        Border outsideBorder = new LineBorder(Color.black);
        Border insideBorder = new EmptyBorder(10,0,10,10);
        JLabel objectLabel = new JLabel();

        objectLabel.setBorder(BorderFactory.createCompoundBorder(outsideBorder,insideBorder));
        objectLabel.setOpaque(false);

        objectLabel.setBounds(objx,objy,objWidth,objHeight);
        ImageIcon objectIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(objFileName)));
        objectLabel.addMouseListener(dragDropController);
        objectLabel.addMouseMotionListener(dragDropController);
        objectLabel.setPreferredSize(objectLabel.getPreferredSize());

        objectLabel.setIcon(objectIcon);

        bgPanel[bgNumber].add(objectLabel);
        bgPanel[bgNumber].add(bgLabel[bgNumber]);
        return objectLabel;
    }
    public void generateScene(){
        createWindow();
        //Ecran d'intro
        createBackground(1,"/res/menuPrincipale/LogoMPBlanc.png",Color.black);
        createObject(1,450,700,350,40,"/res/menuPrincipale/textObject/cliquerPourContinuer.png","pressEnter");
        bgPanel[1].add(bgLabel[1]);

        //Ecran Principale
        createBackground(2,"/res/menuPrincipale/LogoMPBlanc.png",Color.black);
        createObject(2,0,0,150,75,"/res/menuPrincipale/LogoIsep.png","null");
        createObject(2,100,650,350,40,"/res/menuPrincipale/textObject/nouvellePartie.png","newGame");
        //createObject(2,100,700,350,40,"/res/menuPrincipale/textObject/scores.png","scores");
        createObject(2,100,700,350,40,"/res/menuPrincipale/textObject/quitter.png","quit");
        bgPanel[2].add(bgLabel[2]);

        // Nouvelle Partie
        createBackground(3,"/res/menuPrincipale/Panel.png",Color.black);

        createObject(3,150,150,350,40,"/res/newGame/textObject/nombreDeJoueur.png","null");
        createObject(3,1060,60,350,40,"/res/newGame/textObject/X.png","backNewGameMP");
        createObject(3,175,425,350,50,"/res/newGame/textObject/cliquerPourContinuer.png","validNewGame");

        createObject(3,100,225,350,40,"/res/newGame/textObject/2Joueur.png","2P");
        createObject(3,100,275,350,40,"/res/newGame/textObject/3Joueur.png","3P");
        createObject(3,100,325,350,40,"/res/newGame/textObject/4Joueur.png","4P");

        labels = new ArrayList<>();

        labels.add(createObject(3,300,225,50,50,"/res/newGame/validation.png","2P"));
        labels.add(createObject(3,300,275,50,50,"/res/newGame/validation2.png","3P"));
        labels.add(createObject(3,300,325,50,50,"/res/newGame/validation2.png","4P"));



        createObject(3,650,150,350,40,"/res/newGame/textObject/difficulte.png","null");

        createObject(3,600,225,350,40,"/res/newGame/textObject/facile.png","facile");
        createObject(3,600,275,350,40,"/res/newGame/textObject/normal.png","normal");
        createObject(3,600,325,350,40,"/res/newGame/textObject/difficile.png","difficile");
        createObject(3,600,375,350,40,"/res/newGame/textObject/expert.png","expert");

        labels.add(createObject(3,800,225,50,50,"/res/newGame/validation.png","facile"));
        labels.add(createObject(3,800,275,50,50,"/res/newGame/validation2.png","normal"));
        labels.add(createObject(3,800,325,50,50,"/res/newGame/validation2.png","difficile"));
        labels.add(createObject(3,800,375,50,50,"/res/newGame/validation2.png","expert"));

        bgPanel[3].add(bgLabel[3]);

        labelsUI = new ArrayList<>();
        //Ecran de jeu
        createGridBackground(4,"/res/gamePanel/background.png",Color.black);
        labelsUI.add(createObject(4,100,500,350,40,"/res/gamePanel/carteTexture/appaiser.png","apaiser"));
        labelsUI.add(createObject(4,150,425,350,40,"/res/gamePanel/carteTexture/apaiserQuestion.png","null"));
        labelsUI.add(createObject(4,450,500,350,40,"/res/gamePanel/carteTexture/non.png","non"));
        labelsUI.add(createObject(4,150,425,350,40,"/res/gamePanel/carteTexture/regarder2Cartes.png","null"));
        labelsUI.add(createObject(4,150,425,350,40,"/res/gamePanel/carteTexture/retournerOP.png","null"));
        for(int i = 0;i<labelsUI.size();i++){
            labelsUI.get(i).setVisible(false);
        }

        bgPanel[4].add(bgLabel[4]);
    }

    private void radioChange(String type, int value) {
        JLabel past;
        JLabel now;

        if (type.equals("player")) {
            if (value == playerAmount)
                return;

            past = labels.get(playerAmount-2);
            now = labels.get(value-2);

            playerAmount = value;
        } else { // "difficulty"
            if (value == difficulty)
                return;

            past = labels.get(difficulty-1 + 3);
            now = labels.get(value-1 + 3);

            difficulty = value;
        }

        ImageIcon yesIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/newGame/validation.png")));
        ImageIcon noIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/res/newGame/validation2.png")));

        past.setIcon(noIcon);
        now.setIcon(yesIcon);
    }
}
