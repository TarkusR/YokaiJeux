package Main;

import Event.EventMP;
import com.gamelogic.yokai.*;

import javax.swing.*;

public class GameManager {
    ActionHandler aHandler = new ActionHandler(this);
    //public Board boardPlaceHolder = new Board()
    public UI ui = new UI(this);
    public SceneChanger sChanger = new SceneChanger(this);
    public EventMP evMP= new EventMP(this);

    public static void main(String[] args) {
            new GameManager();
    }

    public GameManager(){

    }


}
