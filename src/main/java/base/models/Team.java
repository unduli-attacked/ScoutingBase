package base.models;

import base.Main;
import base.lib.DataClasses.*;
import base.lib.Enums;
import base.lib.Functions;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Team {
    //BASIC ID
    public int teamNum;
    public String teamName;
    
    //MATCHES
    public ArrayList<Match> matchesScouted = new ArrayList<>();
    public ArrayList<DataScout> dataScouts = new ArrayList<>();
    public ArrayList<NoteScout> noteScouts = new ArrayList<>();
    
    //HISTOGRAMS
    public HashMap<Match, HashMap<Enums.Goal, Integer>> score = new HashMap<>();
    public HashMap<Match, HashMap<Enums.Goal, Integer>> autoScore = new HashMap<>();
    //todo fouls
    public HashMap<Match, LocalTime> cycleTime = new HashMap<>();
    public HashMap<Match, LocalTime> climbTime = new HashMap<>();
    public HashMap<Match, Double> driverRank = new HashMap<>();
    public HashMap<Match, Double> hpRank = new HashMap<>();
    public HashMap<Match, Double> defRank = new HashMap<>();
    public HashMap<Match, Double> defAvoidRank = new HashMap<>();
    
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
    public HashMap<Match, Enums.Dot> autoMovement = new HashMap<>();
    
    /**
     * FAILURE = didn't climb
     * SUCCESS = climbed but didnt level
     * LEVEL_SELF = leveled only themselves
     * LEVEL_TEAM = leveled with multiple robots
     * WAS_LIFTED = lifted to climb BY another bot
     * NO_ATTEMPT = didn't try to climb
     */
    public HashMap<Match, Enums.Dot> climbed = new HashMap<>();
    
    /**
     *
     */
    public HashMap<Match, Enums.Dot> buddyClimb = new HashMap<>();
    public HashMap<Match, Enums.Dot> levelClimb = new HashMap<>();
    
    /**
     * SUCCESS = parked and didn't climb
     * FAILURE = neither parked nor climbed
     * NO_ATTEMPT = climbed
     */
    public HashMap<Match, Enums.Dot> parked = new HashMap<>();
    /**
     * FAILURE = tipped
     * SUCCESS = didn't tip
     * RECOVERED = got untipped
     */
    public HashMap<Match, Enums.Dot> tipped = new HashMap<>();
    /**
     * FAILURE = disabled
     * SUCCESS = didn't disable
     */
    public HashMap<Match, Enums.Dot> disabled = new HashMap<>();
    
    /**
     * FAILURE = broke and threw the match
     * RECOVERED = broke but still played well
     * SUCCESS = didn't break
     */
    public HashMap<Match, Enums.Dot> broke = new HashMap<>();
    
    /**
     * FAILURE = had bad connection issues
     * RECOVERED = had connection issues but fixed them
     * SUCCESS = no issues
     */
    public HashMap<Match, Enums.Dot> connectionIssues = new HashMap<>();
    
    //SCORING MAPS
    public HashMap<Match, ArrayList<Shot>> totalScoring = new HashMap<>();
    public HashMap<Match, ArrayList<Shot>> autoScoring = new HashMap<>();
    
    //PIT
    public Pit mainPit;
    public ArrayList<SecondPit> secondPits;
    public ArrayList<PitScout> pitScouts;
    
    
    
    
    public Team(int teamNum_, String teamName_){
        this.teamNum = teamNum_;
        this.teamName = teamName_;
        Main.currentSession.teams.add(this);
    }
    
    public void addMatch(Match match_){
        this.matchesScouted.add(match_);
        this.dataScouts.addAll(match_.matchScouts);
        this.noteScouts.addAll(match_.noteScouts);
        
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
        for(Shot s : match_.matchData.shots){
            score_.put(s.scored, score_.get(s.scored)+1);
            if(s.timeStamp.isBefore(LocalTime.of(0, 0, 16))) {
                autoScore_.put(s.scored, score_.get(s.scored) + 1);
            }
        }
        this.score.put(match_, score_);
        
        this.autoScore.put(match_, autoScore_);
        
        //todo fouls
    
        float cycleTime_ = 0;
        for(int s=1; s<match_.matchData.shots.size(); s++){
            cycleTime_ += (Functions.getFloatTime(match_.matchData.shots.get(s).timeStamp)-
                            Functions.getFloatTime(match_.matchData.shots.get(s-1).timeStamp));
        }
        cycleTime_ = cycleTime_/match_.matchData.numShots-1;
        this.cycleTime.put(match_, Functions.getLocalTime(cycleTime_));
        
        this.climbTime.put(match_, match_.matchData.climbDuration);
        
        this.driverRank.put(match_, match_.matchData.driverRank);
        
        this.hpRank.put(match_, match_.matchData.humanPlayerRank);
        
        this.defRank.put(match_, match_.matchData.defenseRank);
        
        this.defAvoidRank.put(match_, match_.matchData.defenseAvoidanceRank);
        
        //averages occur in getters
        
        this.autoMovement.put(match_, (match_.matchData.moved) ? Enums.Dot.SUCCESS : Enums.Dot.FAILURE);
        
        //fixme climbed is waiting on the app
        
        if(match_.matchData.wasAssisted){
            this.buddyClimb.put(match_, Enums.Dot.WAS_LIFTED);
        }
        
        //fixme level type waiting on app
        
        if(!match_.matchData.parked){
            if(!this.climbed.get(match_).equals(Enums.Dot.FAILURE)
                && !this.climbed.get(match_).equals(Enums.Dot.NO_ATTEMPT)){
                this.parked.put(match_, Enums.Dot.NO_ATTEMPT);
            }else{
                this.parked.put(match_, Enums.Dot.FAILURE);
            }
        }else{
            this.parked.put(match_, Enums.Dot.SUCCESS);
        }
        
        //fixme need app for tipped
        
        this.disabled.put(match_, (match_.matchData.disabled) ? Enums.Dot.FAILURE : Enums.Dot.SUCCESS);
        
        //fixme need app for recovery
        
        //fixme add data point for connection issues
        
        ArrayList<Shot> tempTotalScore = new ArrayList<>();
        ArrayList<Shot> tempAutoScore = new ArrayList<>();
        for(Shot s : match_.matchData.shots){
            tempTotalScore.add(s);
            
            if(s.timeStamp.isBefore(LocalTime.of(0, 0, 16))){
                tempAutoScore.add(s);
            }
        }
        
        this.totalScoring.put(match_, tempTotalScore);
        this.autoScoring.put(match_, tempAutoScore);
    }
    
    public void addPit(Pit pit_){
        this.mainPit = pit_;
        this.pitScouts.add(Main.currentSession.pitScouts.get(pit_.scoutID));
        for(SecondPit pit : pit_.secondPits.values()){
            this.addSecondPit(pit);
        }
    }
    
    public void addSecondPit(SecondPit pit_){
        this.secondPits.add(pit_);
        this.pitScouts.add(Main.currentSession.pitScouts.get(pit_.scoutID));
    }
}
