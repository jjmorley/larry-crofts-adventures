package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.JsonNode;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.persistency.SaveData;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static nz.ac.wgtn.swen225.lc.persistency.Load.*;
import static nz.ac.wgtn.swen225.lc.persistency.Save.autoSave;

/**
 * These tests import the level template for testing.
 * This ensures the load process worked.
 *
 * @author Seb Collis 300603371
 */

public class LoadTests {
  JsonNode json = loadAsJSON(new File("src/main/resources/levels/levels-template.json"));
  SaveData save = loadAsSaveData(new File("src/main/resources/levels/levels-template.json"));
  Tile[][] saveBoard = save.getDomain().getBoard().getBoard();
  Domain domain = save.getDomain();

  @Test
  public void JSONNodeNotEmpty() {
    assert json != null;
    assert !json.isEmpty();
  }

  @Test
  public void saveDataNotEmpty() {
    assert save != null;
    assert saveBoard.length != 0;
  }

  @Test
  public void saveDataCorrectValues() {
    assert save.getTimeRemaining() == 1000;
    assert save.getLevelNum() == 0;
  }

  @Test
  public void boardCorrectSize() {
    assert saveBoard.length != 0;
    assert saveBoard.length == 3;
  }

  @Test
  public void boardCorrectValues() {
    assert saveBoard[0][0].getName().equals("Wall");
    assert saveBoard[0][0].getPosition().equals(new Position(0, 0));

    assert saveBoard[0][2].getName().equals("Door") : saveBoard[0][2].getName();
    assert saveBoard[1][0].getName().equals("Exit") : saveBoard[1][0].getName();
    assert saveBoard[1][1].getName().equals("Free") : saveBoard[1][1].getName();
    assert saveBoard[1][2].getName().equals("ExitDoor") : saveBoard[1][2].getName();
    assert saveBoard[2][2].getName().equals("InfoTile") : saveBoard[2][2].getName();
  }

  @Test
  public void domainPlayerCorrectValues() {
    assert domain.getPlayer().getTreasuresLeft() == 1;
    assert domain.getPlayer().getPosition().equals(new Position(1, 1));
  }

  @Test
  public void domainActorCorrectValues() {
    List<Position> positionList = List.of(new Position(0, 0),
            new Position(0, 1), new Position(0, 0),
            new Position(0, 1), new Position(0, 2),
            new Position(0, 1));

    assert domain.getActors().size() == 1;
    assert domain.getActors().get(0).getRoute().containsAll(positionList);
  }

  @Test
  public void autoLoadTest() {
    //making sure the last saved level is the template
    autoSave(save);
    SaveData d = autoLoad();

    Tile[][] autoLoadBoard = d.getDomain().getBoard().getBoard();

    assert (saveBoard.length == autoLoadBoard.length);
    assert (saveBoard[0][2].getName().equals(autoLoadBoard[0][2].getName()));
    assert (saveBoard[1][2].getName().equals(autoLoadBoard[1][2].getName()));
    assert (saveBoard[2][2].getName().equals(autoLoadBoard[2][2].getName()));
    assert (d.getLevelNum() == save.getLevelNum());
    assert (d.getTimeRemaining() == save.getTimeRemaining());

  }

}
