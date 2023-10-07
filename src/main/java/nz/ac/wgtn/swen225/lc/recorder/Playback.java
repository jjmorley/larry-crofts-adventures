package nz.ac.wgtn.swen225.lc.recorder;
//import nz.ac.wgtn.swen225.lc.app.Direction;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.app.Game;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Playback {
    private Game game;
    private int level;
    private int frame = 0;
    private static int playSpeed = 0;
    private static int previousSpeed = 0;
    private boolean playingBack = false;
    private List<String> playerMoveHistory = new ArrayList<>();
    private List<String> actorMoveHistory = new ArrayList<>();
    public Playback(File file, Game game){
        loadRecordedGameFromFile(file);
        this.game = game;
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
            game.startRecordingPlayback(level, this);
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
           game.updateActors();
        }
        frame++;
    }

    public void pause(){
        playingBack = false;
        previousSpeed = playSpeed;
        setSpeed(0);
    }

    public void play(){
        playingBack = true;
        if(previousSpeed == 0){
            setSpeed(4000);
        } else {
            setSpeed(previousSpeed);
        }
        autoReplayGame(playSpeed);
    }

    public void cancelPlayback() {
        playingBack = false;
    }
    public void autoReplayGame(int speed) {
        Timer timer = new Timer();
        playingBack = true;
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (actorMoveHistory.get(frame).equals("")){
                    game.movePlayer(Direction.valueOf(playerMoveHistory.get(frame)));
                } else {
                    game.updateActors();
                }
                frame++;
                if (!playingBack) {
                    timer.cancel();
                   // return;
                }
            }
        };

        timer.scheduleAtFixedRate(task, 0, speed); // Initial delay of 0 milliseconds, repeat every 2000 milliseconds (2 seconds)
    }
}
