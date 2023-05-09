package game;

import game.utility.ConsoleHandler;

import java.io.Serializable;

public class Action implements Serializable {
    private final String description;

    public Action(String description) {
        this.description = description;
    }

    public void performAction(){
        ConsoleHandler.showMessage(description);
    }
}
