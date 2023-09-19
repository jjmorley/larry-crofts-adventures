package nz.ac.wgtn.swen225.lc.persistency;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.io.IOException;

/**
 * Loads in level from JSON file.
 */
public class Load {

    private static JsonNode json;

    /**
     * Loads JSON file from a given filepath.
     *
     * TODO: CHANGE FILE PATH TO A PARAMETER
     */
    public static void loadJSON(){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.readTree(new File("levels/levels-template.json"));
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
    public static String[][] loadAsArray(){
        //JSON LOADING
        loadJSON();
        JsonNode level = json.get("level");
        int gridSize = json.get("gridSize").asInt();

        //MAKE ARRAY
        String[][] levelArray = new String[gridSize][gridSize];
        for(int i = 0; i < gridSize; i++){
            for(int j = 0; j <  gridSize; j++){
                levelArray[i][j] = level.get(i + ", " + j).asText();
            }
        }

    //for testing porpoises
//        for(int i = 0; i < gridSize; i++){
//            for(int j = 0; j <  gridSize; j++){
//                System.out.println(levelArray[i][j]);
//            }
//        }

        return levelArray;
    }

}
