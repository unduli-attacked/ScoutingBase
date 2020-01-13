package base;

import java.util.ArrayList;

public class Session {
    public int year;
    public String eventName;
    public String tbaEventKey;
    public String directory;
    public ArrayList matches;
    public ArrayList pits;
    public ArrayList teams;
    public ArrayList pitScouts;
    public ArrayList standScouts;
    public ArrayList noteScouts;
    
    public Session(int year_, String eventName_, String tbaEventKey_, String directory_){
        this.year = year_;
        this.eventName = eventName_;
        this.tbaEventKey = tbaEventKey_;
        this.directory = directory_;
        
        this.matches = new ArrayList();
        this.pits = new ArrayList();
        this.teams = new ArrayList();
        this.pitScouts = new ArrayList();
        this.standScouts = new ArrayList();
        this.noteScouts = new ArrayList();
    }
}
