package base;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import base.lib.Enums.PitData.*;

public class CustomTeam {
    //BASIC ID INFO
    int number; //MAIN ID
    String scoutedName; //comes from pit data
    String syncedName; //comes from sync
    String sponsors; //comes from sync
    boolean isFullySync = false; //changes to true when team and all matches are synced
    ArrayList<String> nicknames = new ArrayList<>(); //comes from pit data
    
    //LINKED DATA STRUCTURES
    ArrayList<CustomMatch> matches = new ArrayList<CustomMatch>();
    ArrayList<CustomPit> pits = new ArrayList<CustomPit>();
    ArrayList<Date> changeTime = new ArrayList<>(); //maping of change number to time
    ArrayList<String> changeField = new ArrayList<>(); //mapping of change number to name of the changed field EXACTLY LIKE THE VAR NAME
    ArrayList<Object> changePrev = new ArrayList<>(); //mapping of change number to original value
    ArrayList<Object> changeNew = new ArrayList<>(); //mapping of change number to new value
    
    //MATCH DATA
    boolean consistOffHab; //does robot consistently get off the hab (more than 75%)
    int totalYellow; //total number of yellow cards
    int totalRed; //total number of red cards
    int totalProblems; //total number of estops/borks
    int totalRPs; //total ranking points
    int totalHabRPs; //total hab-related ranking points
    int totalRocketRPs; //total rocket-related ranking points
    HashMap<Integer, String> matchNotes = new HashMap<>(); //DATA SCOUT notes. linked by actual match num
    
    //PIT DATA
    String latestScout; //name of latest person to enter pit data
    HabLevel climbLevel; //highest level of *tested* climb (1 2 3)
    HabLevel startLevel; //highest level of *tested* start
    Piece intakeType; //game pieces capable of intaking
    Piece prefPiece; //preferred game piece
    RocketLevel maxRocketLevel; //maximum rocket level the robot can reach
    int mechIssues; //mechanical issues, on a scale of 1-10
    boolean hasCamera; //has a driver camera
    boolean hasAlignment; //has a way of aligning on field
    boolean hasPresets; //has preset positions for subsystems
    double piecesPMatch; //average number of pieces placed in a match
    double cycleTime; //average cycle time
    boolean autoDriver; //uses a driver+camera in auto
    boolean autoPath; //uses pathing in auto
    boolean NOauto; //does not move during auto
    Preference humanPlayerPref; //level of preference for their own human player
    Preference stratPref; //level of preference for their own strategy
    
    //STRATEGY
    ArrayList<AutoStrategy> autoStrategies = new ArrayList<>(); //strategies in the auto period
    String autoNotes; //notes on the auto period
    ArrayList<TeleopStrategy> teleopStrategies = new ArrayList<>(); // strategies in the main match
    String teleopNotes; //notes on the main match
    String generalNotes; //general notes on the team
    
    public CustomTeam(int teamNum_){
        setNumber(teamNum_);
    }
    
    public void addMatch(CustomMatch match_){
        getMatches().add(match_);
        
        setTotalYellow(getTotalYellow() + (int)match_.yellows);
        setTotalRed(getTotalRed() + (int)match_.reds);
        setTotalProblems(getTotalProblems() + (match_.eStopped?1:0) + (match_.broken?1:0));
        setTotalRPs(getTotalRPs() + match_.rankingPoints);
        setTotalHabRPs(getTotalHabRPs() + (match_.habRP?1:0));
        setTotalRocketRPs(getTotalRocketRPs() + (match_.rocketRP?1:0));
        getMatchNotes().put(match_.matchNum, match_.matchNotes);
        
        int timesOffHab_ = 0;
        int matchesChecked_ = 0;
        for(CustomMatch m_ : getMatches()){
            timesOffHab_ += (m_.crossedLine?1:0);
            matchesChecked_++;
        }
        setConsistOffHab((((double)timesOffHab_/(double)matchesChecked_)>=.75));
    }
    
    public void addPit(CustomPit pit_){
        if(getPits().size()!=0){
            appendPit(pit_);
        }else{
            addFirstPit(pit_);
        }
        getPits().add(pit_);
    }
    
    public void addFirstPit(CustomPit pit_){
        setScoutedName(pit_.getTeamName());
        setNicknames(pit_.getNicknames());
        
        setLatestScout(pit_.getScoutName());
        setClimbLevel(pit_.getClimbLevel());
        setStartLevel(pit_.getStartLevel());
        setIntakeType(pit_.getIntakeType());
        setPrefPiece(pit_.getIntakePref());
        setMaxRocketLevel(pit_.getReach());
        setMechIssues(pit_.getMechanicalIssues());
        setHasCamera(pit_.isHasCamera());
        setHasAlignment(pit_.isHasAlignment());
        setHasPresets(pit_.isHasPresets());
        setPiecesPMatch(pit_.getPiecesPMatch());
        setCycleTime(pit_.getCycleTime());
        setAutoDriver(pit_.isDriverAuto());
        setAutoPath(pit_.isPathsAuto());
        setNOauto(pit_.isNoStratAuto());
        setHumanPlayerPref(pit_.getHumanPlayer());
        setStratPref(pit_.getStrategy());
        
        setAutoStrategies(pit_.getMainAutoStrat());
        setTeleopStrategies(pit_.getMainTeleopStrat());
        
        setAutoNotes(pit_.getNotesAuto());
        setTeleopNotes(pit_.getNotesTeleop());
        setGeneralNotes(pit_.getMainNotes());
    }
    
    public void appendPit(CustomPit pit_){
        changeTime.add(Date.from(Instant.now()));
        changeField.add("latestScout");
        changePrev.add(getLatestScout());
        changeNew.add(pit_.getScoutName());
        setLatestScout(pit_.getScoutName());
        
        setScoutedName((String)changeLogAppend("scoutedName", getScoutedName(), pit_.getScoutName()));
        setNicknames((ArrayList<String>)changeLogAppend("nicknames", getNicknames(), pit_.getNicknames()));
        setClimbLevel((HabLevel) changeLogAppend("climbLevel", getClimbLevel(), pit_.getClimbLevel()));
        setStartLevel((HabLevel) changeLogAppend("startLevel", getStartLevel(), pit_.getStartLevel()));
        setIntakeType((Piece) changeLogAppend("intakeType", getIntakeType(), pit_.getIntakeType()));
        setPrefPiece((Piece) changeLogAppend("prefPiece", getPrefPiece(), pit_.getIntakePref()));
        setMaxRocketLevel((RocketLevel) changeLogAppend("maxRocketLevel", getMaxRocketLevel(), pit_.getReach()));
        setMechIssues((int) changeLogAppend("mechIssues", getMechIssues(), pit_.getMechanicalIssues()));
        setHasCamera((boolean) changeLogAppend("hasCamera", isHasCamera(), pit_.isHasCamera()));
        setHasAlignment((boolean) changeLogAppend("hasAlignment", isHasAlignment(), pit_.isHasAlignment()));
        setHasPresets((boolean) changeLogAppend("hasPresets", isHasPresets(), pit_.isHasPresets()));
        setPiecesPMatch(Math.floor((double)changeLogAppend("piecesPMatch", getPiecesPMatch(), pit_.getPiecesPMatch())));
        setCycleTime(Math.floor((double)changeLogAppend("cycleTime", getCycleTime(), pit_.getCycleTime())));
        setAutoDriver((boolean) changeLogAppend("autoDriver", isAutoDriver(), pit_.isDriverAuto()));
        setAutoPath((boolean) changeLogAppend("autoPath", isAutoPath(), pit_.isPathsAuto()));
        setNOauto((boolean) changeLogAppend("NOauto", isNOauto(), pit_.isNoStratAuto()));
        setHumanPlayerPref((Preference) changeLogAppend("humanPlayerPref", getHumanPlayerPref(), pit_.getHumanPlayer()));
        setStratPref((Preference) changeLogAppend("stratPref", getStratPref(), pit_.getStrategy()));
        setAutoStrategies((ArrayList<AutoStrategy>) changeLogAppend("autoStrategies", getAutoStrategies(), pit_.getMainAutoStrat()));
        setTeleopStrategies((ArrayList<TeleopStrategy>) changeLogAppend("teleopStrategies", getTeleopStrategies(), pit_.getMainTeleopStrat()));
        setAutoNotes((String) changeLogAppend("autoNotes", getAutoNotes(), pit_.getNotesAuto()));
        setTeleopNotes((String) changeLogAppend("teleopNotes", getTeleopNotes(), pit_.getNotesTeleop()));
        setGeneralNotes((String) changeLogAppend("generalNotes", getGeneralNotes(), pit_.getMainNotes()));
        
    }
    
    private Object changeLogAppend(String fieldName_, Object prev_, Object newVal_){
        if(!prev_.equals(newVal_)) {
            getChangeTime().add(Date.from(Instant.now()));
            getChangeField().add(fieldName_);
            getChangePrev().add(prev_);
            getChangeNew().add(newVal_);
            return newVal_;
        }else{
            return prev_;
        }
    }
    
    public int getNumber() {
        return number;
    }
    
    public void setNumber(int number) {
        this.number = number;
    }
    
    public String getScoutedName() {
        return scoutedName;
    }
    
    public void setScoutedName(String scoutedName) {
        this.scoutedName = scoutedName;
    }
    
    public String getSyncedName() {
        return syncedName;
    }
    
    public void setSyncedName(String syncedName) {
        this.syncedName = syncedName;
    }
    
    public String getSponsors() {
        return sponsors;
    }
    
    public void setSponsors(String sponsors) {
        this.sponsors = sponsors;
    }
    
    public boolean isFullySync() {
        return isFullySync;
    }
    
    public void setFullySync(boolean fullySync) {
        isFullySync = fullySync;
    }
    
    public ArrayList<String> getNicknames() {
        return nicknames;
    }
    
    public void setNicknames(ArrayList<String> nicknames) {
        this.nicknames = nicknames;
    }
    
    public ArrayList<CustomMatch> getMatches() {
        return matches;
    }
    
    public void setMatches(ArrayList<CustomMatch> matches) {
        this.matches = matches;
    }
    
    public ArrayList<CustomPit> getPits() {
        return pits;
    }
    
    public void setPits(ArrayList<CustomPit> pits) {
        this.pits = pits;
    }
    
    public ArrayList<Date> getChangeTime() {
        return changeTime;
    }
    
    public void setChangeTime(ArrayList<Date> changeTime) {
        this.changeTime = changeTime;
    }
    
    public ArrayList<String> getChangeField() {
        return changeField;
    }
    
    public void setChangeField(ArrayList<String> changeField) {
        this.changeField = changeField;
    }
    
    public ArrayList<Object> getChangePrev() {
        return changePrev;
    }
    
    public void setChangePrev(ArrayList<Object> changePrev) {
        this.changePrev = changePrev;
    }
    
    public ArrayList<Object> getChangeNew() {
        return changeNew;
    }
    
    public void setChangeNew(ArrayList<Object> changeNew) {
        this.changeNew = changeNew;
    }
    
    public boolean isConsistOffHab() {
        return consistOffHab;
    }
    
    public void setConsistOffHab(boolean consistOffHab) {
        this.consistOffHab = consistOffHab;
    }
    
    public int getTotalYellow() {
        return totalYellow;
    }
    
    public void setTotalYellow(int totalYellow) {
        this.totalYellow = totalYellow;
    }
    
    public int getTotalRed() {
        return totalRed;
    }
    
    public void setTotalRed(int totalRed) {
        this.totalRed = totalRed;
    }
    
    public int getTotalProblems() {
        return totalProblems;
    }
    
    public void setTotalProblems(int totalProblems) {
        this.totalProblems = totalProblems;
    }
    
    public int getTotalRPs() {
        return totalRPs;
    }
    
    public void setTotalRPs(int totalRPs) {
        this.totalRPs = totalRPs;
    }
    
    public int getTotalHabRPs() {
        return totalHabRPs;
    }
    
    public void setTotalHabRPs(int totalHabRPs) {
        this.totalHabRPs = totalHabRPs;
    }
    
    public int getTotalRocketRPs() {
        return totalRocketRPs;
    }
    
    public void setTotalRocketRPs(int totalRocketRPs) {
        this.totalRocketRPs = totalRocketRPs;
    }
    
    public HashMap<Integer, String> getMatchNotes() {
        return matchNotes;
    }
    
    public void setMatchNotes(HashMap<Integer, String> matchNotes) {
        this.matchNotes = matchNotes;
    }
    
    public String getLatestScout() {
        return latestScout;
    }
    
    public void setLatestScout(String latestScout) {
        this.latestScout = latestScout;
    }
    
    public HabLevel getClimbLevel() {
        return climbLevel;
    }
    
    public void setClimbLevel(HabLevel climbLevel) {
        this.climbLevel = climbLevel;
    }
    
    public HabLevel getStartLevel() {
        return startLevel;
    }
    
    public void setStartLevel(HabLevel startLevel) {
        this.startLevel = startLevel;
    }
    
    public Piece getIntakeType() {
        return intakeType;
    }
    
    public void setIntakeType(Piece intakeType) {
        this.intakeType = intakeType;
    }
    
    public Piece getPrefPiece() {
        return prefPiece;
    }
    
    public void setPrefPiece(Piece prefPiece) {
        this.prefPiece = prefPiece;
    }
    
    public RocketLevel getMaxRocketLevel() {
        return maxRocketLevel;
    }
    
    public void setMaxRocketLevel(RocketLevel maxRocketLevel) {
        this.maxRocketLevel = maxRocketLevel;
    }
    
    public int getMechIssues() {
        return mechIssues;
    }
    
    public void setMechIssues(int mechIssues) {
        this.mechIssues = mechIssues;
    }
    
    public boolean isHasCamera() {
        return hasCamera;
    }
    
    public void setHasCamera(boolean hasCamera) {
        this.hasCamera = hasCamera;
    }
    
    public boolean isHasAlignment() {
        return hasAlignment;
    }
    
    public void setHasAlignment(boolean hasAlignment) {
        this.hasAlignment = hasAlignment;
    }
    
    public boolean isHasPresets() {
        return hasPresets;
    }
    
    public void setHasPresets(boolean hasPresets) {
        this.hasPresets = hasPresets;
    }
    
    public double getPiecesPMatch() {
        return piecesPMatch;
    }
    
    public void setPiecesPMatch(double piecesPMatch) {
        this.piecesPMatch = piecesPMatch;
    }
    
    public double getCycleTime() {
        return cycleTime;
    }
    
    public void setCycleTime(double cycleTime) {
        this.cycleTime = cycleTime;
    }
    
    public boolean isAutoDriver() {
        return autoDriver;
    }
    
    public void setAutoDriver(boolean autoDriver) {
        this.autoDriver = autoDriver;
    }
    
    public boolean isAutoPath() {
        return autoPath;
    }
    
    public void setAutoPath(boolean autoPath) {
        this.autoPath = autoPath;
    }
    
    public boolean isNOauto() {
        return NOauto;
    }
    
    public void setNOauto(boolean NOauto) {
        this.NOauto = NOauto;
    }
    
    public Preference getHumanPlayerPref() {
        return humanPlayerPref;
    }
    
    public void setHumanPlayerPref(Preference humanPlayerPref) {
        this.humanPlayerPref = humanPlayerPref;
    }
    
    public Preference getStratPref() {
        return stratPref;
    }
    
    public void setStratPref(Preference stratPref) {
        this.stratPref = stratPref;
    }
    
    public ArrayList<AutoStrategy> getAutoStrategies() {
        return autoStrategies;
    }
    
    public void setAutoStrategies(ArrayList<AutoStrategy> autoStrategies) {
        this.autoStrategies = autoStrategies;
    }
    
    public String getAutoNotes() {
        return autoNotes;
    }
    
    public void setAutoNotes(String autoNotes) {
        this.autoNotes = autoNotes;
    }
    
    public ArrayList<TeleopStrategy> getTeleopStrategies() {
        return teleopStrategies;
    }
    
    public void setTeleopStrategies(ArrayList<TeleopStrategy> teleopStrategies) {
        this.teleopStrategies = teleopStrategies;
    }
    
    public String getTeleopNotes() {
        return teleopNotes;
    }
    
    public void setTeleopNotes(String teleopNotes) {
        this.teleopNotes = teleopNotes;
    }
    
    public String getGeneralNotes() {
        return generalNotes;
    }
    
    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }
}
