package game.utility;

import game.enums.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SaveManager {
    /*
    Saves the game in the save folder.
     */
    public static void saveGame(String saveName){
        try {
            File directory = new File("saves");
            if (! directory.exists()){
                directory.mkdir();
            }
            FileOutputStream fileOutputStream
                    = new FileOutputStream("saves/"+saveName+".savegame");
            ObjectOutputStream objectOutputStream
                    = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(GameManager.getInstance());
            objectOutputStream.flush();
            objectOutputStream.close();
        }
        catch (IOException ioException){
            ConsoleHandler.showMessage(Message.SAVE_ERROR.getText());
        }
    }
    /*
    Gets all the save game names.
     */
    public static List<String> getSaveNamesList(){
        File dir = new File("saves");
        File [] files = dir.listFiles((dir1, name) -> name.endsWith(".savegame"));
        if(files != null) {
            return Arrays.stream(files).map(file -> file.getName().replace(".savegame", "")).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    /*
    Loads the specified save game.
     */
    public static boolean loadGame(String saveName){
        try {

            FileInputStream fileInputStream
                    = new FileInputStream("saves/"+saveName+".savegame");
            ObjectInputStream objectInputStream
                    = new ObjectInputStream(fileInputStream);
            GameManager.setInstance((GameManager) objectInputStream.readObject());
            objectInputStream.close();
            return true;
        }
        catch (IOException | ClassNotFoundException ioException){
            ConsoleHandler.showMessage(Message.LOAD_ERROR.getText());
            return false;
        }
    }
}
