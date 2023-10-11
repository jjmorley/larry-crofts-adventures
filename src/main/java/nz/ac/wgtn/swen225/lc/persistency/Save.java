package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Door;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.ExitDoor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.Lava;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.WalkableTile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.Water;

/**
 * Saves level info as JSON.
 *
 * @author Seb Collis 300603371
 */
public class Save {


  /**
   * Autosaves to a JSON titled with the hash of the current time.
   */
  public static void autoSave(SaveData s) {
    File tempFile = new File("saves/");

    if (!tempFile.isDirectory()) {
      new File("saves/").mkdirs();
    }
    File f = new File("saves/"
            + java.time.LocalDateTime.now().hashCode() + ".json");
    saveJsonFromSaveData(f, s);
  }

  /**
   * Saves a JSON file from a SaveData packet.
   */
  public static void saveJsonFromSaveData(File f, SaveData s) {
    ObjectMapper mapper = new ObjectMapper();

    ObjectNode json = saveObjectFromDomain(s.getDomain(), mapper);

    json.putPOJO("levelNum", s.getLevelNum());
    json.putPOJO("time", s.getTimeRemaining());

    ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
    try {
      writer.writeValue(f, json);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Saves ObjectNode from Domain, for JSON loading
   * This will not be a complete JSON without other ObjectNodes.
   *
   * @return ObjectNode
   */
  public static ObjectNode saveObjectFromDomain(Domain d, ObjectMapper m) {
    ObjectNode json = m.createObjectNode();

    Tile[][] board = d.getBoard().getBoard();
    int gridSize = board.length;

    //add the array representing the level to the JSON
    //FYI: this is deliberately an ObjectNode, not an ArrayNode.
    ObjectNode array = m.createObjectNode();
    for (int i = 0; i < gridSize; i++) {
      for (int j = 0; j < gridSize; j++) {
        Tile t = board[i][j];
        if (t instanceof WalkableTile) {
          GameObject g = ((WalkableTile) t).getGameObject();
          if (g != null) {
            if (g instanceof Key) {
              array.putPOJO(i + ", " + j,
                      g.getName().toLowerCase().charAt(0) + ", "
                              + (char) ((Key) g).getKey()
              );
            } else {
              if (t instanceof Lava) {
                array.putPOJO(i + ", " + j, "v");
              } else if (t instanceof Water) {
                array.putPOJO(i + ", " + j, "a");
              } else {
                array.putPOJO(i + ", " + j, g.getName().toLowerCase().charAt(0));
              }
            }
          } else {
            array.putPOJO(i + ", " + j, t.getName().toLowerCase().charAt(0));
          }
        } else {
          if (t instanceof Door) {
            array.putPOJO(i + ", " + j,
                    t.getName().toLowerCase().charAt(0) + ", "
                            + (char) ((Door) t).getKeyID());
          } else if (t instanceof ExitDoor) {
            array.putPOJO(i + ", " + j, "l");
          } else {
            array.putPOJO(i + ", " + j, t.getName().toLowerCase().charAt(0));
          }
        }
      }
    }
    json.putPOJO("level", array);

    //Add character position  and grid size to JSON
    json.putPOJO("char", d.getPlayer().getPosition().x() + ", " + d.getPlayer().getPosition().y());
    json.putPOJO("gridSize", gridSize);
    json.putPOJO("treasures", d.getPlayer().getTreasuresLeft());
    json.putPOJO("inventory", d.getPlayer().getInventory());

    List<Actor> a = d.getActors();
    ObjectNode actors = m.createObjectNode();
    int actorNum = 0;
    for (Actor act : a) {

      StringBuilder pathBuilder = new StringBuilder();
      for (Position p : act.getRoute()) {
        pathBuilder.append(p.x()).append(", ").append(p.y()).append(", ");
      }
      pathBuilder.delete(pathBuilder.length() - 2, pathBuilder.length());
      String path = pathBuilder.toString();
      actors.putPOJO(actorNum + "", path);
      actorNum++;
    }
    json.putPOJO("actor", actors);

    return json;
  }
}
