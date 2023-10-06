package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Domain;

public class SaveData {

    private static Domain domain;
    private static int levelNum;
    private static int timeRemaining;

    public SaveData(Domain d, int level, int time){
        domain = d;
        levelNum = level;
        timeRemaining = time;
    }

    public static Domain getDomain(){
        return domain;
    }

    public static int getLevelNum(){
        return levelNum;
    }

    public static int getTimeRemaining(){
        return timeRemaining;
    }
}
