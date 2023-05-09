package game;

import game.enums.Direction;

import java.io.Serializable;
import java.util.*;

public class Scene implements Serializable {
    private String name;
    private final String enterMessage;
    private List<Item> items;

    private final Map<Direction,Scene> neighborScenes;

    public Scene(String name,String enterMessage) {
        this.name = name;
        this.enterMessage = enterMessage;
        neighborScenes = new HashMap<>();
        items = new ArrayList<>();
        //Initialize all directions of the scene.
        for(Direction direction : Direction.values()){
            neighborScenes.put(direction,null);
        }
    }


    /**
     * Adds a new scene in the dictionary in the selected direction.
     * @param direction The direction to set the neighbor scene.
     * @param scene The scene to be set.
     */
    public void addNeighborScene(Direction direction, Scene scene){
        if(neighborScenes.get(direction) == null) {
            neighborScenes.replace(direction, scene);//Set scene as neighbor in the selected direction.
            scene.addNeighborScene(direction.getOppositeDirection(),this);//And also set this scene as neighbor to the other scene.
        }
    }

    public Scene getNeighborScene(Direction direction){
        return neighborScenes.get(direction);
    }
    public List<Item> getItems() {
        return items;
    }
    public String getEnterMessage() {
        return enterMessage;
    }
}
