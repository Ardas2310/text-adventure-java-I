package game.utility;

import game.*;
import game.Character;
import game.enums.Direction;
import game.Enemies;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import game.enums.ItemProperty;

public class GameManager implements Serializable {
    private static final long serialVersionUID = 1L;

    //Only one instance of the GameManager class is allowed.
    private static GameManager instance;



    private boolean isInitialized;

    //Get the instance.
    public static GameManager getInstance() {
        return Objects.requireNonNullElseGet(instance, GameManager::new);
    }
    //Set instance (used while loading the game)
    public static void setInstance(GameManager gameManager){
        instance = gameManager;
    }
    private GameManager(){
        instance = this;
    }

    private Enemies enemies;
    private Character character;
    //private Character rat;

    public void initializeGame(){
        Scene initial = generateScenes();
        generateCharacter(initial);
        isInitialized = true;
    }

    /*
    Generates all the scenes of the game.
     */
    private Scene generateScenes(){
        Scene initialScene = new Scene("Initial Scene", "You are at a grassland.");
        Scene caveSecretRoom = new Scene("Cave Secret Room", "You enter a secret room inside the cave.");
        Scene cave = new Scene("Cave", "You enter into a dark cave.");
        Scene forest = new Scene("Forest", "You enter into a dense forest.");
        Scene lake = new Scene("Lake", "You are at a lake.");



        //Generate Scene Items.
        Item flower = new Item("Flower");
        flower.addItemProperty(ItemProperty.EATABLE,new Action("You just ate a poppy, nothing happened."));
        flower.addItemProperty(ItemProperty.TAKEABLE, new Action("You put the flower in your inventory."));

        Item rustySword = new Item("Rusty Sword");
        rustySword.addItemProperty(ItemProperty.TAKEABLE, new Action("You put the rusty sword in your inventory."));
        rustySword.addItemProperty(ItemProperty.USEABLE, new Action("You swing the rusty sword."));

        Item oldBook = new Item("Old Book");
        oldBook.addItemProperty(ItemProperty.READABLE, new Action("You try to read the book, a lot of pages are torn apart and it seems that it is written in some sort of ancient language.\n" +
                "As you try to figure out what these ancient symbols mean you successfully recognize the symbols: Sky, Crystal, Gauntlet."));
        oldBook.addItemProperty(ItemProperty.TAKEABLE, new Action("You put the old book in your inventory."));

        Item strangePotion = new Item("Strange Potion");
        strangePotion.addItemProperty(ItemProperty.DRINKABLE, new Action("You drink the strange looking potion.\n " +
                "You are growing stronger as you feel a tremendous amount of energy running through your veins."));
        strangePotion.addItemProperty(ItemProperty.TAKEABLE, new Action("You put the strange potion in your inventory."));

        Item woodenLever = new Item("Wooden Lever");
        woodenLever.addItemProperty(ItemProperty.USEABLE, new Action("As you go through the woods, you see an old wooden lever hidden behind a big tree.\n " +
                "You pull the lever and after hearing the sound of some sort of a mechanism,from inside tree's log a hidden door opens revealing a staircase to an underground tunnel."));


        Item woodenZombie = new Item("rotten zombie");
        woodenZombie.addItemProperty(ItemProperty.FIGHTABLE, new Action("kill that zombie."));
        //Add items to their respective scenes.
        initialScene.getItems().add(flower);
        cave.getItems().add(rustySword);
        caveSecretRoom.getItems().add(oldBook);
        caveSecretRoom.getItems().add(strangePotion);
        forest.getItems().add(woodenLever);
        forest.getItems().add(woodenZombie);

        //Set initial scene's neighbor scenes.
        initialScene.addNeighborScene(Direction.NORTH,cave);
        initialScene.addNeighborScene(Direction.EAST,forest);
        System.out.println("here2");
        initialScene.addNeighborScene(Direction.WEST,lake);

        //Set cave's neighbor scenes.
        cave.addNeighborScene(Direction.EAST,caveSecretRoom);
        return initialScene;





    }

    /*
    Generates character and enemies
     */
    private void generateCharacter(Scene initial){
        character = new Character("Daedalus",100,10, initial);
        GameManager.getInstance().setInitialized(true);

        Enemies zombie = new Enemies(35,5);
        Enemies skeleton = new Enemies(20,7);
        Enemies snake =new Enemies(15,4);
        new Enemies(1,1);




   /*     System.out.println("you found a skeleton do you want to fight it yes or no?"); //if yes
        while(character.getPlayerHP()>0 && skeleton.getEnemiesHP()>0) {
            System.out.println("you got hit by a skeleton HP left= " + character.getHit(skeleton.getEnemiesDamage()));
            System.out.println("you damaged the skeleton HP left= " + skeleton.getHit(character.getPlayerDamage()));

            if(skeleton.getEnemiesHP()<0){
                System.out.println("You killed the enemy.Your reward +10 Health");
                character.setplayerHP(character.getPlayerHP());
                System.out.println("Your damage="+character.getPlayerDamage());
                System.out.println("Your HP="+character.getPlayerHP());

            }else if(character.getPlayerHP()<0){
                System.out.println("you died");
            }
        }

*/
    }





    public Enemies getEnemies() {
        return enemies;
    }

    public Character getCharacter() {
        return character;
    }



    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }


}
