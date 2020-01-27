package base;

import base.scouts.DataScout;
import base.scouts.NoteScout;
import base.scouts.PitScout;

import java.util.ArrayList;
import java.util.HashMap;

public class Session {
    public int year;
    public String eventName;
    public String tbaEventKey;
    public String directory;
    
    public String spreadsheetID;
    public String mainPitTab;
    public String finalMainPitCol;
    public String secondPitTab;
    public String finalSecondPitCol;
    public String dataTab;
    public String finalDataCol;
    public String noteTab;
    public String finalNoteCol;
    
    
    public ArrayList<Match> matches;
    public ArrayList<Pit> pits;
    public ArrayList teams;
    public HashMap<String, PitScout> pitScouts;
    public HashMap<String, DataScout> standScouts;
    public HashMap<String, NoteScout> noteScouts;
    
    public int numDataScouts; // per team per match
    public int numNoteScouts; // per alliance
    
    public Session(int year_, String eventName_, String tbaEventKey_, String directory_, int numDataScouts_, int numNoteScouts_){
        this.year = year_;
        this.eventName = eventName_;
        this.tbaEventKey = tbaEventKey_;
        this.directory = directory_;
        
        this.matches = new ArrayList<>();
        this.pits = new ArrayList();
        this.teams = new ArrayList();
        this.pitScouts = new HashMap<>();
        this.standScouts = new HashMap<>();
        this.noteScouts = new HashMap<>();
        
        this.numDataScouts = numDataScouts_;
        this.numNoteScouts = numNoteScouts_;
    }
    
    public void setSheet(String spreadsheetID_, String mainPitTab_, String secondPitTab_, String dataTab_, String noteTab_,
                         String finalMainPitCol_, String finalSecondPitCol_, String finalDataCol_, String finalNoteCol_){
        this.spreadsheetID = spreadsheetID_;
        this.mainPitTab = mainPitTab_;
        this.secondPitTab = secondPitTab_;
        this.dataTab = dataTab_;
        this.noteTab = noteTab_;
        
        this.finalMainPitCol = finalMainPitCol_;
        this.finalSecondPitCol = finalSecondPitCol_;
        this.finalDataCol = finalDataCol_;
        this.finalNoteCol = finalNoteCol_;
    }
}
