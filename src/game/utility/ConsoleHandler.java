package game.utility;

import java.util.Scanner;

public class ConsoleHandler {
    private static final Scanner scanner = new Scanner(System.in);
    /*
    Gets the next line of user input.
     */
    public static String getNextLine(){
        return scanner.nextLine();
    }

    /*
    Shows message to console.
     */
    public static void showMessage(String message){
        System.out.println(message);
    }
}
