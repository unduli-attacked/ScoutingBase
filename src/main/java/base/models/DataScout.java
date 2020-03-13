package base.models;

import base.Main;
import base.lib.DataClasses.DataScoutMatch;
import base.lib.Enums;
import base.lib.Functions;

import java.time.LocalTime;
import java.util.ArrayList;

public class DataScout implements Comparable, Saveable {
    String name;
    double rank;
    public ArrayList<Match> matches = new ArrayList<>();
    public ArrayList<DataScoutMatch> matchData = new ArrayList<>();
    public ArrayList<Integer> matchesScouted = new ArrayList<>();
    
    public DataScout(String name_, Session session_) {
        this.name = name_;
        session_.standScouts.put(this.name, this);
    }
    
    public DataScout(String name_) {
        new DataScout(name_, Main.currentSession);
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getRank() {
        return this.rank;
    }
    
    /**
     * adds a match to the data scout. does not add to any Match
     *
     * @param match_ a DATA SCOUT data class
     */
    public void addMatch(DataScoutMatch match_) {
        this.matchData.add(match_);
        this.matchesScouted.add((int) match_.matchNum);
    }
    
    public DataScoutMatch submitMatch(int matchNum_) {
        if (!matchesScouted.contains(matchNum_)) {
            return null;
        }
        for (DataScoutMatch match_ : matchData) {
            if ((int) match_.matchNum == matchNum_) {
                return match_;
            }
        }
        return null;
    }
    
    
    @Override
    public int compareTo(Object o) {
        int compareRank = (int) Math.floor(((DataScout) o).getRank());
        int rank = (int) Math.floor(this.getRank());
        
        return compareRank - rank;
        
    }
    
    public void setRank(double rank_) {
        this.rank = rank_;
    }
    
    public void calculateRank(String key_, Object correctData, Object scoutData) {
        if (correctData.equals(scoutData)) {
            this.rank += 6;
        } else {
            switch (key_) {
                case "matchNum":
                case "yellowCard":
                case "redCard":
                    this.rank -= 10;
                    break;
                case "allPos":
                    if (((Enums.Station) correctData).isBlue() == ((Enums.Station) scoutData).isBlue()) {
                        this.rank -= 10;
                    } else {
                        this.rank -= 15;
                    }
                    break;
                case "teamNum":
                    int digitsCorrect = 0;
                    String stringNum = String.valueOf((int) scoutData);
                    String stringCorrect = String.valueOf((int) correctData);
                    for (int i = 0; i < stringCorrect.length() - 1; i++) {
                        try {
                            if (stringNum.charAt(i) == stringCorrect.charAt(i)) {
                                digitsCorrect++;
                            }
                        } catch (NullPointerException e) {
                            digitsCorrect--;
                        }
                    }
                    this.rank -= Math.abs((stringCorrect.length() - digitsCorrect) * 2);
                    break;
                case "absent":
                    this.rank -= 20;
                    break;
                case "startingPosition":
                    this.rank -= Math.abs((double) correctData - (double) scoutData);
                    break;
                case "capacityTimeS1":
                case "capacityTimeS2":
                case "capacityTimeS3":
                case "climbDuration":
                case "activateTimeS2":
                case "activateTimeS3":
                    this.rank -= Math.abs(Functions.getFloatTime((LocalTime) correctData) - Functions.getFloatTime((LocalTime) scoutData));
                    break;
                case "operationalRP":
                case "leveled":
                    this.rank -= 8;
                    break;
                case "numShots":
                case "totalPoints":
                    this.rank -= Math.abs((int) correctData - (int) scoutData);
                    break;
                case "climb":
                case "parked":
                case "wasAssisted":
                case "buddyClimb":
                    this.rank -= 7;
                    break;
                case "disabled":
                    this.rank -= 4;
                    break;
                case "incapacitated":
                    this.rank -= 3;
                    break;
                case "climbRP":
                    this.rank -= 8;
                case "totalRP":
                    this.rank -= Math.abs((int) correctData - (int) scoutData) * 3.5;
                    break;
                default:
                    this.rank -= 5;
                    break;
            }
        }
    }
    
    /**
     * Used in saving raw data
     *
     * @return the file name WITHOUT directory or extension (ex. M14Blue1)
     */
    @Override
    public String getFileName() {
        return "DATA_" + this.getName();
    }
    
    /**
     * Used in saving raw data
     *
     * @return the raw directory WITHOUT slashes (ex. rawMatches)
     */
    @Override
    public String getRawDirName() {
        return "rawScouts";
    }
}
