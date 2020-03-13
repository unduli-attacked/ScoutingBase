package base.threads;

import base.Main;
import base.lib.ColumnMappings;
import base.lib.DataClasses.*;
import base.lib.Enums;
import base.lib.Functions;
import base.lib.SheetsFunctions;
import base.models.Match;
import base.models.Pit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static base.lib.ColumnMappings.AppData.*;

/**
 * Collects data from a Google Sheet and saves it in a Base-readable format
 *
 * @author Jocelyn McHugo
 * @version 2020.1
 * @since 2020-03-13
 */
public class DataCollectionThread extends Thread {
    
    /**
     * The last row that the program has checked. Defaults to 1 to account for headers.
     */
    public int lastSavedRow = 1;
    
    /**
     * Repeatedly checks for new data from the current {@link base.models.Session}'s spreadsheet. If data is found,
     * converts it into a {@link DataScoutMatch} using {@code saveMatch} and saves it
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            List<Object> temp = null;
            try {
                temp = SheetsFunctions.getData(Main.currentSession.spreadsheetID, Main.currentSession.dataTab, lastSavedRow + 1, Main.currentSession.finalDataCol);
            } catch (Exception e) {
            
            }
            if (temp != null) {
                if (Functions.findIndivMatch(Integer.parseInt((String) temp.get(MATCH_NUM.val)), Integer.parseInt((String) temp.get(TEAM_NUM.val)), Main.currentSession) == null) {
                    DataScoutMatch tempMatch = saveMatch(temp);
                    //TODO add to associated scout and Session
                }
                lastSavedRow++;
            }
            
            yield();
        }
    }
    
    /**
     * Takes a sheet output list, converts it to strings, and matches it to values in a {@link DataScoutMatch}
     *
     * @param temp_ object list from {@link SheetsFunctions}
     * @return the object list in {@link DataScoutMatch} form
     */
    public DataScoutMatch saveMatch(List<Object> temp_) {
        ArrayList<String> temp = new ArrayList<>();
        for (Object o : temp_) {
            temp.add((String) o);
        }
        DataScoutMatch tempMatch = new DataScoutMatch();
        int len = Integer.parseInt(temp.get(IMG_DIM.val).split(",")[0].substring(1)); //todo tst
        int hei = Integer.parseInt(temp.get(IMG_DIM.val).split(",")[1].substring(0, temp.get(IMG_DIM.val).split(",")[1].length() - 1));
        int startTimeMS = Integer.parseInt(temp.get(MATCH_START.val));
        String[] shots = temp.get(SHOTS.val).split(";");
        ArrayList<Shot> shoots = new ArrayList<>();
        for (String str : shots) {
            //FIXME currently relative to screen size
            Shot finShot = new Shot();
            String shot = str.substring(1, str.length() - 1);
            String[] elements = shot.split(";");
            
            //LOCATION
            int y, x;
            try {
                x = Integer.parseInt(elements[0].split(",")[0].substring(1));
                y = Integer.parseInt(elements[0].split(",")[1].substring(1, elements[0].split(",")[1].length() - 1));
            } catch (NumberFormatException e) {
                continue;
            }
            finShot.position = new Point(x, y);
            
            //TYPE
            switch (elements[1]) {
                case "MISS":
                    finShot.scored = Enums.Goal.MISS;
                    break;
                case "LOWER":
                    finShot.scored = Enums.Goal.LOWER;
                    break;
                case "OUTER":
                    finShot.scored = Enums.Goal.OUTER;
                    break;
                case "INNER":
                    finShot.scored = Enums.Goal.INNER;
                    break;
                default:
                    finShot.scored = Enums.Goal.MISS;
                    break;
            }
            
            //TIME
            finShot.timeStamp = Functions.getLocalTime((float) Integer.parseInt(elements[2]) - startTimeMS);
            
            shoots.add(finShot);
        }
        tempMatch.shots = shoots;
        tempMatch.climbDuration = Functions.getLocalTime((float) Integer.parseInt(temp.get(CLIMB_END.val)) - (float) Integer.parseInt(temp.get(CLIMB_START.val)));
        tempMatch.scoutName = temp.get(ID.val);
        tempMatch.teamNum = Integer.parseInt(temp.get(TEAM_NUM.val));
        tempMatch.matchNum = Integer.parseInt(temp.get(MATCH_NUM.val));
        switch (temp.get(ALL_POS.val)) {
            case "Red1":
                tempMatch.allPos = Enums.Station.RED_1;
                break;
            case "Red2":
                tempMatch.allPos = Enums.Station.RED_2;
                break;
            case "Red3":
                tempMatch.allPos = Enums.Station.RED_3;
                break;
            case "Blue1":
                tempMatch.allPos = Enums.Station.BLUE_1;
                break;
            case "Blue2":
                tempMatch.allPos = Enums.Station.BLUE_2;
                break;
            case "Blue3":
                tempMatch.allPos = Enums.Station.BLUE_3;
                break;
        }
        tempMatch.absent = temp.get(ABSENT.val).equals("on");
        tempMatch.startingPosition = Integer.parseInt(temp.get(START_POS.val));
        tempMatch.moved = temp.get(OFF_LINE.val).equals("on");
        tempMatch.fouls.put(Enums.Foul.TRENCH_TECH, Integer.parseInt(temp.get(TRENCH_TECH.val)));
        tempMatch.fouls.put(Enums.Foul.PIN_FOUL, Integer.parseInt(temp.get(PIN_FOUL.val)));
        tempMatch.fouls.put(Enums.Foul.HUMAN_FOUL, Integer.parseInt(temp.get(HUMAN_FOUL.val)));
        tempMatch.fouls.put(Enums.Foul.ZONE_TECH, Integer.parseInt(temp.get(ZONE_FOUL.val)));
        tempMatch.fouls.put(Enums.Foul.GEN_TECH, Integer.parseInt(temp.get(TECH_FOUL.val)));
        tempMatch.fouls.put(Enums.Foul.GEN_FOUL, Integer.parseInt(temp.get(FOUL.val)));
        tempMatch.climb = temp.get(CLIMB.val).equals("on");
        tempMatch.buddyClimb = temp.get(BUDDY.val).equals("on");
        tempMatch.wasAssisted = temp.get(ASSIST.val).equals("on");
        tempMatch.leveled = temp.get(LEVEL.val).equals("on");
        tempMatch.parked = temp.get(PARKED.val).equals("on");
        tempMatch.climbRP = temp.get(CLIMB_RP.val).equals("on");
        tempMatch.disabled = temp.get(ESTOP.val).equals("on");
        tempMatch.incapacitated = temp.get(BROKE.val).equals("on");
        tempMatch.yellowCard = temp.get(YELLOW.val).equals("on");
        tempMatch.redCard = temp.get(RED.val).equals("on");
        tempMatch.climb = temp.get(CLIMB.val).equals("on");
        tempMatch.driverRank = Integer.parseInt(temp.get(DRIVE_RANK.val));
        tempMatch.humanPlayerRank = Integer.parseInt(temp.get(HP_RANK.val));
        tempMatch.defenseRank = Integer.parseInt(temp.get(DEF_RANK.val));
        tempMatch.defenseAvoidanceRank = Integer.parseInt(temp.get(DEF_AVOID_RANK.val));
        tempMatch.dataNotes = temp.get(NOTES.val);
        return tempMatch;
    }
}
