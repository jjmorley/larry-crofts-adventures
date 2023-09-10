# Requirements

### 1. Input

| Keybind | Action(s)                                                                                                   |
|---------|-------------------------------------------------------------------------------------------------------------|
| CTRL-X  | Exit without saving<br/>Next game will resume from last unfinished save if one exists<br/>Else from level 1 |
| CTRL-S  | Save and exit<br/>Next game will resume form save                                                           |
| CTRL-R  | Load a saved game<br/>Popup file sector to select the save                                                  |
| CTRL-#  | Start a new game at level #                                                                                 |
| SPACE   | Pause the game<br/>Display a "Game is paused" dialog                                                        |
| ESC     | Resume the game<br/>Close the "Game is paused" dialog                                                       |
| ARROWS  | Try move in the arrow direction                                                                             |

### 2. The application window should display
- The time left in the level
- The current Level
- The collected keys
- The number of treasures left in the level

### 3. It should also offer buttons and menu items to:
- Pause the game
- Exit the game
- Save the game
- Load a saved game
- Open a help page
- Load a level (Not a requirement but is kind of needed?)

### The module also manages the level time limit. When it reaches zero:
- Display game over
- Stop the game
- Let the player restart the level

# Brainstorm

```java
class App {
    private Game currentGame;
    
    public App(){
        /*
         * App always exists
         * No data to do with the current game exists directly in app, instead current game data should be handled
         * by currentGame
         * 
         * currentGame should be initialized with either:
         *      The newest incomplete save, if one exists
         *      Else level 1
         * */
    }
}

class Game {
    /*
     * This manages the timer for the current game
     * contains the domain
     * 
     * how this is initialized depends on how persistence works
     */
     
}

class MainWindow {
    /*
     * Is the main window
     * */
}

class InputManager {
    /*
     * Captures the keyboard inputs
     * */
}

class MenuBar {
    /*
     * Renders and manages the menu bar
     * */
}
```