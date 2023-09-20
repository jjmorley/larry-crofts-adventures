package nz.ac.wgtn.swen225.lc.app.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

/**
 * A menu that performs an action when clicked.
 * Required because a Menu doesn't generate onAction events when it has no children.
 *
 * @author Trent Shailer 300602354.
 * */
public class MenuButton extends Menu {

  private MenuItem dummyItem;

  /**
   * Create a MenuButton.
   *
   * @param text The text for the menu to display.
   * @param onClick The event handler for when the menu item is clicked.
   * */

  public MenuButton(String text, EventHandler<ActionEvent> onClick) {
    super(text);

    dummyItem = new MenuItem();
    dummyItem.setOnAction(onClick);
    getItems().add(dummyItem);

    this.showingProperty().addListener(
        (observableValue, oldValue, newValue) -> {
          if (newValue) {
            hide();
            // the first menuItem is triggered
            getItems().get(0).fire();
          }
        }
    );
  }

  /**
   * Set the onAction event handler when clicked.
   *
   * @param onClick The event handler for when the menu item is clicked.
   * */
  public void setOnClick(EventHandler<ActionEvent> onClick) {
    dummyItem.setOnAction(onClick);
  }
}
