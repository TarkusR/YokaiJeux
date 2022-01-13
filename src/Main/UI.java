package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class UI {
    GameManager gm;
    JFrame window ;
    public JTextArea[] messageText= new JTextArea[20];
    public JPanel bgPanel[] = new JPanel[20];
    public JLabel bgLabel[]= new JLabel[50];
    public int textCounter= 0;



    public UI(GameManager gm){
        this.gm=gm;
        generateScene();
        window.setVisible(true);
    }

    public void createWindow(){
        window = new JFrame();
        window.setSize(1200,900);
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
        bgPanel[bgNum].setBounds(0,0,1200,900);
        bgPanel[bgNum].setBackground(bgColor);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);
        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0,0,1200,900);
        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(bgFilename)));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public void createObject(int bgNumber,int objx,int objy, int objWidth,int objHeight, String objFileName,String command){

        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objx,objy,objWidth,objHeight);

        ImageIcon objectIcon = new ImageIcon(getClass().getResource(objFileName));
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
                    case "1P" : break;
                    case "2P" : break;
                    case "3P" : break;
                    case "4P" : break;
                    case "5P" : break;
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
        createObject(2,100,700,350,40,"/res/menuPrincipale/textObject/scores.png","scores");
        createObject(2,100,750,350,40,"/res/menuPrincipale/textObject/quitter.png","quit");
        bgPanel[2].add(bgLabel[2]);

        // Nouvelle Partie
        createBackground(3,"/res/menuPrincipale/Panel.png",Color.black);
        createObject(3,150,150,350,40,"/res/newGame/textObject/nombreDeJoueur.png","null");
        createObject(3,100,225,350,40,"/res/newGame/textObject/1Joueur.png","1P");
        createObject(3,100,275,350,40,"/res/newGame/textObject/2Joueur.png","2P");
        createObject(3,100,325,350,40,"/res/newGame/textObject/3Joueur.png","3P");
        createObject(3,100,375,350,40,"/res/newGame/textObject/4Joueur.png","4P");
        createObject(3,1060,60,350,40,"/res/newGame/textObject/x.png","backNewGameMP");
        createObject(3,300,225,50,50,"/res/newGame/validation2.png","1P");
        createObject(3,300,275,50,50,"/res/newGame/validation2.png","2P");
        createObject(3,300,325,50,50,"/res/newGame/validation2.png","3P");
        createObject(3,300,375,50,50,"/res/newGame/validation2.png","4P");
        createObject(3,175,425,350,50,"/res/newGame/textObject/cliquerPourContinuer.png","validNewGame");

        bgPanel[3].add(bgLabel[3]);
    }
}
