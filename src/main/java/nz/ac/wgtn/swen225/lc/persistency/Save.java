package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.util.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.*;

import java.io.File;
import java.io.IOException;

public class Save {

    /**
     * Writes current state of level to JSON.
     *
     * TODO: Save json with name based upon computer time. May need separate
     * func. for this
     */
    public static void saveJSON(String[][] levelArray, int gridSize, String charPos){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();

        //add the array representing the level to the JSON
        //FYI: this is deliberately an ObjectNode, not an ArrayNode.
        ObjectNode array = mapper.createObjectNode();
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j <  gridSize; j++){
                array.putPOJO(i + ", " + j, levelArray[i][j]);
            }
        }
        json.putPOJO("level", array);

        //Add character position  and grid size to JSON
        json.putPOJO("char",charPos);
        json.putPOJO("gridSize",gridSize);

        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        try{
            writer.writeValue(new File("levels/savedLevel.json"), json);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
