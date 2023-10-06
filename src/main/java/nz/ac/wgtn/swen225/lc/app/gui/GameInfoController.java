package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import nz.ac.wgtn.swen225.lc.app.Game;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Item;
import nz.ac.wgtn.swen225.lc.domain.gameObject.item.Key;

import java.util.*;

/**
 * The UI for displaying the game info.
 *
 * @author Trent Shailer 300602354.
 */
public class GameInfoController {
  public Label treasureLeft;
  public Label timeLeft;
  public VBox inventory;

  private Map<String, Image> itemImages;

  private List<ImageView> slots;

  private int numberOfSlots;

  /**
   * Initializes the UI for game info.
   */
  public void initialize() {
    itemImages = Map.ofEntries(
        Map.entry("key_r", new Image("/Sprites/Key_Red.png")),
        Map.entry("key_g", new Image("/Sprites/Key_Green.png")),
        Map.entry("key_b", new Image("/Sprites/Key_Yellow.png")),
        Map.entry("noItem", new Image("/UI/GameInfo/blank.png"))
    );

    slots = new ArrayList<>();

    HBox inventoryRow1 = initializeInventoryRow(5);
    HBox inventoryRow2 = initializeInventoryRow(5);

    inventory.getChildren().addAll(inventoryRow1, inventoryRow2);
  }

  private HBox initializeInventoryRow(int slots) {
    HBox row = new HBox();
    row.setAlignment(Pos.CENTER);

    for (int i = 0; i < slots; i++) {
      StackPane slot = initializeInventorySlot();
      row.getChildren().add(slot);
    }

    numberOfSlots += slots;
    return row;
  }

  private StackPane initializeInventorySlot() {
    ImageView item = new ImageView(itemImages.get("noItem"));
    item.setFitHeight(24);
    item.setFitWidth(24);

    slots.add(item);

    StackPane slot = new StackPane();

    Image backgroundImage = new Image("/UI/GameInfo/inventory.png");
    ImageView background = new ImageView(backgroundImage);

    slot.getChildren().addAll(background, item);

    return slot;
  }

  /**
   * Update the Game Info Bar to reflect any updates that have occurred.
   *
   * @param domain The domain that is relevant to the game bar.
   * @param game   The game that is relevant to the game bar.
   */
  public void updateUI(Domain domain, Game game) {
    timeLeft.setText(Integer.toString(game.getTimeLeft()));
    treasureLeft.setText(Integer.toString(domain.getPlayer().getTreasuresLeft()));

    List<Item> playerInventory = domain.getPlayer().getInventory();
    for (int i = 0; i < numberOfSlots; i++) {
      updateSlot(i, playerInventory);
    }
  }

  private void updateSlot(int index, List<Item> items) {
    String imageKey = "noItem";

    if (index < items.size()) {
      Item item = items.get(index);
      imageKey = getImageKeyFromItem(item);
    }


    slots.get(index).setImage(itemImages.get(imageKey));
  }

  private String getImageKeyFromItem(Item item) {
    if (item instanceof Key key) {
      int id = key.getKey();
      char color = (char) id;
      String newKey = "key_" + color;

      if (itemImages.containsKey(newKey)) {
        return newKey;
      }
    }

    return "noItem";
  }
}
