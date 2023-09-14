package main.java.nz.ac.wgtn.swen225.lc.recorder;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

class GameEvent {
    // Define your game state data here
}

class GameRecorder {
    private List<GameEvent> gameHistory = new ArrayList<>();

    public void startRecording() {
        // Initialize recording
    }

    public void recordGameState(GameEvent gameEvent) {
        gameHistory.add(gameEvent);
    }

    public void saveRecordedGameToFile(String filePath) {
        try {
           ObjectMapper objectMapper = new ObjectMapper();
           objectMapper.writeValue(new File(filePath), gameHistory);
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