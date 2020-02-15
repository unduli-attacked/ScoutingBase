package base.models;

import base.lib.FileSystem;
import base.lib.Functions;
import base.lib.SavingFunctions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    
    
    public HashMap<String, Match> matches; //fileName, match
    public HashMap<Integer, Pit> pits; //teamNum, pit
    public HashMap<Integer, Team> teams; //teamnum, Team
    public HashMap<String, PitScout> pitScouts; //scoutID, scout
    public HashMap<String, DataScout> standScouts; //scoutName, scout
    public HashMap<String, NoteScout> noteScouts; //scoutName, scout
    
    public int numDataScouts; // per team per match
    public int numNoteScouts; // per alliance
    
    public Session(int year_, String eventName_, String tbaEventKey_, String directory_, int numDataScouts_, int numNoteScouts_){
        this.year = year_;
        this.eventName = eventName_;
        this.tbaEventKey = tbaEventKey_;
        this.directory = directory_;
        
        this.matches = new HashMap<>();
        this.pits = new HashMap<>();
        this.teams = new HashMap<>();
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
    
    public boolean saveSession(){
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        try {
            FileWriter fr = new FileWriter("/mainStorage/sessions/"+this.tbaEventKey+".json");
            gson.toJson(this, fr);
            fr.flush();
            fr.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void saveAll(){
        this.saveSession();
        SavingFunctions.saveSaveables(this.matches.values());
        SavingFunctions.saveSaveables(this.pits.values());
        SavingFunctions.saveSaveables(this.teams.values());
        SavingFunctions.saveSaveables(this.pitScouts.values());
        SavingFunctions.saveSaveables(this.standScouts.values());
        SavingFunctions.saveSaveables(this.noteScouts.values());
    }
    
    public void recoverScouts(){
        File scouts = new File(this.directory+ FileSystem.RAW_SCOUTS);
        if(scouts.isDirectory() && scouts.listFiles()!=null) {
            for (File scoutJson_ : scouts.listFiles()) {
                if(scoutJson_.getName().startsWith("PIT")){
                    PitScout tempPScout = SavingFunctions.recoverSaveable(scoutJson_, PitScout.class);
                    if (tempPScout!=null)this.pitScouts.put(tempPScout.getID(), tempPScout);
                }else if(scoutJson_.getName().startsWith("DATA")){
                    DataScout tempDScout = SavingFunctions.recoverSaveable(scoutJson_, DataScout.class);
                    if (tempDScout!=null)this.standScouts.put(tempDScout.getName(), tempDScout);
                }else if(scoutJson_.getName().startsWith("NOTE")){
                    NoteScout tempNScout = SavingFunctions.recoverSaveable(scoutJson_, NoteScout.class);
                    if (tempNScout!=null) this.noteScouts.put(tempNScout.name, tempNScout);
                }
            }
        }
    }
    
    public void recoverMatches(){
        File matches  = new File(this.directory+FileSystem.RAW_MATCHES);
        if(matches.isDirectory() && matches.listFiles()!=null){
            for(File matchJson_ : matches.listFiles()){
                Match tempMatch = SavingFunctions.recoverSaveable(matchJson_, Match.class);
                if (tempMatch!=null)this.matches.put(tempMatch.getFileName(), tempMatch);
            }
        }
    }
    
    public void recoverPits(){
        File pits  = new File(this.directory+FileSystem.RAW_PITS);
        if(pits.isDirectory() && pits.listFiles()!=null) {
            for(File pitJson_ : pits.listFiles()){
                if(pitJson_.getName().startsWith("PRIMARY")){
                    Pit tempP = SavingFunctions.recoverSaveable(pitJson_, Pit.class);
                    if(tempP!=null)this.pits.put(tempP.teamNum, tempP);
                }
            }
        
            for(File pitJson_ : pits.listFiles()){
                if(pitJson_.getName().startsWith("SECOND")){
                    SecondPit tempSecondP = SavingFunctions.recoverSaveable(pitJson_, SecondPit.class);
                    if(tempSecondP!=null){
                        Pit connecPit = Functions.findPit(tempSecondP.teamNum, this);
                        if(connecPit!=null){
                            if(!connecPit.secondPits.containsValue(tempSecondP)){
                                connecPit.secondPits.put(tempSecondP.timeScouted, tempSecondP);
                            }
                        }
                    }
                }
            }
        }
    
    }
    
    public void recoverTeams(){
        File rawTeams = new File(this.directory+FileSystem.RAW_TEAMS);
        if(rawTeams.isDirectory() && rawTeams.listFiles()!=null){
            for(File tem : rawTeams.listFiles()){
                Team tempTem = SavingFunctions.recoverSaveable(tem, Team.class);
                if(tempTem!=null) this.teams.put(tempTem.teamNum, tempTem);
            }
        }
    }
}
