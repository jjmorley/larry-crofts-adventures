package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Treasure;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.*;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * Loads in level from JSON file.
 *
 * TODO: Time remaining
 *
 * @author Seb Collis 300603371
 */
public class Load {

    private static JsonNode json;

    /**
     * Loads JSON file from a given filepath.
     *
     */
    public static void loadJSON(File file){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.readTree(file);
        }
        catch (IOException e){
            System.out.println("Something messed up. \n" + e.toString());
        }
    }

    /**
     * Loads level based upon whatever has been last saved.
     *
     * @return Domain
     */
    public static Domain autoLoad(){
        Path dir = Paths.get("src/main/resources/levels");  // specify directory
        try{
            File filePath = Files.list(dir)
                    .filter(f -> !Files.isDirectory(f))
                    .max(Comparator.comparingLong(f -> f.toFile().lastModified()))
                    .orElseGet(null).toFile();

            if (filePath != null) //this should not ever be false - would throw error otherwise
            {
                return loadAsDomain(filePath);
            }
        }
        catch(Exception e){
            System.out.println("Something messed up. \n" + e.toString());
        }
        return null;
    }

    /**
     * Return loaded JSON as an array. This is a 2D array ATM but am happy to change it.
     *
     * @return String[][]
     */
    public static String[][] loadAsArray(File file){
        //JSON LOADING
        loadJSON(file);
        JsonNode level = json.get("level");
        int gridSize = json.get("gridSize").asInt();

        //MAKE ARRAY
        String[][] levelArray = new String[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j <  gridSize; j++){
                levelArray[i][j] = level.get(i + ", " + j).asText();
            }
        }
        return levelArray;
    }

    /**
     * Return loaded JSON as a domain object.
     *
     * TODO: MULTIPLE ACTORS
     *
     * @return Domain
     */
    public static Domain loadAsDomain(File file){
        //JSON LOADING
        loadJSON(file);
        JsonNode level = json.get("level");
        int gridSize = json.get("gridSize").asInt();
        int treasures = json.get("treasures").asInt();
        String actorPos = json.get("actor").asText();

        //MAKE ARRAY
        Tile[][] levelArray = new Tile[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j <  gridSize; j++){
                String s = level.get(i + ", " + j).asText();
                Tile tile;
                switch(s){
                    case("f"):
                        tile = new Free(null, new Position(i, j));
                        break;
                    case("w"):
                        tile = new Wall(new Position(i, j));
                        break;
                    case("l"):
                        tile = new ExitDoor(new Position(i, j));
                        break;
                    case("e"):
                        tile = new Exit(null, new Position(i, j));
                        break;
                    case("i"):
                        tile = new InfoTile(null, new Position(i, j), "hasn't been set yet");
                        break;
                    case("t"):
                        tile = new Free(new Treasure(new Position(i, j)), new Position(i, j));
                        break;
                    default:
                        if (s.matches("[d][,][ ][a-z]")){
                            Key k = new Key(s.charAt(3), new Position(0 ,0));
                            tile = new Door(k, new Position(i, j));
                        }
                        else if (s.matches("[k][,][ ][a-z]")){
                            Key k = new Key(s.charAt(3), new Position(i, j));
                            tile = new Free(k, new Position(i, j));
                        }
                        else {
                            tile = new Free(null, new Position(i, j));
                        }
                }
                levelArray[i][j] = tile;
            }
        }

        //need to break this into its own thing
        ArrayList<Position> actorList = new ArrayList<Position>();
        for (int i = 0; i < actorPos.length(); i += 4){
            int x = Integer.valueOf(actorPos.charAt(i)) - 48;
            int y = Integer.valueOf(actorPos.charAt(i + 2)) - 48;
            actorList.add(new Position(x, y));
        }
        Actor a = new Actor(actorList);
        ArrayList<Actor> actors = new ArrayList<Actor>();
        actors.add(a);

        Board b = new Board(levelArray, true);

        Player p = new Player(playerPos(), new ArrayList<Item>(), treasures);

        Domain d = new Domain(b, p, actors);

        return d;
    }

    /**
     * Return character's position. This is -48 because it's otherwise the ascii values
     *
     * @return Position
     */
    public static Position playerPos(){
        String s = json.get("char").asText();
        int x = Integer.valueOf(s.charAt(0)) - 48;
        int y = Integer.valueOf(s.charAt(3)) - 48;

        return new Position(x, y);
    }

    /**
     * Return loaded JSON as JSONNode. This is for testing purposes.
     *
     * @return JsonNode
     */
    public static JsonNode loadAsJSON(File file){
        loadJSON(file);

        return json;
    }

}
