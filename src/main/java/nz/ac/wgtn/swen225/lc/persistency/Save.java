package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Treasure;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Calendar;

public class Save {

    /**
     * Writes current state of level to JSON.
     *
     */

    public static void autoSave(SaveData s) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        File f = new File(currentTime.toString());

        saveJsonFromSaveData(f, s);
    }

    public static void saveJsonFromSaveData(File f, SaveData s){
        ObjectMapper mapper = new ObjectMapper();

        ObjectNode json = saveJSONFromDomain(s.getDomain(), mapper);

        json.putPOJO("level", s.getLevelNum());
        json.putPOJO("time", s.getTimeRemaining());

        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try {
            writer.writeValue(f, json);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectNode saveJSONFromDomain(Domain d, ObjectMapper m) {
        ObjectNode json = m.createObjectNode();

        Tile[][] board = d.getBoard().getBoard();
        int gridSize = board.length;
        int treasures = 0;

        //add the array representing the level to the JSON
        //FYI: this is deliberately an ObjectNode, not an ArrayNode.
        ObjectNode array = m.createObjectNode();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Tile t = board[i][j];
                if (t instanceof WalkableTile) {
                    GameObject g = ((WalkableTile) t).getGameObject();
                    if (g != null) {
                        array.putPOJO(i + ", " + j, g.getName().toLowerCase().charAt(0));
                        if (g instanceof Treasure) {
                            treasures++;
                        }
                    } else {
                        array.putPOJO(i + ", " + j, t.getName().toLowerCase().charAt(0));
                    }
                } else {
                    array.putPOJO(i + ", " + j, t.getName().toLowerCase().charAt(0));
                }
            }
        }
        json.putPOJO("level", array);

        //Add character position  and grid size to JSON
        json.putPOJO("char", d.getPlayer().getPosition().x() + ", " + d.getPlayer().getPosition().y());
        json.putPOJO("gridSize", gridSize);
        json.putPOJO("treasures", treasures);

        List<Actor> a = d.getActors();
        for (Actor act : a) {
            StringBuilder path = new StringBuilder();
            for (Position p : act.getRoute()) {
                path.append(p.x()).append(", ").append(p.y()).append(",");
            }
            json.putPOJO("actor", path.toString());
        }

        return json;
    }
}
