package base;

import base.scouts.DataScout;
import base.scouts.NoteScout;
import base.scouts.PitScout;

import java.util.ArrayList;

public class Session {
    public int year;
    public String eventName;
    public String tbaEventKey;
    public String directory;
    
    public ArrayList<Match> matches;
    public ArrayList pits;
    public ArrayList teams;
    public ArrayList<PitScout> pitScouts;
    public ArrayList<DataScout> standScouts;
    public ArrayList<NoteScout> noteScouts;
    
    public int numDataScouts; // per team per match
    public int numNoteScouts; // per alliance
    
    public Session(int year_, String eventName_, String tbaEventKey_, String directory_, int numDataScouts_, int numNoteScouts_){
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
        
        this.numDataScouts = numDataScouts_;
        this.numNoteScouts = numNoteScouts_;
    }
}
