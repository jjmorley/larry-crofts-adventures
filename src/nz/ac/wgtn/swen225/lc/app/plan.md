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

### 4. The module also manages the level time limit. When it reaches zero:
- Display game over
- Stop the game
- Let the player restart the level

# Brainstorm

Main question is about how the modules will communicate.

```java
// This plan would result in a lot of 2 way communication between modules...
// May have App as the mainWindow rather than having it as a child class
// Depends on how everything else works, if I need lots of logic in App to carry out it's responsibilities
// or if communication and that behaviour can be handled entirely by the child classes

// Domain has a TryMove function that retuerns a boolean and takes a Direction


class App {
    private Game currentGame;
    private MainWindow mainWindow;
    
    public App(){
        /*
         * App always exists
         * No data to do with the current game exists directly in app, instead current game data should be handled
         * by currentGame
         * 
         * currentGame should be initialized with either:
         *      The newest incomplete save, if one exists
         *      Else level 1
         * 
         * Creates main window and input manager
         * */
    }
    
    /*
     * Actions and events from classes like game and input manager call methods here?
     * */
}

class Game {
    private boolean isPaused;
    private Domain domain;
    private int timeLeft;
    
    // Perhaps the game contains the renderer and game info GUI parts
    // then the main window can render game.gameInfo and game.renderer
    // this means the components that need data from the game are children of the game
    // rather than having communication between have to pass from game -> app -> main window -> component
    // However, this depends on how the rendering with javaFX works and again how data communication works
    // this couples the renderer and the game info very closely with game
    
    // renderer needs info about the domain
    // gameInfo needs info from domain as well
    
    /*
     * This manages the timer for the current game
     * contains the domain
     * 
     * how this is initialized depends on how persistence works
     */
     
}


class InputManager {
    /*
     * Captures the keyboard inputs
     *
     * If the game is paused, stop interpreting certain inputs
     * 
     * Inputs should timeout for a set time after receiving an input
     * however it should probably only timeout if the action is successful
     * */
}

class MainWindow extends Application {
    private MenuBar menuBar;
    private GameInfoGUI gameInfoGUI;
    private Renderer renderer;
    private InputManager inputManager;
    
    // How do I get game reflected in renderer and this?
    
    /*
     * Is the main window
     * Always exists
     * When a new game is started, the game info and renderer are updated/recreated 
     * 
     * Creates the renderer instance
     * Creates the GameInfo gui
     * Creates the menu bar
     * */
}

class MenuBar {
    /*
     * Renders and manages the menu bar (3.)
     * */
}

class GameInfoGUI {
    /*
     * Renders all of (2.)
     * */
}
```