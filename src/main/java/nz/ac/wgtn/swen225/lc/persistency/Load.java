package nz.ac.wgtn.swen225.lc.persistency;

import org.json.*;
/**
 * Loads in level from JSON file.
 */
public class Load {

    private int levelArray[][];

    public static void loadJSON(){
        JSONObject j = new JSONObject("levels/level-template.json");
        System.out.println(j.toString());
    }

    public int[][] loadAsArray(){
       return null;
    }

}
