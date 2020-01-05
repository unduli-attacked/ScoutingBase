package base;

import base.lib.Enums.MatchData.*;
import base.lib.Enums.PitData.*;
import base.lib.Fun;

import java.util.HashMap;

public class CustomMatch {
    //ID INFO
    int matchNum; //MAIN ID
    String timeStamp; //time of submission
    String scoutName; //name of the scouter
    Station alliancePosition; //position on alliance
    int teamNum; //number of scouted team
    CustomTeam team; //scouted team
    boolean tbaSynced = false; //becomes true when the match is synced with the TBA score breakdown
    
    //HATCHES
    int HPShipGame; //hatch panels in ship during the game
    int HPShipAuto; //hatch panels in ship during auto
    int HPRocketGame; //hatch panels in rocket during the game
    int HPRocketAuto; //hatch panels in rocket during auto
    int HPDropGame; //hatch panels dropped during the game
    int HPDropAuto; //hatch panels dropped during auto
    
    //CARGO
    int CShipGame; //cargo in ship during the game
    int CShipAuto; //cargo in ship during auto
    int CRocketGame; //cargo in rocket during the game
    int CRocketAuto; //cargo in rocket during auto
    int CDropGame; //cargo dropped during the game
    int CDropAuto; //cargo dropped during auto
    
    //HAB
    HabLevel startLevel; //the level the robot started on
    boolean crossedLine; //if the robot crossed the autoline during auto
    HabLevel scaleLevel; //the level the robot scaled to in the endgame
    boolean wasAssisted; //the robot was literally carried to their scaleLevel
    boolean assistedOther; //the robot literally carried another robot
    
    //PENALTIES
    double fouls; //number of fouls
    double techs; //number of tech fouls
    double yellows; //number of yellow cards
    double reds; //number of red cards
    boolean eStopped; //if the robot e-stopped
    boolean broken; //if robot broke
    
    //SCORING
    int points; //final alliance points
    int rankingPoints; //ranking points earned for the alliance
    boolean habRP; //if they got the ranking point for hab climb
    boolean rocketRP; //if they got the ranking point for complete rocket
    int driverRank; //rank of the driver
    
    String matchNotes; //the scouter's notes on the match
    HashMap<String, Object> scoreBreakdown; //the score breakdown from TBA
    
    //AUTO TOTALS
    public int totalAutoPlacement(){
        return HPShipAuto+HPRocketAuto+CShipAuto+CRocketAuto;
    }
    
    public int totalAutoDrops(){
        return HPDropAuto+CDropAuto;
    }
    
    //HATCH TOTALS
    public int totalHatchesPlaced(){
        return HPShipAuto+HPShipGame+HPRocketAuto+HPRocketGame;
    }
    
    public int totalRocketHatchesPlaced(){
        return HPRocketGame+HPRocketAuto;
    }
    
    public int totalShipHatchesPlaced(){
        return HPShipGame+HPShipAuto;
    }
    
    public int totalHatchesDropped(){
        return HPDropAuto+HPDropGame;
    }
    
    //CARGO TOTALS
    public int totalCargoPlaced(){
        return CShipGame+CShipAuto+CRocketGame+CRocketAuto;
    }
    
    public int totalRocketCargoPlaced(){
        return CRocketAuto+CRocketGame;
    }
    
    public int totalShipCargoPlaced(){
        return CShipAuto+CShipGame;
    }
    
    public int totalCargoDropped(){
        return CDropAuto+CDropGame;
    }
    
    
    /**
     * Generate a base.CustomMatch object from a ScoutingWebApp csv
     *
     * @param csvRow the row of the ScoutingWebApp csv to convert
     */
    public CustomMatch(String[] csvRow){
        timeStamp = csvRow[0];
        scoutName = csvRow[1];
        teamNum = Integer.valueOf(csvRow[2]);
        //TODO add method to find team
        switch (Integer.valueOf(csvRow[3])){
            case 1:
                startLevel = HabLevel.ONE;
            case 2:
                startLevel = HabLevel.TWO;
            case 3:
                startLevel = HabLevel.THREE;
            default:
                startLevel = HabLevel.ONE; //defaults to level one
        }
        CRocketAuto = Integer.valueOf(csvRow[4]);
        CShipAuto = Integer.valueOf(csvRow[5]);
        CDropAuto = Integer.valueOf(csvRow[6]);
        HPRocketAuto = Integer.valueOf(csvRow[7]);
        HPShipAuto = Integer.valueOf(csvRow[8]);
        HPDropAuto = Integer.valueOf(csvRow[9]);
        CRocketGame = Integer.valueOf(csvRow[10]);
        CShipGame = Integer.valueOf(csvRow[11]);
        CDropGame = Integer.valueOf(csvRow[12]);
        HPRocketGame = Integer.valueOf(csvRow[13]);
        HPShipGame = Integer.valueOf(csvRow[14]);
        HPDropGame = Integer.valueOf(csvRow[15]);
        crossedLine = (csvRow[16].equals("gotOff")); //TODO confirm string
        wasAssisted = (csvRow[17].equals("assisted")); //TODO confirm
        assistedOther = (csvRow[18].equals("rampBot")); //TODO confirm
        switch (Integer.valueOf(csvRow[19])){
            case 0:
                scaleLevel = HabLevel.NONE;
            case 1:
                scaleLevel = HabLevel.ONE;
            case 2:
                scaleLevel = HabLevel.TWO;
            case 3:
                scaleLevel = HabLevel.THREE;
            default:
                scaleLevel = HabLevel.NONE; //defaults to no climb
        }
        yellows = Integer.valueOf(csvRow[20]);
        reds = Integer.valueOf(csvRow[21]);
        fouls = Integer.valueOf(csvRow[22]);
        techs = Integer.valueOf(csvRow[23]);
        eStopped = (csvRow[24].contains("estop")); //TODO confirm
        broken = (csvRow[25].contains("bork")); //TODO confirm
        points = Integer.valueOf(csvRow[26]); //alliance displayed points
        matchNotes = csvRow[26];
        driverRank = Integer.valueOf(csvRow[29]);
        
    }
    
    /**
     * dummy json convert controller
     */
    public CustomMatch(){}
    
    public void syncTBA(){
        int allPos = Integer.valueOf(alliancePosition.toString().charAt(alliancePosition.toString().length()-1));
        //TODO add scout rank stuff
        
        //FIXME add match find and score breakdown find, assign to scoreBreakdown
        if(points!=(int)scoreBreakdown.get("totalPoints")){
            Fun.reportMatchDataError("Final points are inconsistent",matchNum,alliancePosition);
            points = (int)scoreBreakdown.get("totalPoints");
        }
        
        if(fouls>=(int)scoreBreakdown.get("foulCount")){
            Fun.reportMatchDataError("Foul count is higher than total fouls", matchNum, alliancePosition);
            fouls = (int)scoreBreakdown.get("foulCount");
        }
        
        if(techs>=(int)scoreBreakdown.get("techFoulCount")){
            Fun.reportMatchDataError("Tech foul count is higher than total tech fouls", matchNum, alliancePosition);
            techs = (int)scoreBreakdown.get("techFoulCount");
        }
        
        //TODO add calculated match point verification
        
        if(rankingPoints!=(int)scoreBreakdown.get("rp")){
            Fun.reportMatchDataError("Ranking points are inconsistant", matchNum, alliancePosition);
            rankingPoints=(int)scoreBreakdown.get("rp");
        }
        
        if (startLevel == HabLevel.ONE && ((String) scoreBreakdown.get("preMatchLevelRobot"+(allPos))).equals("HabLevel2")) {
            Fun.reportMatchDataError("Start hab level is incorrect", matchNum, alliancePosition);
            startLevel=HabLevel.TWO;
        }else if(startLevel == HabLevel.TWO &&((String) scoreBreakdown.get("preMatchLevelRobot"+(allPos))).equals("HabLevel1")){
            Fun.reportMatchDataError("Start hab level is incorrect", matchNum, alliancePosition);
            startLevel=HabLevel.ONE;
        }
        
        if (crossedLine && (((String)scoreBreakdown.get("habLineRobot"+allPos)).equals("None"))){
            Fun.reportMatchDataError("Robot is incorrectly reported as crossing hab line",matchNum, alliancePosition);
            crossedLine=!crossedLine;
        }else if(!crossedLine && (((String)scoreBreakdown.get("habLineRobot"+allPos)).equals("CrossedHabLineInSandstorm"))){
            Fun.reportMatchDataError("Robot is incorrectly reported as not moving during sandstorm",matchNum, alliancePosition);
            crossedLine=!crossedLine;
        }
        
        if(scoreBreakdown.get("endgameRobot"+allPos)!=null &&
                scaleLevel.basic!=((String)scoreBreakdown.get("endgameRobot"+allPos)).charAt(((String) scoreBreakdown.get("endgameRobot"+allPos)).length()-1)){
            Fun.reportMatchDataError("Climb level is incorrect",matchNum, alliancePosition);
            switch (((String)scoreBreakdown.get("endgameRobot"+allPos)).charAt(((String) scoreBreakdown.get("endgameRobot"+allPos)).length()-1)){
                case 1:
                    scaleLevel=HabLevel.ONE;
                case 2:
                    scaleLevel=HabLevel.TWO;
                case 3:
                    scaleLevel=HabLevel.THREE;
                default:
                    scaleLevel=HabLevel.NONE;
            }
        }
        
        if((habRP!=(boolean)scoreBreakdown.get("habDockingRankingPoint"))){
            Fun.reportMatchDataError("Hab ranking point is incorrect",matchNum,alliancePosition);
            habRP=!habRP;
        }
        
        if(rocketRP!=(boolean)scoreBreakdown.get("completeRocketRankingPoint")){
            Fun.reportMatchDataError("Rocket ranking point is incorrect",matchNum,alliancePosition);
            rocketRP=!rocketRP;
        }
    }
}
