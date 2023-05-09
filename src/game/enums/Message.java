package game.enums;

public enum Message {
    MAIN_MENU("Welcome to text adventure!\nPlease type \"new game\" in order to start a new game or \"load <save name>\" in order to load a previously saved game."),
    MAIN_MENU_INVALID_INPUT("Invalid input, please type \"new game'\" in order to start a new game or \"load <save name>\" in order to load a previously saved game."),
    INITIAL("Your adventure started, please type \"help\" for the list of available commands"),
    INVALID_COMMAND("Invalid command, please type 'help' for a list of available commands."),
    INPUT_EMPTY("Please enter a command."),
    EXIT("Exiting game..."),
    DEAD_END("This direction leads to a dead-end, please chose another one."),
    TOO_MANY_ARGUMENTS("The command you typed requires %s arguments."),
    INVALID_DIRECTION("The direction you typed doesn't exist, try one of the following: north, south, east, west, northeast, nortwest, southeast, southwest."),
    ITEM_SEARCH_FOUND("After some time of searching you find these items:"),
    NO_NUMBER_ERROR("Invalid input, the second argument must be a number."),
    NUMBER_OUT_OF_RANGE("Invalid input, the second argument must be a number from %s to %s."),
    NO_INTERACTABLE_ITEMS("There are no interactable items."),
    NO_DROPABLE_ITEMS("Inventory is empty so there are no items to drop."),
    CANT_PERFORM_ACTION("You can't %s %s try another action."),
    OPEN_INVENTORY("Your inventory contains these items:"),
    ITEM_SEARCH_NOT_FOUND("After some time of searching, you found nothing."),
    ITEM_DROP("You dropped %s."),
    INVALID_ARGUMENT_NUMBER("The command you typed can have in total %s or %s arguments."),
    SAVE_LIST("The following saves exist:"),
    SAVE_ERROR("An error occurred while saving the game."),
    LOAD_NAME_ERROR("Load with name %s does not exist, please choose a different one."),
    LOAD_ERROR("An error occurred while loading the game."),
    SAVED_GAME("Game saved successfully."),
    LOAD_GAME("Game loaded successfully."),
    HELP_MESSAGE("The available commands are:\n" +
            "move <direction> -> To move between scenes.\n" +
            "search -> To search the scene for items.\n" +
            "<action> <item number> -> To perform the selected action to an item in the scene (available actions: drink, eat, use, take, read).\n" +
            "<action> inventory <item number> -> To perform the selected action to an item in the inventory (available actions: drink, eat, use, take, read).\n" +
            "open inventory -> To show the items that are currently in the inventory.\n" +
            "drop <item number> -> To drop an item from the inventory.\n" +
            "savelist -> To show the list of available saves.\n" +
            "save <name> -> To save the game with the specific name.\n" +
            "load <name> -> To load the game from a specific previously saved game.\n\n" +
            "All commands are case insensitive which means that OPEN INVENTORY, oPeN INventORy, open inventory are all valid \n" +
            "the only case sensitive input is the <save> parameter of save and load commands." );

    private String text;
    Message(String text){
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
