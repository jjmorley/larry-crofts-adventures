package nz.ac.wgtn.swen225.lc.recorder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Playback {
    public void loadRecordedGameFromFile(String filePath) {
        //TODO throw exception if failure to meet format
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {};
            Map<String, Object> recordedGameData = objectMapper.readValue(new File(filePath), typeReference);
            System.out.println("Loaded Recorded Game Data from: " + filePath);
            //return recordedGameData;
        } catch (IOException e) {
            e.printStackTrace();
            //return null; // Handle the error appropriately (e.g., return a default value or throw an exception)
        }
    }
}
