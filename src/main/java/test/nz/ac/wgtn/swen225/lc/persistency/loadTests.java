package test.nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import static nz.ac.wgtn.swen225.lc.persistency.Load.*;

public class loadTests {
    JsonNode json = loadAsJSON("src/main/resources/levels/levels-template.json");
    String[][] levelArray = loadAsArray("src/main/resources/levels/levels-template.json");

    @Test
    public void JSONNotEmpty(){
        assert json != null;
        assert json.isEmpty() != true;
    }

    @Test
    public void arrayNotEmpty(){
        assert levelArray != null;
        assert levelArray.length != 0;
    }

    @Test
    public void JSONHasCharPos(){
        String charPos = json.get("char").asText();

        assert charPos != null;
        assert charPos.equals("1, 1");
    }

    @Test
    public void JSONHasGridSize(){
        int gridSize = json.get("gridSize").asInt();

        assert gridSize != 0;
        assert gridSize == 3;
    }

    @Test
    public void arrayCorrectLength(){
        assert levelArray.length == 3;
        assert levelArray[0].length == 3;
    }

    @Test
    public void arrayCorrectValues(){
        assert levelArray[0][0].equals("w");
        assert levelArray[0][2].equals("d");
        assert levelArray[1][1].equals("f");
        assert levelArray[2][0].equals("k");
    }

    @Test
    public void playerPositionCorrect(){
        assert json.get("char").asText().equals("1, 1");
    }

}
