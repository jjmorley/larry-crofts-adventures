package nz.ac.wgtn.swen225.lc.recorder;


//import nz.ac.wgtn.swen225.lc.app.Direction;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;


class GameEvent {
    // Define your game state data here
}


public class Recorder {
    private List<GameEvent> gameHistory = new ArrayList<>();
    private List<String> moveHistory = new ArrayList<>();

    public void startRecording() {
        // Initialize recording
    }

    public void addPlayerMove(Direction d) {
        moveHistory.add(d.toString());
        System.out.println("Player Move '" + d.toString() + "' Recorded");
    }

    public void saveRecordedGameToFile(String filePath) {
        try {
           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.writeValue(new File(filePath), moveHistory);
           System.out.println("Recorded Moves Saved to: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRecordedGameFromFile(String filePath) {
        try {
        	//System.out.println("loaded");
            ObjectMapper objectMapper = new ObjectMapper();
            gameHistory = objectMapper.readValue(new File(filePath), List.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replayGameStepByStep() {
        // Implement step-by-step replay logic
    }

    public void autoReplayGame(int speed) {
        // Implement auto-replay logic with the given speed
    }

}