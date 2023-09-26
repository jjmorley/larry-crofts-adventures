package test.nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;

import static nz.ac.wgtn.swen225.lc.persistency.Load.loadAsJSON;
import static nz.ac.wgtn.swen225.lc.persistency.Save.*;

public class saveTests {

    static String[][] levelArray = {
            new String[]{"w", "w", "d"},
            new String[]{"f", "f", "f"},
            new String[]{"k", "f", "f"}
    };

    @BeforeAll
    static void setup() {
        saveJSON(levelArray, 3, "1, 1", "levels/saved-level-test.json");
    }

    @Test
    public void SavedJSONNotEmpty(){
        JsonNode json = loadAsJSON("levels/saved-level-test.json");

        assert json != null;
        assert json.isEmpty() != true;
    }

    @Test
    public void SavedJSONIdenticalToTemplate(){
        JsonNode templateJson = loadAsJSON("levels/levels-template.json");
        JsonNode savedJson = loadAsJSON("levels/saved-level-test.json");

        assert templateJson.equals(savedJson);
    }
}
