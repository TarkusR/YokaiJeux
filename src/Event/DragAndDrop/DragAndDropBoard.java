package Event.DragAndDrop;

import Main.GameManager;
import com.gamelogic.yokai.Position;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DragAndDropBoard extends MouseAdapter {

    GameManager gm;
    private Point initialLoc;
    private Point initialLocOnScreen;
    public int[][][] position ;



    public DragAndDropBoard(GameManager gm){
        this.gm=gm;
        this.position = gm.game.board.position;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Component comp = (Component)e.getSource();
        initialLoc = comp.getLocation();
        initialLocOnScreen = e.getLocationOnScreen();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Position pastPosition = null;
        Component comp = (Component)e.getSource();
        Point locOnScreen = e.getLocationOnScreen();
        boolean flag = false;
        int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
        int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;
        for(int i = 0 ; i< position[1].length;i++) {
            for(int j = 0; j < position[1].length;j++){
                        if(initialLoc.x<=position[i][j][0]+50 && initialLoc.x>=position[i][j][0]-50){


                            if (initialLoc.y<=position[i][j][1]+50 && initialLoc.y>=position[i][j][1]-50){

                                pastPosition = new Position(i,j);
                                flag=true;
                            }
                            if(flag){
                                break;
                            }
                        }
                if(flag){
                    break;
                }
            }
        }
        flag = false;
        for(int i = 0 ; i< position[1].length-1;i++){

                for(int j = 0; j < position[1].length-1;j++){
                    if(x<=position[i][j][0]+50 && x>=position[i][j][0]-50){

                        if (y<=position[i][j][1]+50 && y>=position[i][j][1]-50){
                            if(gm.game.board.getGrid()[i][j]==gm.game.board.nullCard){
                                int cor1 = i;
                                int cor2 = j;
                                Position newPosition = new Position(cor1,cor2);

                                // appliquer le mouvement s'il est valide
                                if (gm.game.board.moveYokai(pastPosition, newPosition))
                                    comp.setLocation(position[i][j][0],position[i][j][1]);
                                flag = true;
                            }

                        }

                    }else{
                        comp.setLocation(initialLoc.x,initialLoc.y);
                    }
                    if(flag){
                        break;
                    }
                }
            if(flag){
                break;
            }

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Component comp = (Component)e.getSource();
        Point locOnScreen = e.getLocationOnScreen();

        int x = locOnScreen.x - initialLocOnScreen.x + initialLoc.x;
        int y = locOnScreen.y - initialLocOnScreen.y + initialLoc.y;
        comp.setLocation(x, y);
    }
}