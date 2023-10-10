package nz.ac.wgtn.swen225.lc.domain;

import javafx.geometry.Pos;
import nz.ac.wgtn.swen225.lc.domain.Board;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.Position;
import nz.ac.wgtn.swen225.lc.domain.gameObject.GameObject;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Actor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Direction;
import nz.ac.wgtn.swen225.lc.domain.gameObject.Moveable.Player;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Treasure;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Door;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.ExitDoor;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Tile;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.Wall;
import nz.ac.wgtn.swen225.lc.domain.gameObject.tile.walkableTile.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * The type Domain test.
 *
 * @author Alexander_Galloway 300611406.
 */
public class DomainTest {

  /**
   * Test 1.
   */
  @Test
  public void testWin1() {
    int treasures = 0;

    List<GameObject> items = new ArrayList<>(){};
    items.add(new Treasure(new Position(4, 2))); treasures++;
    items.add(new Treasure(new Position(4, 6))); treasures++;
    items.add(new Key(0, new Position(3, 2)));

    Player player = new Player(new Position(4, 4), new ArrayList<Item>(), treasures);
    Tile[][] tileArr = makeBoard();

    tileArr[player.getPosition().x()][player.getPosition().y()] = new Free(player, player.getPosition());
    tileArr[5][2] = new Door((Key) items.get(2), player.getPosition());

    tileArr = addItemsToBoard(items, tileArr);
    Board board = new Board(tileArr, true);
    Domain domain = new Domain(board, player, null);

    domain.movePlayer(Direction.LEFT);
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures 1";
    assert domain.getPlayer().getPosition().equals(new Position(4, 3)) : "PlayerPos 1";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory 1";

    domain.movePlayer(Direction.LEFT);
    assert domain.getPlayer().getTreasuresLeft() == 1 : "treasures 2";
    assert domain.getPlayer().getPosition().equals(new Position(4, 2)) : "PlayerPos 2";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory 2";

    domain.movePlayer(Direction.LEFT);
    assert domain.getPlayer().getTreasuresLeft() == 1 : "treasures 3";
    assert domain.getPlayer().getPosition().equals(new Position(4, 2)) : "PlayerPos 3";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory 3";

    domain.movePlayer(Direction.DOWN);
    assert domain.getPlayer().getTreasuresLeft() == 1 : "treasures 4";
    assert domain.getPlayer().getPosition().equals(new Position(4, 2)) : "PlayerPos 4";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory 4";

    domain.movePlayer(Direction.UP);
    assert domain.getPlayer().getTreasuresLeft() == 1 : "treasures 5";
    assert domain.getPlayer().getPosition().equals(new Position(3, 2)) : "PlayerPos 5";
    assert domain.getPlayer().getInventory().size() == 1 : "Inventory 5";


    domain.movePlayer(Direction.DOWN);
    assert domain.getPlayer().getTreasuresLeft() == 1 : "treasures 6";
    assert domain.getPlayer().getPosition().equals(new Position(4, 2)) : "PlayerPos 6";
    assert domain.getPlayer().getInventory().size() == 1 : "Inventory 6";

    domain.movePlayer(Direction.DOWN);
    assert domain.getPlayer().getTreasuresLeft() == 1 : "treasures 7";
    assert domain.getPlayer().getPosition().equals(new Position(5, 2)) : "PlayerPos 7";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory 7";

    for (int i = 1; i <= 4; i++) {
      domain.movePlayer(Direction.RIGHT);
      assert domain.getPlayer().getTreasuresLeft() == 1 : "treasures";
      assert domain.getPlayer().getPosition().equals(new Position(5, 2+i)) : "PlayerPos";
      assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
    }

    assert domain.movePlayer(Direction.UP).hasPlayerMoved();
    assert domain.getPlayer().getTreasuresLeft() == 0 : "treasures 8";
    assert domain.getPlayer().getPosition().equals(new Position(4, 6)) : "PlayerPos 8";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory 8";

    for (int i = 1; i <= 2; i++) {
      domain.movePlayer(Direction.LEFT);
      assert domain.getPlayer().getTreasuresLeft() == 0 : "treasures";
      assert domain.getPlayer().getPosition().equals(new Position(4, 6-i)) : "PlayerPos";
      assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
    }

    for (int i = 1; i <= 3; i++) {
      domain.movePlayer(Direction.UP);
      assert domain.getPlayer().getTreasuresLeft() == 0 : "treasures";
      assert domain.getPlayer().getPosition().equals(new Position(4-i, 4)) : "PlayerPos";
      assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
    }

    assert domain.movePlayer(Direction.UP).hasPlayerWon();
    assert domain.getPlayer().getTreasuresLeft() == 0 : "treasures";
    assert domain.getPlayer().getPosition().equals(new Position(0, 4)) : "PlayerPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
  }

  @Test
  public void testLose1() {
    int treasures = 0;

    List<GameObject> items = new ArrayList<>() {
    };
    items.add(new Treasure(new Position(4, 2)));
    treasures++;
    items.add(new Treasure(new Position(4, 6)));
    treasures++;
    items.add(new Key(0, new Position(3, 2)));

    Player player = new Player(new Position(4, 4), new ArrayList<Item>(), treasures);
    Tile[][] tileArr = makeBoard();

    tileArr[player.getPosition().x()][player.getPosition().y()] = new Free(player, player.getPosition());
    tileArr[5][2] = new Door((Key) items.get(2), player.getPosition());
    tileArr[4][3] = new Water(null, player.getPosition());
    tileArr[4][5] = new Lava(null, player.getPosition());

    tileArr = addItemsToBoard(items, tileArr);
    Board board = new Board(tileArr, true);
    Domain domain = new Domain(board, player, null);

    assert !domain.movePlayer(Direction.LEFT).isPlayerAlive() : "Hasn't died Died";
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getPlayer().getPosition().equals(new Position(4, 3)) : "PlayerPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
  }

  @Test
  public void testLose2() {
    int treasures = 0;

    List<GameObject> items = new ArrayList<>() {
    };
    items.add(new Treasure(new Position(4, 2)));
    treasures++;
    items.add(new Treasure(new Position(4, 6)));
    treasures++;
    items.add(new Key(0, new Position(3, 2)));

    Player player = new Player(new Position(4, 4), new ArrayList<Item>(), treasures);
    Tile[][] tileArr = makeBoard();

    tileArr[player.getPosition().x()][player.getPosition().y()] = new Free(player, player.getPosition());
    tileArr[5][2] = new Door((Key) items.get(2), player.getPosition());
    tileArr[4][3] = new Water(null, player.getPosition());
    tileArr[4][5] = new Lava(null, player.getPosition());

    tileArr = addItemsToBoard(items, tileArr);
    Board board = new Board(tileArr, true);
    Domain domain = new Domain(board, player, null);

    assert !domain.movePlayer(Direction.RIGHT).isPlayerAlive() : "Died";
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getPlayer().getPosition().equals(new Position(4, 5)) : "PlayerPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
  }

  @Test
  public void testLose3() {
    int treasures = 0;
    List<Position> lp = new ArrayList<Position>(); lp.add(new Position(3, 4));

    List<GameObject> items = new ArrayList<>() {
    };
    items.add(new Treasure(new Position(4, 2)));
    treasures++;
    items.add(new Treasure(new Position(4, 6)));
    treasures++;
    items.add(new Key(0, new Position(3, 2)));
    items.add(new Actor(lp));

    Player player = new Player(new Position(4, 4), new ArrayList<Item>(), treasures);
    Tile[][] tileArr = makeBoard();

    tileArr[player.getPosition().x()][player.getPosition().y()] = new Free(player, player.getPosition());
    tileArr[5][2] = new Door((Key) items.get(2), player.getPosition());
    tileArr[4][3] = new Water(null, player.getPosition());
    tileArr[4][5] = new Lava(null, player.getPosition());

    tileArr = addItemsToBoard(items, tileArr);
    Board board = new Board(tileArr, true);
    Domain domain = new Domain(board, player, null);

    assert !domain.movePlayer(Direction.UP).isPlayerAlive() : "Died";
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getPlayer().getPosition().equals(new Position(3, 4)) : "PlayerPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
  }

  @Test
  public void testLose4() {
    int treasures = 0;
    List<Position> lp = new ArrayList<Position>();
    lp.add(new Position(3, 4));
    lp.add(new Position(4, 4));

    Actor actor = new Actor(lp);
    List<Actor> la = new ArrayList<>(); la.add(actor);

    List<GameObject> items = new ArrayList<>() {
    };
    items.add(new Treasure(new Position(4, 2)));
    treasures++;
    items.add(new Treasure(new Position(4, 6)));
    treasures++;
    items.add(new Key(0, new Position(3, 2)));
    items.add(actor);

    Player player = new Player(new Position(4, 4), new ArrayList<Item>(), treasures);
    Tile[][] tileArr = makeBoard();

    tileArr[player.getPosition().x()][player.getPosition().y()] = new Free(player, player.getPosition());
    tileArr[5][2] = new Door((Key) items.get(2), player.getPosition());
    tileArr[4][3] = new Water(null, player.getPosition());
    tileArr[4][5] = new Lava(null, player.getPosition());

    tileArr = addItemsToBoard(items, tileArr);
    Board board = new Board(tileArr, true);
    Domain domain = new Domain(board, player, la);

    assert !domain.advanceClock().isPlayerAlive() : "Died";
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getPlayer().getPosition().equals(new Position(4, 4)) : "PlayerPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
  }
  @Test
  public void testInfo1() {
    int treasures = 0;
    List<Position> lp = new ArrayList<Position>();
    lp.add(new Position(3, 4));
    lp.add(new Position(4, 4));

    Actor actor = new Actor(lp);
    List<Actor> la = new ArrayList<>(); la.add(actor);

    List<GameObject> items = new ArrayList<>() {
    };
    items.add(new Treasure(new Position(4, 2)));
    treasures++;
    items.add(new Treasure(new Position(4, 6)));
    treasures++;
    items.add(new Key(0, new Position(3, 2)));
    items.add(actor);

    Player player = new Player(new Position(4, 4), new ArrayList<Item>(), treasures);
    Tile[][] tileArr = makeBoard();

    tileArr[player.getPosition().x()][player.getPosition().y()] = new Free(player, player.getPosition());
    tileArr[5][2] = new Door((Key) items.get(2), player.getPosition());
    tileArr[4][3] = new Water(null, player.getPosition());
    tileArr[4][5] = new Lava(null, player.getPosition());
    tileArr[5][4] = new InfoTile(null, player.getPosition(), "Information on my info tile");

    tileArr = addItemsToBoard(items, tileArr);
    Board board = new Board(tileArr, true);
    Domain domain = new Domain(board, player, la);

    assert domain.movePlayer(Direction.DOWN).getTileInformation() != null : "didn't get info";
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getPlayer().getPosition().equals(new Position(5, 4)) : "PlayerPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
  }

  @Test
  public void testActor1() {
    int treasures = 0;
    List<Position> lp = new ArrayList<Position>();
    lp.add(new Position(3, 4));
    lp.add(new Position(2, 4));
    lp.add(new Position(2, 3));
    lp.add(new Position(3, 3));

    Actor actor = new Actor(lp);
    List<Actor> la = new ArrayList<>(); la.add(actor);

    List<GameObject> items = new ArrayList<>();
    items.add(new Treasure(new Position(4, 2)));
    treasures++;
    items.add(new Treasure(new Position(4, 6)));
    treasures++;
    items.add(new Key(0, new Position(3, 2)));
    items.add(actor);

    Player player = new Player(new Position(4, 4), new ArrayList<Item>(), treasures);
    Tile[][] tileArr = makeBoard();

    tileArr[player.getPosition().x()][player.getPosition().y()] = new Free(player, player.getPosition());
    tileArr[5][2] = new Door((Key) items.get(2), player.getPosition());
    tileArr[4][3] = new Water(null, player.getPosition());
    tileArr[4][5] = new Lava(null, player.getPosition());
    tileArr[5][4] = new InfoTile(null, player.getPosition(), "Information on my info tile");

    tileArr = addItemsToBoard(items, tileArr);
    Board board = new Board(tileArr, true);
    Domain domain = new Domain(board, player, la);

    domain.advanceClock();
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getActors().get(0).getPosition().equals(new Position(2, 4)) : "ActorPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";

    domain.advanceClock();
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getActors().get(0).getPosition().equals(new Position(2, 3)) : "ActorPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";

    domain.advanceClock();
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getActors().get(0).getPosition().equals(new Position(3, 3)) : "ActorPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";

    domain.advanceClock();
    assert domain.getPlayer().getTreasuresLeft() == 2 : "treasures";
    assert domain.getActors().get(0).getPosition().equals(new Position(3, 4)) : "ActorPos";
    assert domain.getPlayer().getInventory().size() == 0 : "Inventory";
  }

  /**
   * Make board.
   *
   * @return the board
   */
  public Tile[][] makeBoard() {
    Tile[][] tileArr = {
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null},
        {null, null, null, null, null, null, null, null, null}};

    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {

        if (i == 1 || j == 1 || i == 7 || j == 7) {
          tileArr[i][j] = new Wall(new Position(i, j));
        } else {
          tileArr[i][j] = new Free(null, new Position(i, j));
        }
      }
    }

    tileArr[0][4] = new Exit(null, new Position(0, 4));
    tileArr[1][4] = new ExitDoor(new Position(1, 4));

    return tileArr;
  }

  /**
   * Make board tile [ ] [ ].
   *
   * @param <T>     the type parameter
   * @param listT   the list t
   * @param tileArr the tile arr
   * @return the tile [ ] [ ]
   */
  public <T extends GameObject> Tile[][] addItemsToBoard(List<T> listT, Tile[][] tileArr) {
    for (T t : listT) {
      tileArr[t.getPosition().x()][t.getPosition().y()]
          = new Free(t, t.getPosition());
    }
    return tileArr;
  }
}
