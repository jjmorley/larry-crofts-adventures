package nz.ac.wgtn.swen225.lc.recorder;
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
            ///return null; // Handle the error appropriately (e.g., return a default value or throw an exception)
        }
    }
}
