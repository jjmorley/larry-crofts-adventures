package nz.ac.wgtn.swen225.lc.recorder;


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

/**
 * This class takes care of recording a game to a file.
 *
 * @author Nate Burt 300605172.
 */
public class Recorder {
    //private static List<GameEvent> gameHistory = new ArrayList<>();
    private List<String> playerMoveHistory = new ArrayList<>();
    private List<String> actorMoveHistory = new ArrayList<>();
    private int level;
    private Game game;

    /**
     * Creates a new instance of recorder.
     *
     * @param level specified game level being played
     * @param game  current game instance
     */
    public Recorder(int level, Game game) {
        this.level = level;
        this.game = game;
    }

    /**
     * Adds player move to history and empty move to actor history.
     *
     * @param d Direction of player move.
     */
    public void addPlayerMove(Direction d) {
        playerMoveHistory.add(d.toString());
        actorMoveHistory.add("");
        System.out.println("Player Move '" + d.toString() + "' Recorded");
    }

    /**
     * Records actor move
     */
    public void addActorMove() {
        actorMoveHistory.add("ActorMove");
        playerMoveHistory.add("");
    }

    /**
     * Method of saving recorded game to file
     *
     * @param file File to save recorded game to/
     */
    public void saveRecordedGameToFile(File file) {
        try {
            //Put histories and game level
            Map<String, Object> recordedGameData = new HashMap<>();
            recordedGameData.put("playerMoveHistory", playerMoveHistory);
            recordedGameData.put("actorMoveHistory", actorMoveHistory);
            recordedGameData.put("level", level);

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(file, recordedGameData);
            System.out.println("Recorded Moves Saved to: " + file.toString());

        } catch (IOException e) {
            System.out.println("Unable to save file. \n" + e.toString());
        }
    }

    /**
     * Getter for player move history.
     *
     * @return player movements
     */
    public List<String> getPlayerMoveHistory() {
        return playerMoveHistory;
    }

    /**
     * Getter for actor move history.
     *
     * @return actors moving
     */
    public List<String> getActorMoveHistory() {
        return actorMoveHistory;
    }

    /**
     * Getter for recorded game level.
     *
     * @return level of the game
     */
    public int getLevel() {
        return level;
    }

}