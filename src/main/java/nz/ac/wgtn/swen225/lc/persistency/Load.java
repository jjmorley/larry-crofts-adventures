package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;

/**
 * Loads in level from JSON file.
 *
 * TODO: RETURN CHARACTER POS
 * TODO: EXTRA NPC
 */
public class Load {

    private static JsonNode json;

    /**
     * Loads JSON file from a given filepath.
     *
     */
    public static void loadJSON(String pathName){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.readTree(new File(pathName));
        }
        catch (IOException e){
            System.out.println("Something messed up. \n" + e.toString());
        }
    }

    /**
     * Return loaded JSON as an array. This is a 2D array ATM but am happy to change it.
     *
     * @return String[][]
     */
    public static String[][] loadAsArray(String pathName){
        //JSON LOADING
        loadJSON(pathName);
        JsonNode level = json.get("level");
        int gridSize = json.get("gridSize").asInt();

        //MAKE ARRAY
        String[][] levelArray = new String[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j <  gridSize; j++){
                levelArray[i][j] = level.get(i + ", " + j).asText();
            }
        }
        return levelArray;
    }

    /**
     * Return loaded JSON as JSONNode. This is for testing purposes.
     *
     * @return JsonNode
     */
    public static JsonNode loadAsJSON(String pathName){
        loadJSON(pathName);

        return json;
    }

}
