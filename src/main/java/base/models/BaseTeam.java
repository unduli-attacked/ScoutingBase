package base.models;

import base.Main;
import base.lib.DataClasses.*;
import base.lib.Enums;
import base.lib.Functions;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

<<<<<<< Updated upstream:src/main/java/base/models/Team.java
public class Team {
=======
public class BaseTeam implements Saveable{
>>>>>>> Stashed changes:src/main/java/base/models/BaseTeam.java
    //BASIC ID
    public int teamNum;
    public String teamName;
    
    //MATCHES
    public ArrayList<BaseMatch> matchesScouted = new ArrayList<>();
    public ArrayList<DataScout> dataScouts = new ArrayList<>();
    public ArrayList<NoteScout> noteScouts = new ArrayList<>();
    
    //HISTOGRAMS
    public HashMap<BaseMatch, HashMap<Enums.Goal, Integer>> score = new HashMap<>();
    public HashMap<BaseMatch, HashMap<Enums.Goal, Integer>> autoScore = new HashMap<>();
    //todo fouls
    public HashMap<BaseMatch, LocalTime> cycleTime = new HashMap<>();
    public HashMap<BaseMatch, LocalTime> climbTime = new HashMap<>();
    public HashMap<BaseMatch, Double> driverRank = new HashMap<>();
    public HashMap<BaseMatch, Double> hpRank = new HashMap<>();
    public HashMap<BaseMatch, Double> defRank = new HashMap<>();
    public HashMap<BaseMatch, Double> defAvoidRank = new HashMap<>();
    
    //AVERAGES
    private LocalTime avCycleT;
    private LocalTime avClimbT;
    private double avDriverRank;
    private double avHPRank;
    private double avDefRank;
    private double avDefAvoidRank;
    
    //Y/Ns
    /**
     * FAILURE = didn't move
     * SUCCESS = moved
     */
    public HashMap<BaseMatch, Enums.Dot> autoMovement = new HashMap<>();
    
    /**
     * FAILURE = didn't climb
     * SUCCESS = climbed but didnt level
     * LEVEL_SELF = leveled only themselves
     * LEVEL_TEAM = leveled with multiple robots
     * WAS_LIFTED = lifted to climb BY another bot
     * NO_ATTEMPT = didn't try to climb
     */
    public HashMap<BaseMatch, Enums.Dot> climbed = new HashMap<>();
    
    /**
     *
     */
    public HashMap<BaseMatch, Enums.Dot> buddyClimb = new HashMap<>();
    public HashMap<BaseMatch, Enums.Dot> levelClimb = new HashMap<>();
    
    /**
     * SUCCESS = parked and didn't climb
     * FAILURE = neither parked nor climbed
     * NO_ATTEMPT = climbed
     */
    public HashMap<BaseMatch, Enums.Dot> parked = new HashMap<>();
    /**
     * FAILURE = tipped
     * SUCCESS = didn't tip
     * RECOVERED = got untipped
     */
    public HashMap<BaseMatch, Enums.Dot> tipped = new HashMap<>();
    /**
     * FAILURE = disabled
     * SUCCESS = didn't disable
     */
    public HashMap<BaseMatch, Enums.Dot> disabled = new HashMap<>();
    
    /**
     * FAILURE = broke and threw the match
     * RECOVERED = broke but still played well
     * SUCCESS = didn't break
     */
    public HashMap<BaseMatch, Enums.Dot> broke = new HashMap<>();
    
    /**
     * FAILURE = had bad connection issues
     * RECOVERED = had connection issues but fixed them
     * SUCCESS = no issues
     */
    public HashMap<BaseMatch, Enums.Dot> connectionIssues = new HashMap<>();
    
    //SCORING MAPS
    public HashMap<BaseMatch, ArrayList<Shot>> totalScoring = new HashMap<>();
    public HashMap<BaseMatch, ArrayList<Shot>> autoScoring = new HashMap<>();
    
    //PIT
    public BasePit mainBasePit;
    public ArrayList<SecondPit> secondPits;
    public ArrayList<PitScout> pitScouts;
    
    
    
    
    public BaseTeam(int teamNum_, String teamName_){
        this.teamNum = teamNum_;
        this.teamName = teamName_;
        Main.currentSession.teams.add(this);
    }
    
    public void addMatch(BaseMatch baseMatch_){
        this.matchesScouted.add(baseMatch_);
        this.dataScouts.addAll(baseMatch_.matchScouts);
        this.noteScouts.addAll(baseMatch_.noteScouts);
        
        HashMap<Enums.Goal, Integer> score_ = new HashMap<>();
        score_.put(Enums.Goal.MISS, 0);
        score_.put(Enums.Goal.LOWER, 0);
        score_.put(Enums.Goal.OUTER, 0);
        score_.put(Enums.Goal.INNER, 0);
    
        HashMap<Enums.Goal, Integer> autoScore_ = new HashMap<>();
        autoScore_.put(Enums.Goal.MISS, 0);
        autoScore_.put(Enums.Goal.LOWER, 0);
        autoScore_.put(Enums.Goal.OUTER, 0);
        autoScore_.put(Enums.Goal.INNER, 0);
        for(Shot s : baseMatch_.matchData.shots){
            score_.put(s.scored, score_.get(s.scored)+1);
            if(s.timeStamp.isBefore(LocalTime.of(0, 0, 16))) {
                autoScore_.put(s.scored, score_.get(s.scored) + 1);
            }
        }
        this.score.put(baseMatch_, score_);
        
        this.autoScore.put(baseMatch_, autoScore_);
        
        //todo fouls
    
        float cycleTime_ = 0;
        for(int s = 1; s< baseMatch_.matchData.shots.size(); s++){
            cycleTime_ += (Functions.getFloatTime(baseMatch_.matchData.shots.get(s).timeStamp)-
                            Functions.getFloatTime(baseMatch_.matchData.shots.get(s-1).timeStamp));
        }
        cycleTime_ = cycleTime_/ baseMatch_.matchData.numShots-1;
        this.cycleTime.put(baseMatch_, Functions.getLocalTime(cycleTime_));
        
        this.climbTime.put(baseMatch_, baseMatch_.matchData.climbDuration);
        
        this.driverRank.put(baseMatch_, baseMatch_.matchData.driverRank);
        
        this.hpRank.put(baseMatch_, baseMatch_.matchData.humanPlayerRank);
        
        this.defRank.put(baseMatch_, baseMatch_.matchData.defenseRank);
        
        this.defAvoidRank.put(baseMatch_, baseMatch_.matchData.defenseAvoidanceRank);
        
        //averages occur in getters
        
        this.autoMovement.put(baseMatch_, (baseMatch_.matchData.moved) ? Enums.Dot.SUCCESS : Enums.Dot.FAILURE);
        
        //fixme climbed is waiting on the app
        
        if(baseMatch_.matchData.wasAssisted){
            this.buddyClimb.put(baseMatch_, Enums.Dot.WAS_LIFTED);
        }
        
        //fixme level type waiting on app
        
        if(!baseMatch_.matchData.parked){
            if(!this.climbed.get(baseMatch_).equals(Enums.Dot.FAILURE)
                && !this.climbed.get(baseMatch_).equals(Enums.Dot.NO_ATTEMPT)){
                this.parked.put(baseMatch_, Enums.Dot.NO_ATTEMPT);
            }else{
                this.parked.put(baseMatch_, Enums.Dot.FAILURE);
            }
        }else{
            this.parked.put(baseMatch_, Enums.Dot.SUCCESS);
        }
        
        //fixme need app for tipped
        
        this.disabled.put(baseMatch_, (baseMatch_.matchData.disabled) ? Enums.Dot.FAILURE : Enums.Dot.SUCCESS);
        
        //fixme need app for recovery
        
        //fixme add data point for connection issues
        
        ArrayList<Shot> tempTotalScore = new ArrayList<>();
        ArrayList<Shot> tempAutoScore = new ArrayList<>();
        for(Shot s : baseMatch_.matchData.shots){
            tempTotalScore.add(s);
            
            if(s.timeStamp.isBefore(LocalTime.of(0, 0, 16))){
                tempAutoScore.add(s);
            }
        }
        
        this.totalScoring.put(baseMatch_, tempTotalScore);
        this.autoScoring.put(baseMatch_, tempAutoScore);
    }
    
<<<<<<< Updated upstream:src/main/java/base/models/Team.java
    public void addPit(Pit pit_){
        this.mainPit = pit_;
        this.pitScouts.add(Main.currentSession.pitScouts.get(pit_.scoutID));
=======
    public void addPit(BasePit basePit_){
        this.mainBasePit = basePit_;
        this.pitScouts.add(Main.currentSession.pitScouts.get(basePit_.scoutID));
        for(SecondPit pit : basePit_.secondPits.values()){
            this.addSecondPit(pit);
        }
>>>>>>> Stashed changes:src/main/java/base/models/BaseTeam.java
    }
    
    public void addSecondPit(SecondPit pit_){
        this.secondPits.add(pit_);
        this.pitScouts.add(Main.currentSession.pitScouts.get(pit_.scoutID));
    }
}
