package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionHandler implements ActionListener {

    public ActionHandler(GameManager gm){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String yourChoice = e.getActionCommand();
        /*En fonction de ce qui est fait sur l'ecran il est
        possible de realiser certaine actions*/
        switch(yourChoice){
            /*Exemple
            * case "ClickMenu": gm.evMP.lancerPartie(); break;*/

            case "goLaunchGame" :
        }
    }
}
