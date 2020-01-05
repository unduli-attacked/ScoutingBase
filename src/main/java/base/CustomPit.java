package base;

import base.lib.Enums.PitData.*;

import java.util.ArrayList;

public class CustomPit {
    //BASIC ID INFO
    int teamNum; //MAIN ID
    String teamName; //the name of the scouted team
    String scoutName; //the name of the pit scouter
    ArrayList<String> nicknames; //nicknames for the robot
    
    //CLIMB
    HabLevel climbLevel; //level the robot can climb to
    
    //PLACEMENT
    Piece intakeType; //pieces the robot can intake
    Piece intakePref; //preferred game piece
    RocketLevel reach; //what level the robot can reach on the rocket
    
    //ROBOT SPECIFICS
    boolean hasCamera; //has a driver camera
    boolean hasAlignment; //has a way of aligning on field
    boolean hasPresets; //has preset positions for subsystems
    double piecesPMatch; //average number of pieces placed in a match
    double cycleTime; //average cycle time
    int mechanicalIssues; //mechanical issues on a scale of 1-10
    
    //AUTO
    HabLevel startLevel; //highest level the robot can successfully start on
    boolean driverAuto; //uses driver + camera
    boolean pathsAuto; //uses pathing
    boolean noStratAuto; //doesnt move in auto
    ArrayList<AutoStrategy> mainAutoStrat; //main strategy for the autonomous period
    String notesAuto;
    
    //TELEOP
    ArrayList<TeleopStrategy> mainTeleopStrat; //main strategy for the teleoperated period
    String notesTeleop;
    
    //PREFERENCES
    Preference humanPlayer; //how strongly the human player is preferred
    Preference strategy; //how strongly the strategy is preferred
    
    String mainNotes;
    
    public CustomPit() {
    }
    
    public int getTeamNum() {
        return teamNum;
    }
    
    public void setTeamNum(int teamNum) {
        this.teamNum = teamNum;
    }
    
    public String getTeamName() {
        return teamName;
    }
    
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    public String getScoutName() {
        return scoutName;
    }
    
    public void setScoutName(String scoutName) {
        this.scoutName = scoutName;
    }
    
    public ArrayList<String> getNicknames() {
        return nicknames;
    }
    
    public void setNicknames(ArrayList<String> nicknames) {
        this.nicknames = nicknames;
    }
    
    public HabLevel getClimbLevel() {
        return climbLevel;
    }
    
    public void setClimbLevel(HabLevel climbLevel) {
        this.climbLevel = climbLevel;
    }
    
    public Piece getIntakeType() {
        return intakeType;
    }
    
    public void setIntakeType(Piece intakeType) {
        this.intakeType = intakeType;
    }
    
    public Piece getIntakePref() {
        return intakePref;
    }
    
    public void setIntakePref(Piece intakePref) {
        this.intakePref = intakePref;
    }
    
    public RocketLevel getReach() {
        return reach;
    }
    
    public void setReach(RocketLevel reach) {
        this.reach = reach;
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
    
    public int getMechanicalIssues() {
        return mechanicalIssues;
    }
    
    public void setMechanicalIssues(int mechanicalIssues) {
        this.mechanicalIssues = mechanicalIssues;
    }
    
    public HabLevel getStartLevel() {
        return startLevel;
    }
    
    public void setStartLevel(HabLevel startLevel) {
        this.startLevel = startLevel;
    }
    
    public boolean isDriverAuto() {
        return driverAuto;
    }
    
    public void setDriverAuto(boolean driverAuto) {
        this.driverAuto = driverAuto;
    }
    
    public boolean isPathsAuto() {
        return pathsAuto;
    }
    
    public void setPathsAuto(boolean pathsAuto) {
        this.pathsAuto = pathsAuto;
    }
    
    public boolean isNoStratAuto() {
        return noStratAuto;
    }
    
    public void setNoStratAuto(boolean noStratAuto) {
        this.noStratAuto = noStratAuto;
    }
    
    public ArrayList<AutoStrategy> getMainAutoStrat() {
        return mainAutoStrat;
    }
    
    public void setMainAutoStrat(ArrayList<AutoStrategy> mainAutoStrat) {
        this.mainAutoStrat = mainAutoStrat;
    }
    
    public String getNotesAuto() {
        return notesAuto;
    }
    
    public void setNotesAuto(String notesAuto) {
        this.notesAuto = notesAuto;
    }
    
    public ArrayList<TeleopStrategy> getMainTeleopStrat() {
        return mainTeleopStrat;
    }
    
    public void setMainTeleopStrat(ArrayList<TeleopStrategy> mainTeleopStrat) {
        this.mainTeleopStrat = mainTeleopStrat;
    }
    
    public String getNotesTeleop() {
        return notesTeleop;
    }
    
    public void setNotesTeleop(String notesTeleop) {
        this.notesTeleop = notesTeleop;
    }
    
    public Preference getHumanPlayer() {
        return humanPlayer;
    }
    
    public void setHumanPlayer(Preference humanPlayer) {
        this.humanPlayer = humanPlayer;
    }
    
    public Preference getStrategy() {
        return strategy;
    }
    
    public void setStrategy(Preference strategy) {
        this.strategy = strategy;
    }
    
    public String getMainNotes() {
        return mainNotes;
    }
    
    public void setMainNotes(String mainNotes) {
        this.mainNotes = mainNotes;
    }
}
