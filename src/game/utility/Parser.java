package game.utility;


import game.Character;
import game.Item;
import game.enums.Command;
import game.enums.Direction;
import game.enums.ItemProperty;
import game.enums.Message;
import game.Enemies;

import java.util.ArrayList;
import java.util.List;

public class Parser  {
    /*
    Performs the action that the user typed after all checks
    has been completed successfully.
     */
    public static void handleInput(String input){
        if(!GameManager.getInstance().isInitialized()){
            return;
        }
        List<Object> parsedUserInput = makeCheck(input);
        if(parsedUserInput == null){
            return;
        }
        switch (Command.valueOf(parsedUserInput.get(0).toString().replace("ABLE",""))){
            case MOVE:
                //Move the player to the selected direction.
                GameManager.getInstance().getCharacter().move((Direction) parsedUserInput.get(1));
                break;

            case FIGHT:

               // GameManager.getInstance().getEnemies().getHit(10);
               // System.out.println("Your HP="+GameManager.getInstance().skeleton.getEnemies().getEnemiesHP());
                GameManager.getInstance().getCharacter().getHit(2);
                System.out.println("Your HP="+GameManager.getInstance().getCharacter().getPlayerHP());


                break;

            case SEARCH:
                //Search the room for items.
                List<Item> sceneItems = GameManager.getInstance().getCharacter().getCurrentScene().getItems();
                if(sceneItems.isEmpty()){//If no items found.
                    ConsoleHandler.showMessage(Message.ITEM_SEARCH_NOT_FOUND.getText());
                    break;
                }
                ConsoleHandler.showMessage(Message.ITEM_SEARCH_FOUND.getText());
                for(int i = 0; i< sceneItems.size(); i++){//List all found items.
                    ConsoleHandler.showMessage(i+"."+" "+sceneItems.get(i).getName());
                }
                break;
            case DRINK:
            case EAT:
                ItemProperty itemProperty = ItemProperty.valueOf(parsedUserInput.get(0).toString());
                Item selectedItem;
                if(parsedUserInput.get(1).toString().equals("INVENTORY")){//Check whether user wants to perform action to inventory item.
                    int selectedIndex = (int)parsedUserInput.get(2);
                    selectedItem = GameManager.getInstance().getCharacter().getInventory().getItems().get(selectedIndex);//Get the selected item from inventory..
                    GameManager.getInstance().getCharacter().getInventory().removeItem(selectedItem);//Remove consumed item from inventory.
                }
                else {
                    int selectedIndex = (int)parsedUserInput.get(1);
                    selectedItem = GameManager.getInstance().getCharacter().getCurrentScene().getItems().get(selectedIndex);//Get selected item from scene.
                    GameManager.getInstance().getCharacter().getCurrentScene().getItems().remove(selectedItem);//Remove consumed item from scene.
                }
                selectedItem.getItemProperties().get(itemProperty).performAction();//Perform action of item.
                break;
            case TAKE:
                int selectedIndex = (int)parsedUserInput.get(1);
                selectedItem = GameManager.getInstance().getCharacter().getCurrentScene().getItems().get(selectedIndex);//Get selected item from scene.
                selectedItem.getItemProperties().get(ItemProperty.valueOf(parsedUserInput.get(0).toString())).performAction();//Perform action of item.
                GameManager.getInstance().getCharacter().getInventory().addItem(selectedItem);//Add item to inventory from scene.
                GameManager.getInstance().getCharacter().getCurrentScene().getItems().remove(selectedItem);//Remove item from scene.
                break;
            case USE:
            case READ:
                itemProperty = ItemProperty.valueOf(parsedUserInput.get(0).toString());
                if(parsedUserInput.get(1).toString().equals("INVENTORY")){//Check if trying to read from inventory.
                    selectedIndex = (int)parsedUserInput.get(2);
                    selectedItem = GameManager.getInstance().getCharacter().getInventory().getItems().get(selectedIndex);//Get selected item from inventory.
                }
                else {
                     selectedIndex = (int)parsedUserInput.get(1);
                     selectedItem = GameManager.getInstance().getCharacter().getCurrentScene().getItems().get(selectedIndex);//Get selected item from scene.
                }
                selectedItem.getItemProperties().get(itemProperty).performAction();//Perform action of item.
                break;
            case OPEN:
                if(parsedUserInput.get(1).toString().equals("INVENTORY")){//Check if trying to open inventory.
                    List<Item> inventoryItems = GameManager.getInstance().getCharacter().getInventory().getItems();
                    ConsoleHandler.showMessage(Message.OPEN_INVENTORY.getText());
                    for(int i = 0; i< inventoryItems.size(); i++){//List all inventory items.
                        ConsoleHandler.showMessage(i+"."+" "+inventoryItems.get(i).getName());
                    }
                }
                break;
            case DROP:
                selectedIndex = Integer.parseInt(parsedUserInput.get(1).toString());
                selectedItem = GameManager.getInstance().getCharacter().getInventory().getItems().get(selectedIndex);//Get selected item from invnetory.
                GameManager.getInstance().getCharacter().getInventory().removeItem(selectedItem);//Remove item from inventory.
                GameManager.getInstance().getCharacter().getCurrentScene().getItems().add(selectedItem);//Add item to scene.
                ConsoleHandler.showMessage(String.format(Message.ITEM_DROP.getText(), selectedItem.getName()));
                break;
            case SAVE:
                SaveManager.saveGame(parsedUserInput.get(1).toString());//Save the game.
                ConsoleHandler.showMessage(Message.SAVED_GAME.getText());
                break;
            case LOAD:
                SaveManager.loadGame(parsedUserInput.get(1).toString());//Load the game.
                ConsoleHandler.showMessage(Message.LOAD_GAME.getText());
                break;
            case SAVELIST:
                ConsoleHandler.showMessage(Message.SAVE_LIST.getText());//Show save name list.
                SaveManager.getSaveNamesList().forEach(ConsoleHandler::showMessage);
                break;
            case HELP:ConsoleHandler.showMessage(Message.HELP_MESSAGE.getText());
            break;
            case QUIT:
                ConsoleHandler.showMessage(Message.EXIT.getText());
                System.exit(0);//Exit the application.
        }
    }

    /*
    Extracts commands and parameters from user input
    and performs the required checks in every case.
    If user input successfully passes all the checks
    then it returns all the parsed commands-parameters as a list,
    if not then it returns null and shows the appropriate error message.
     */
    public static List<Object> makeCheck(String input){
        String[] arguments = input.split(" ");//Split user input using space character.
        List<Object> parsedUserInput = new ArrayList<>();//List of parsed user arguments, this will be sent to handle in order to perform the required user tasks.
        if(arguments.length < 1){//Check if input is empty.
            ConsoleHandler.showMessage(Message.INPUT_EMPTY.getText());
            return null;
        }
        try {
            parsedUserInput.add(Command.valueOf(arguments[0].toUpperCase()));//Parse first argument as command type.
        }
        catch (IllegalArgumentException iae){//In case of error.
            ConsoleHandler.showMessage(Message.INVALID_COMMAND.getText());
            return null;
        }
        switch ((Command)parsedUserInput.get(0)){
            case MOVE:
                if(!hasExactlyArgumentNumber(2, arguments)) return null;
                Direction direction = isValidDirection(arguments[1]);
                if(direction == null) return null;
                parsedUserInput.add(direction);
                break;
            case DROP:
                if(!hasExactlyArgumentNumber(2, arguments)) return null;
                Integer selectedIndex = isNumber(arguments[1]);
                if(selectedIndex == null) return null;
                parsedUserInput.add(selectedIndex);
                int itemCount = GameManager.getInstance().getCharacter().getInventory().getItems().size();
                if(itemCount == 0){//In case no items exist in the inventory.
                    ConsoleHandler.showMessage(Message.NO_DROPABLE_ITEMS.getText());
                    return null;
                }
                if(selectedIndex > itemCount-1 || selectedIndex < 0){//In case user inputs a number that does not exist.
                    ConsoleHandler.showMessage(String.format(Message.NUMBER_OUT_OF_RANGE.getText(), 0,itemCount-1));
                    return null;
                }
                break;
            case DRINK:
            case EAT:
            case USE:
            case READ:
                parsedUserInput.set(0, parsedUserInput.get(0)+"ABLE");//Add the "ABLE" at the end of user command so that it matches with item properties ex: use 0 -> useABLE
                if(arguments.length != 2 && arguments.length != 3){
                    ConsoleHandler.showMessage(String.format(Message.INVALID_ARGUMENT_NUMBER.getText(), 2, 3));
                    return null;
                }
                Item selectedItem;
                if(arguments.length == 3){
                    if(!arguments[1].equalsIgnoreCase("INVENTORY")){//second argument must be inventory.
                        ConsoleHandler.showMessage(Message.INVALID_COMMAND.getText());
                        return null;
                    }
                    parsedUserInput.add("INVENTORY");
                    selectedIndex = isNumber(arguments[2]);
                    if(selectedIndex == null) return null;
                    itemCount = GameManager.getInstance().getCharacter().getInventory().getItems().size();
                    if(itemCount == 0){
                        ConsoleHandler.showMessage(Message.NO_INTERACTABLE_ITEMS.getText());
                        return null;
                    }
                    if(selectedIndex > itemCount-1 || selectedIndex < 0){
                        ConsoleHandler.showMessage(String.format(Message.NUMBER_OUT_OF_RANGE.getText(), 0,itemCount-1));
                        return null;
                    }
                    selectedItem = GameManager.getInstance().getCharacter().getInventory().getItems().get(selectedIndex);
                }
                else{
                    selectedIndex = isNumber(arguments[1]);
                    if(selectedIndex == null) return null;
                    itemCount = GameManager.getInstance().getCharacter().getCurrentScene().getItems().size();
                    if(itemCount == 0){
                        ConsoleHandler.showMessage(Message.NO_INTERACTABLE_ITEMS.getText());
                        return null;
                    }
                    if(selectedIndex > itemCount-1 || selectedIndex < 0){
                        ConsoleHandler.showMessage(String.format(Message.NUMBER_OUT_OF_RANGE.getText(), 0,itemCount-1));
                        return null;
                    }
                    selectedItem = GameManager.getInstance().getCharacter().getCurrentScene().getItems().get(selectedIndex);
                }
                parsedUserInput.add(selectedIndex);
                if(selectedItem.getItemProperties().get(ItemProperty.valueOf(parsedUserInput.get(0).toString())) == null) {
                    ConsoleHandler.showMessage(String.format(Message.CANT_PERFORM_ACTION.getText(), parsedUserInput.get(0).toString().replace("ABLE","").toLowerCase(),selectedItem.getName()));
                    return null;
                }
                break;
            case OPEN:
                if(!hasExactlyArgumentNumber(2, arguments)) return null;
                if(!arguments[1].equalsIgnoreCase("INVENTORY")){
                    ConsoleHandler.showMessage(Message.INVALID_COMMAND.getText());
                    return null;
                }
                parsedUserInput.add("INVENTORY");
                break;
            case TAKE:
                parsedUserInput.set(0, parsedUserInput.get(0)+"ABLE");
                if(!hasExactlyArgumentNumber(2, arguments)) return null;
                selectedIndex = isNumber(arguments[1]);
                if(selectedIndex == null) return null;
                parsedUserInput.add(selectedIndex);
                itemCount = GameManager.getInstance().getCharacter().getCurrentScene().getItems().size();
                if(itemCount == 0){
                    ConsoleHandler.showMessage(Message.NO_INTERACTABLE_ITEMS.getText());
                    return null;
                }
                if(selectedIndex > itemCount-1 || selectedIndex < 0){
                    ConsoleHandler.showMessage(String.format(Message.NUMBER_OUT_OF_RANGE.getText(), 0,itemCount-1));
                    return null;
                }
                selectedItem = GameManager.getInstance().getCharacter().getCurrentScene().getItems().get(selectedIndex);
                if(selectedItem.getItemProperties().get(ItemProperty.valueOf(parsedUserInput.get(0).toString())) == null) {
                    ConsoleHandler.showMessage(String.format(Message.CANT_PERFORM_ACTION.getText(), parsedUserInput.get(0).toString().replace("ABLE","").toLowerCase(),selectedItem.getName()));
                    return null;
                }
                break;
            case LOAD:
                if(!hasExactlyArgumentNumber(2, arguments)) return null;
                if(!SaveManager.getSaveNamesList().contains(arguments[1])){
                    ConsoleHandler.showMessage(String.format(Message.LOAD_NAME_ERROR.getText(), arguments[1]));
                    return null;
                }
                parsedUserInput.add(arguments[1]);
                break;
            case SAVE:
                if(!hasExactlyArgumentNumber(2, arguments)) return null;
                parsedUserInput.add(arguments[1]);
                break;
            case HELP:
            case SAVELIST:
            case SEARCH:
            case QUIT:
                if(!hasExactlyArgumentNumber(1, arguments)) return null;
                break;
        }
        return parsedUserInput;
    }

    /*
    Checks if arguments number are the same as the number parameter.
     */
    private static boolean hasExactlyArgumentNumber(int number, String[] arguments){
        if(arguments.length != number){
            ConsoleHandler.showMessage(String.format(Message.TOO_MANY_ARGUMENTS.getText(), number));
            return false;
        }
        return true;
    }
    /*
    Checks if input is valid direction.
     */
    private static Direction isValidDirection(String input){
        try {
            return Direction.valueOf(input.toUpperCase());
        }
        catch (IllegalArgumentException iae){
            ConsoleHandler.showMessage(Message.INVALID_DIRECTION.getText());
            return null;
        }
    }
    /*
    Checks if input is valid number.
     */
    private static Integer isNumber(String input){
        try {
            return Integer.parseInt(input);
        }
        catch (IllegalArgumentException iae){
            ConsoleHandler.showMessage(Message.NO_NUMBER_ERROR.getText());
            return null;
        }
    }
}
