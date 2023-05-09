package game;

import game.enums.Message;
import game.utility.ConsoleHandler;
import game.utility.GameManager;
import game.utility.Parser;
import game.utility.SaveManager;

public class Game {
    public static void main(String[] args) {
        ConsoleHandler.showMessage(Message.MAIN_MENU.getText());

        boolean isNewGame;
        boolean isLoadGame;
        int errorCount =0;
        String userInput;
        String[] splittedUserInput;
        boolean initialized = false;
        do{//While user enters something different than the two options.
            if(errorCount > 0){
                ConsoleHandler.showMessage(Message.MAIN_MENU_INVALID_INPUT.getText());
            }
            userInput = ConsoleHandler.getNextLine();
            splittedUserInput = userInput.split(" ");
            isNewGame = splittedUserInput[0].equalsIgnoreCase("new") && splittedUserInput.length ==2 && splittedUserInput[1].equalsIgnoreCase("game");
            isLoadGame = splittedUserInput[0].equalsIgnoreCase("load") && splittedUserInput.length ==2;

            if(isNewGame) {
                ConsoleHandler.showMessage(Message.INITIAL.getText());
                GameManager.getInstance().initializeGame();
                initialized = true;
            }
            else if(isLoadGame){
               initialized = SaveManager.loadGame(splittedUserInput[1]);
               if(initialized){
                   ConsoleHandler.showMessage(Message.LOAD_GAME.getText());
               }
            }
            errorCount++;
        }while (!initialized);

        while (true){//Main loop, while player does not type quit, wait for command.
            Parser.handleInput(ConsoleHandler.getNextLine());
        }
    }
}
