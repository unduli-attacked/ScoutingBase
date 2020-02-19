package base.models;

<<<<<<< Updated upstream
import java.util.ArrayList;
=======
import base.lib.FileSystem;
import base.lib.Functions;
import base.lib.SavingFunctions;

import java.io.File;
import java.io.IOException;
>>>>>>> Stashed changes
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
    
    
<<<<<<< Updated upstream
    public ArrayList<Match> matches;
    public ArrayList<Pit> pits;
    public ArrayList<Team> teams;
=======
    public HashMap<String, BaseMatch> matches; //fileName, match
    public HashMap<Integer, BasePit> pits; //teamNum, pit
    public HashMap<Integer, BaseTeam> teams; //teamnum, BaseTeam
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
    
    public boolean saveSession(){
            File fl = new File("main_storage/sessions/MAIN"+this.tbaEventKey+".json");
            System.out.println(fl.getAbsolutePath());
            return SavingFunctions.save(fl, this);
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
                BaseMatch tempBaseMatch = SavingFunctions.recoverSaveable(matchJson_, BaseMatch.class);
                if (tempBaseMatch !=null)this.matches.put(tempBaseMatch.getFileName(), tempBaseMatch);
            }
        }
    }
    
    public void recoverPits(){
        File pits  = new File(this.directory+FileSystem.RAW_PITS);
        if(pits.isDirectory() && pits.listFiles()!=null) {
            for(File pitJson_ : pits.listFiles()){
                if(pitJson_.getName().startsWith("PRIMARY")){
                    BasePit tempP = SavingFunctions.recoverSaveable(pitJson_, BasePit.class);
                    if(tempP!=null)this.pits.put(tempP.teamNum, tempP);
                }
            }
        
            for(File pitJson_ : pits.listFiles()){
                if(pitJson_.getName().startsWith("SECOND")){
                    SecondPit tempSecondP = SavingFunctions.recoverSaveable(pitJson_, SecondPit.class);
                    if(tempSecondP!=null){
                        BasePit connecBasePit = Functions.findPit(tempSecondP.teamNum, this);
                        if(connecBasePit !=null){
                            if(!connecBasePit.secondPits.containsValue(tempSecondP)){
                                connecBasePit.secondPits.put(tempSecondP.timeScouted, tempSecondP);
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
                BaseTeam tempTem = SavingFunctions.recoverSaveable(tem, BaseTeam.class);
                if(tempTem!=null) this.teams.put(tempTem.teamNum, tempTem);
            }
        }
    }
    
    public void genDirectories() throws IOException {
        new File(this.directory+FileSystem.RAW_DATA).mkdirs();
        new File(this.directory+FileSystem.RAW_SCOUTS).mkdirs();
        new File(this.directory+FileSystem.RAW_MATCHES).mkdirs();
        new File(this.directory+FileSystem.RAW_PITS).mkdirs();
        new File(this.directory+FileSystem.RAW_TEAMS).mkdirs();
        new File(this.directory+FileSystem.FINAL_DATA).mkdirs();
        new File(this.directory+FileSystem.PDF_TEAMS).mkdirs();
        new File(this.directory+FileSystem.PDF_PIT_NOTES).mkdirs();
        new File(this.directory+FileSystem.PDF_MATCH_NOTES).mkdirs();
    }
    
    public void genTeams() throws IOException{
        //TODO team file
    }
    
    @Override
    public String toString(){
        return this.eventName;
    }
>>>>>>> Stashed changes
}
