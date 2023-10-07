package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.JsonNode;
import nz.ac.wgtn.swen225.lc.domain.Domain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static nz.ac.wgtn.swen225.lc.persistency.Load.*;
import static nz.ac.wgtn.swen225.lc.persistency.Save.*;

public class saveTests {


    @BeforeAll
    public static void setup() {
        SaveData s = loadAsSaveData(new File("src/main/resources/levels/levels-template.json"));
        saveJsonFromSaveData(new File("src/main/resources/levels/saved-level-test.json"), s);
    }

    @Test
    public void SavedJSONNotEmpty(){
        JsonNode json = loadAsJSON(new File("src/main/resources/levels/saved-level-test.json"));

        assert json != null;
        assert json.isEmpty() != true;
    }

    @Test
    public void SavedJSONIdenticalToTemplate(){
        JsonNode templateJson = loadAsJSON(new File("src/main/resources/levels/levels-template.json"));
        JsonNode savedJson = loadAsJSON(new File("src/main/resources/levels/saved-level-test.json"));

        assert templateJson.equals(savedJson);
    }
}
