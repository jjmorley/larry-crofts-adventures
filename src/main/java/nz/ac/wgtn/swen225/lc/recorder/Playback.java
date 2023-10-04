package nz.ac.wgtn.swen225.lc.recorder;
import nz.ac.wgtn.swen225.lc.app.Direction;
import nz.ac.wgtn.swen225.lc.app.Game;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Playback {
    private Game game;
    private int level;
    private static int playSpeed;
    private static int previousSpeed = 0;
    private int frame = 0;
    private List<String> playerMoveHistory = new ArrayList<>();
    private List<String> actorMoveHistory = new ArrayList<>();
    public Playback(File file, Game game){
        loadRecordedGameFromFile(file);
        game.startRecording(level);
    }
    public void loadRecordedGameFromFile(File file) {
        //TODO throw exception if failure to meet format
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
            Map<String, Object> recordedGameData = objectMapper.readValue(file, typeReference);
            System.out.println("Loaded Recorded Game Data from: " + file);
            playerMoveHistory = (List<String>) recordedGameData.get("playerMoveHistory");
            actorMoveHistory = (List<String>) recordedGameData.get("actorMoveHistory");
            level = (int) recordedGameData.get("level");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSpeed(int speed){
        playSpeed = speed;
    }

    public void playNextFrame() {
        pause();
        // Implement step-by-step replay logic
        if (actorMoveHistory.get(frame).equals("")){
            game.movePlayer(Direction.valueOf(playerMoveHistory.get(frame)));
        } else {
            game.updateActor(Direction.valueOf(playerMoveHistory.get(frame)));
        }
        frame++;
    }

    public void pause(){
        previousSpeed = playSpeed;
        setSpeed(0);
    }

    public void play(){
        if(previousSpeed == 0){
            setSpeed(1000);
        } else {
            setSpeed(previousSpeed);
        }
        autoReplayGame(playSpeed);
    }
    public void autoReplayGame(int speed) {
        // Implement auto-replay logic with the given speed
        while(playSpeed > 0){
            if (actorMoveHistory.get(frame).equals("")){
                game.movePlayer(Direction.valueOf(playerMoveHistory.get(frame)));
            } else {
                game.updateActor(Direction.valueOf(playerMoveHistory.get(frame)));
            }
            frame++;
            try {
                Thread.sleep(playSpeed); // Sleep to control replay speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
