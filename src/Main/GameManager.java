package Main;

import Event.Event;
import com.gamelogic.yokai.*;

public class GameManager {
    ActionHandler aHandler = new ActionHandler(this);
    public UI ui = new UI(this);
    public SceneChanger sChanger = new SceneChanger(this);
    public Event evMP= new Event(this);
    public Game game;
    public static void main(String[] args) {
            new GameManager();
    }

    public GameManager(){

    }


}
