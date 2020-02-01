package base.models;

import base.lib.Enums;

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
    
    //HISTOGRAMS
    public HashMap<Match, HashMap<Enums.Goal, Integer>> scoreLoc = new HashMap<>();
    public HashMap<Match, HashMap<Enums.Goal, Integer>> autoScore = new HashMap<>();
    //todo fouls
    public HashMap<Match, LocalTime> cycleTime = new HashMap<>();
    public HashMap<Match, LocalTime> climbTime = new HashMap<>();
    public HashMap<Match, Integer> driverRank = new HashMap<>();
    public HashMap<Match, Integer> hpRank = new HashMap<>();
    public HashMap<Match, Integer> defRank = new HashMap<>();
    
    //AVERAGES
    public LocalTime avCycleT;
    public LocalTime avClimbT;
    public int avDriverRank;
    public int avHPRank;
    public int avDefRank;
    
    //Y/Ns
    public HashMap<Match, Enums.Dot> autoMovement = new HashMap<>();
    public HashMap<Match, Enums.Dot> climbed = new HashMap<>();
    public HashMap<Match, Enums.Dot> buddyClimb = new HashMap<>();
    public HashMap<Match, Enums.Dot> levelClimb = new HashMap<>();
    public HashMap<Match, Enums.Dot> parked = new HashMap<>();
    public HashMap<Match, Enums.Dot> tipped = new HashMap<>();
    public HashMap<Match, Enums.Dot> disabled = new HashMap<>();
    public HashMap<Match, Enums.Dot> broke = new HashMap<>();
    public HashMap<Match, Enums.Dot> connectionIssues = new HashMap<>();
    
    //SCORING MAPS
    public HashMap<Match, HashMap<Point, Enums.Goal>> totalScoring = new HashMap<>();
    public HashMap<Match, HashMap<Point, Enums.Goal>> autoScoring = new HashMap<>();
    
    //PIT Y/N
    public Pit mainPit;
    public ArrayList<SecondPit> secondPits;
    
    
}
