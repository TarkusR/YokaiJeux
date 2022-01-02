package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class UI {
    GameManager gm;
    JFrame window ;
    public JTextArea messageText;
    public JPanel bgPanel[] = new JPanel[20];
    public JLabel bgLabel[]= new JLabel[20];



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
    }

    public void createTextObject(int boundPosX, int boundPosY, int boundWidth, int boundHeight, String textArea,String command, Color bgColor){
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
                    case "newGame":  break;
                    case "scores" :  break;
                    case "options":  break;
                    case "quit" :  window.dispose(); break;
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
        this.messageText=messageText;
        window.add(messageText);
        window.setResizable(false);
    }

    public void createBackground(int bgNum,String bgFilename, Color bgColor){
        bgPanel[bgNum] = new JPanel();
        bgPanel[bgNum].setBounds(0,0,1200,575);
        bgPanel[bgNum].setBackground(bgColor);
        bgPanel[bgNum].setLayout(null);
        window.add(bgPanel[bgNum]);
        bgLabel[bgNum] = new JLabel();
        bgLabel[bgNum].setBounds(0,0,1200,575);
        ImageIcon bgIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(bgFilename)));
        bgLabel[bgNum].setIcon(bgIcon);
    }

    public void createObject(int bgNumber,int objx,int objy, int objWidth,int objHeight, String objFileName){

        JLabel objectLabel = new JLabel();
        objectLabel.setBounds(objx,objy,objWidth,objHeight);

        ImageIcon objectIcon = new ImageIcon(getClass().getResource(objFileName));
        objectLabel.setIcon(objectIcon);


        bgPanel[bgNumber].add(objectLabel);
        bgPanel[bgNumber].add(bgLabel[bgNumber]);
    }

    public void generateScene(){
        createWindow();
        //Ecran d'intro
        createBackground(1,"/res/LogoMPBlanc.png",Color.black);
        createTextObject(498,600,196,30,"Cliquer pour continuer","pressEnter",Color.black);
        bgPanel[1].add(bgLabel[1]);

        //Ecran Principale
        createBackground(2,"/res/LogoMPBlanc.png",Color.black);
        createObject(2,0,0,150,75,"/res/LogoIsep.png");
        bgPanel[2].add(bgLabel[2]);
    }
}
