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


public class Recorder {
    //private static List<GameEvent> gameHistory = new ArrayList<>();
    private List<String> playerMoveHistory = new ArrayList<>();
    private List<String> actorMoveHistory = new ArrayList<>();
    private int level;
    private Game game;

    public Recorder(int level, Game game) {
        this.level = level;
        this.game = game;
        // Initialize recording
    }

    public void addPlayerMove(Direction d) {
        playerMoveHistory.add(d.toString());
        actorMoveHistory.add("");//delete if actor and player can move at same time
        System.out.println("Player Move '" + d.toString() + "' Recorded");
    }
    public void addActorMove(Direction d) {
        actorMoveHistory.add(d.toString());
        playerMoveHistory.add("");//delete if actor and player can move at same time
        System.out.println("Player Move '" + d.toString() + "' Recorded");
    }


    public void saveRecordedGameToFile(File file) {
        try {
            Map<String, Object> recordedGameData = new HashMap<>();
            recordedGameData.put("playerMoveHistory", playerMoveHistory);
            recordedGameData.put("actorMoveHistory", actorMoveHistory);
            recordedGameData.put("level", level);

           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.writeValue(file, recordedGameData);
           System.out.println("Recorded Moves Saved to: " + file.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replayGameStepByStep() {
        for (String move : playerMoveHistory){
            System.out.println("Replaying move: " + move);

        }
        // Implement step-by-step replay logic
    }

    public void autoReplayGame(int speed) {
        // Implement auto-replay logic with the given speed
    }

}