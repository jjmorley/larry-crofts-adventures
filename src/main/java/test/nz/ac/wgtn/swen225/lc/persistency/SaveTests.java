package test.nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.JsonNode;
import nz.ac.wgtn.swen225.lc.persistency.SaveData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static nz.ac.wgtn.swen225.lc.persistency.Load.*;
import static nz.ac.wgtn.swen225.lc.persistency.Save.*;

public class SaveTests {

  /**
   * These tests save, and then re-import the level
   * template for testing. This ensures the save process worked.
   *
   * @author Seb Collis 300603371
   */
  @BeforeAll
  public static void setup() {
    SaveData s = loadAsSaveData(new File("src/main/resources/levels/levels-template.json"));
    saveJsonFromSaveData(new File("src/main/resources/levels/saved-level-test.json"), s);
  }

  @Test
  public void savedJSONNotEmpty() throws IOException {
    String jsonAsString = Files.readString(new File("src/main/resources/levels/saved-level-test.json").toPath());
    JsonNode json = loadAsJSON(new File("src/main/resources/levels/saved-level-test.json"));

    assert jsonAsString != null;

    assert json != null;
    assert json.isEmpty() != true;
  }

  @Test
  public void autoSaveNotEmpty() throws IOException {
    SaveData s = loadAsSaveData(new File("src/main/resources/levels/levels-template.json"));
    autoSave(s);
    SaveData autoSavedData = autoLoad();

    assert autoSavedData != null;
    assert autoSavedData.getDomain() != null;
    assert autoSavedData.getDomain().getBoard().getBoard().length != 0;
  }

  @Test
  public void saveDataHasDomain() {
    SaveData s = loadAsSaveData(new File("src/main/resources/levels/saved-level-test.json"));

    assert s != null;
    assert s.getDomain() != null;
    assert s.getDomain().getBoard().getBoard().length != 0;
  }

  @Test
  public void savedJSONIdenticalToTemplate() {
    JsonNode templateJson = loadAsJSON(new File("src/main/resources/levels/levels-template.json"));
    JsonNode savedJson = loadAsJSON(new File("src/main/resources/levels/saved-level-test.json"));

    assert templateJson.equals(savedJson);
  }
}
