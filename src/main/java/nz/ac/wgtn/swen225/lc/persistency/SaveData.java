package nz.ac.wgtn.swen225.lc.persistency;

import nz.ac.wgtn.swen225.lc.domain.Domain;

public class SaveData {

    private Domain domain;
    private int levelNum;
    private int timeRemaining;

    public SaveData(Domain d, int level, int time){
        domain = d;
        levelNum = level;
        timeRemaining = time;
    }

    public Domain getDomain(){
        return domain;
    }

    public int getLevelNum(){
        return levelNum;
    }

    public int getTimeRemaining(){
        return timeRemaining;
    }
}
