package nz.ac.wgtn.swen225.lc.recorder;


//import nz.ac.wgtn.swen225.lc.app.Direction;
import com.fasterxml.jackson.core.type.TypeReference;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;


//class GameEvent {
//    // Define your game state data here
//    private static int turn = 0;
//    private String move = "";
//    private String itemCollected = "";
//    private String actor = "";
//    private String player = "";
//
//    //game event for actor movement
//    public GameEvent(Actor actor, Direction direction){
//        turn++;
//        this.actor = actor.toString();
//        this.move = direction.toString();
//    }
//    //game event for player movement
//    public GameEvent(Player player, Direction direction) {
//        turn++;
//        this.player = player.toString();
//        this.move = direction.toString();
//
//    }
//    //game event for player item collection
//    public GameEvent(Player player, Item item){
//        turn++;
//        this.player = player.toString();
//        this.itemCollected = item.toString();
//    }
//    //game event for player attack
//    public GameEvent(Player player, Actor actor){
//        turn++;
//        this.player = player.toString();
//        this.actor = actor.toString();
//    }
//
//    public int turn(){
//        return this.turn;
//    }
//    public String move(){
//        return this.move;
//    }
//    public String item(){
//        return this.itemCollected;
//    }
//    public String player(){
//        return this.player;
//    }
//    public String actor(){
//        return this.actor;
//    }
//}

public class Recorder {
    //private static List<GameEvent> gameHistory = new ArrayList<>();
    private List<String> moveHistory = new ArrayList<>();
    private int level;
    private Game game;

    public Recorder(int level, Game game) {
        this.level = level;
        this.game = game;
        // Initialize recording
    }

    public void addPlayerMove(Direction d) {
        moveHistory.add(d.toString());
        System.out.println("Player Move '" + d.toString() + "' Recorded");
    }

//    public static void addMoveEvent(Player chap, Direction d) {
//        gameHistory.add(new GameEvent(chap,d));
//        System.out.println("Player Move '" + d.toString() + "' Recorded");
//    }
//
//    public static void addMoveEvent(Actor actor, Direction d) {
//        gameHistory.add(new GameEvent(actor,d));
//        System.out.println("Actor Move '" + d.toString() + "' Recorded");
//    }
//    public static void addCollectEvent(Player chap, Item item) {
//        gameHistory.add(new GameEvent(chap,item));
//    }
//
//    public static void addAttackEvent(Player chap, Actor actor){
//        gameHistory.add(new GameEvent(chap,actor));
//    }

    public void saveRecordedGameToFile(String filePath) {
        try {
            Map<String, Object> recordedGameData = new HashMap<>();
            recordedGameData.put("moveHistory", moveHistory);
            recordedGameData.put("level", level);

           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.writeValue(new File(filePath), recordedGameData);
           System.out.println("Recorded Moves Saved to: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replayGameStepByStep() {
        for (String move : moveHistory){
            System.out.println("Replaying move: " + move);

        }
        // Implement step-by-step replay logic
    }

    public void autoReplayGame(int speed) {
        // Implement auto-replay logic with the given speed
    }

}