package game;

import game.enums.Direction;
import game.enums.Message;
import game.utility.ConsoleHandler;

import javax.naming.OperationNotSupportedException;
import java.io.Serializable;

public class Character implements Serializable {
    private final String name;
    private final Inventory inventory;
    public Object fight;
    private Scene currentScene;
    private int characterHP;
    public int currentcharacterDamage;
    public int currentcharacterHP ;
    private int characterDamage;


    public Character(String name,int characterHP,int characterDamage ,Scene initialScene) {
        this.name = name;
        this.inventory = new Inventory();
        this.currentScene = initialScene;
        this.characterHP = characterHP;
        this.characterDamage = characterDamage;

    }

    public int setplayerHP(int currentplayerHP) {
        currentplayerHP=characterHP;
        characterHP = characterHP + 10;
        currentplayerHP=characterHP;
        return currentplayerHP;
    }

    public  int getPlayerDamage() {
        return characterDamage;
    }

    public  int getHit(int enemiesDamage) {
        characterHP = characterHP - enemiesDamage;
        return characterHP;
    }


    public int setPlayerDamage(int currentplayerDamage) {
        currentplayerDamage=characterDamage;
        characterDamage = characterDamage + 2;
        currentplayerDamage=characterDamage;
        return currentplayerDamage;
    }

    public  int getPlayerHP() {
        return characterHP;
    }


    /*
    Tries to move the character in the specified direction
     */
    public void move(Direction direction){
        try {
            Scene neighborScene = currentScene.getNeighborScene(direction);
            System.out.println("here");
            if (neighborScene != null) {//If scene exists in that direction.
                currentScene = currentScene.getNeighborScene(direction);//move character.
                ConsoleHandler.showMessage(currentScene.getEnterMessage());
            } else {
                throw new OperationNotSupportedException();
            }
        }
        catch (OperationNotSupportedException operationNotSupportedException){//If can't move to that scene show dead end message.
            ConsoleHandler.showMessage(Message.DEAD_END.getText());
        }



        }

    public Inventory getInventory() {
        return inventory;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }


}