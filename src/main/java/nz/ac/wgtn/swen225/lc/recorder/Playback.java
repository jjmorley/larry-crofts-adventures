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
    private int frame = 0;
    private int level;
    private static int playSpeed = 0;
    private boolean playingBack = false;
    private List<String> playerMoveHistory = new ArrayList<>();
    private List<String> actorMoveHistory = new ArrayList<>();
    public Playback(File file, Game game) throws IOException{
        this.game = game;
        loadRecordedGameFromFile(file);

    }

    public void loadRecordedGameFromFile(File file) throws IOException{
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
            Map<String, Object> recordedGameData = objectMapper.readValue(file, typeReference);
            System.out.println("Loaded Recorded Game Data from: " + file);

            if (!recordedGameData.containsKey("playerMoveHistory") ||
                    !recordedGameData.containsKey("actorMoveHistory") ||
                    !recordedGameData.containsKey("level")) {
                throw new IOException("Invalid file format: Missing required fields");
            }

            playerMoveHistory = (List<String>) recordedGameData.get("playerMoveHistory");
            actorMoveHistory = (List<String>) recordedGameData.get("actorMoveHistory");
            this.level = (int) recordedGameData.get("level");
            game.startRecordingPlayback(level, this);

    }

    public void setSpeed(int speed){
        playSpeed = speed;
    }

    public void playNextFrame() {
        pause();
        // Implement step-by-step replay logic
        if(frame == playerMoveHistory.size()-1){
            frame = 0;
        }
        if (actorMoveHistory.get(frame).equals("")){
            game.movePlayer(Direction.valueOf(playerMoveHistory.get(frame)));
        } else {
           game.updateActors();
        }
        frame++;
    }

    public void pause(){
        playingBack = false;
    }

    public void play(){
        playingBack = true;
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
                if (!playingBack || frame == playerMoveHistory.size()) {
                    timer.cancel();
                    pause();
                    // return;
                }
                if (actorMoveHistory.get(frame).equals("")){
                    game.movePlayer(Direction.valueOf(playerMoveHistory.get(frame)));
                } else {
                    game.updateActors();
                }
                frame++;

            }
        };

        timer.scheduleAtFixedRate(task, 0, speed); // Initial delay of 0 milliseconds, repeat every 2000 milliseconds (2 seconds)
    }

    public List<String> getPlayerMoveHistory() {
        return playerMoveHistory;
    }

    public List<String> getActorMoveHistory() {
        return actorMoveHistory;
    }

    public int getLevel() {
        return level;
    }
}
