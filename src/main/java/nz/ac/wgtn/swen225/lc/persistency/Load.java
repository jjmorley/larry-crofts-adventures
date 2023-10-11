package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Comparator;

import com.fasterxml.jackson.databind.node.ArrayNode;
import nz.ac.wgtn.swen225.lc.domain.*;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Treasure;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.*;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.*;



/**
 * Loads in level from JSON file.
 *
 * @author Seb Collis 300603371
 */
public class Load {

  private static JsonNode json;

  /**
   * Loads JSON file from a given filepath.
   */
  public static void loadJSON(File file) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      json = objectMapper.readTree(file);
    } catch (IOException e) {
      System.out.println("Something messed up. \n" + e.toString());
    }
  }

  /**
   * Loads level based upon whatever has been last saved.
   *
   * @return Domain
   */
  public static SaveData autoLoad() {
    Path dir = Paths.get("saves/");  // specify directory
    try {
      File filePath = Files.list(dir)
              .filter(f -> !Files.isDirectory(f))
              .max(Comparator.comparingLong(f -> f.toFile().lastModified()))
              .orElse(Paths.get("src/main/resources/levels/level1.json")).toFile(); //load level 1 by default

      if (filePath != null) { //this should not ever be false - would throw error otherwise
        return loadAsSaveData(filePath);
      }
    } catch (Exception e) {
      System.out.println("Something messed up. \n" + e.toString());
    }
    return null;
  }

  /**
   * Return loaded JSON as a SaveData object.
   *
   * @return SaveData
   */
  public static SaveData loadAsSaveData(File file) {
    Domain d = loadAsDomain(file);
    int levelNum = json.get("levelNum").asInt();
    int timeRemaining = json.get("time").asInt();

    return new SaveData(d, levelNum, timeRemaining);
  }

  /**
   * Return loaded JSON as a domain object.
   *
   * @return Domain
   */
  public static Domain loadAsDomain(File file) {
    //JSON LOADING
    loadJSON(file);
    JsonNode level = json.get("level");
    int gridSize = json.get("gridSize").asInt();
    int treasures = json.get("treasures").asInt();
    JsonNode actors = json.get("actor");

    //MAKE ARRAY
    Tile[][] levelArray = new Tile[gridSize][gridSize];
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        String s = level.get(i + ", " + j).asText();
        Tile tile;
        switch (s) {
          case ("f"):
            tile = new Free(null, new Position(i, j));
            break;
          case ("w"):
            tile = new Wall(new Position(i, j));
            break;
          case ("l"):
            tile = new ExitDoor(new Position(i, j));
            break;
          case ("e"):
            tile = new Exit(null, new Position(i, j));
            break;
          case ("i"):
            tile = new InfoTile(null, new Position(i, j), "hasn't been set yet");
            break;
          case ("t"):
            tile = new Free(new Treasure(new Position(i, j)), new Position(i, j));
            break;
          case ("v"):
            tile = new Lava(null, new Position(i, j));
            break;
          case ("a"):
            tile = new Water(null, new Position(i, j));
            break;
          default:
            if (s.matches("[d][,][ ][a-z]")) {
              Key k = new Key(s.charAt(3), new Position(0, 0));
              tile = new Door(k, new Position(i, j));
            } else if (s.matches("[k][,][ ][a-z]")) {
              Key k = new Key(s.charAt(3), new Position(i, j));
              tile = new Free(k, new Position(i, j));
            } else {
              tile = new Free(null, new Position(i, j));
            }
        }
        levelArray[i][j] = tile;
      }
    }

    ArrayList<Actor> boardActors = new ArrayList<Actor>();
    for (int i = 0; i < actors.size(); i++) {
      String actorPos = actors.get(i + "").asText();
      ArrayList<Position> actorList = new ArrayList<Position>();
      for (int j = 0; j < actorPos.length(); j += 6) {
        int x = (int) actorPos.charAt(j) - 48;
        int y = (int) actorPos.charAt(j + 3) - 48;
        actorList.add(new Position(x, y));
      }
      Actor a = new Actor(actorList);
      boardActors.add(a);
    }

    ArrayList<Item> inv = new ArrayList<Item>();
    if (json.get("inventory") != null) {
      ArrayNode items = json.get("inventory").withArray("");

      for (int i = 0; i < items.size(); i++) {
        JsonNode item = items.get(i);
        if (item.get("name").asText().equals("Key")) {
          inv.add(new Key(item.get("key").asInt(),
                  new Position(item.get("position").get("x").asInt(),
                          item.get("position").get("y").asInt())));
        }
      }
    }

    Board b = new Board(levelArray, true);

    Player p = new Player(playerPos(), inv, treasures);

    return new Domain(b, p, boardActors);
  }

  /**
   * Return character's position. This is -48 because it's otherwise the ascii values
   *
   * @return Position
   */
  public static Position playerPos() {
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
  public static JsonNode loadAsJSON(File file) {
    loadJSON(file);

    return json;
  }

}
