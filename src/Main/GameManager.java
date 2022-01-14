package Main;

import Event.EventMP;
import com.gamelogic.yokai.*;

import javax.swing.*;

public class GameManager {
    ActionHandler aHandler = new ActionHandler(this);
    public UI ui = new UI(this);
    public SceneChanger sChanger = new SceneChanger(this);
    public EventMP evMP= new EventMP(this);
    public Game game;
    public static void main(String[] args) {
            new GameManager();
    }

    public GameManager(){

    }


}
