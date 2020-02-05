package base.models;

import base.Main;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class SecondPit {
    //MAJOR IDENTIFIERS
    public int teamNum;
    public String teamName;
    public String scoutID;
    public LocalTime timeScouted;
    public String interviewee;
    
    //DATA
    public String changes;
    public String issues;
    public String basicNotes;
    public ArrayList<String> images;
    public HashMap<String, String> specificQuestions; // Question, Answer (includes more)
    
    
    public SecondPit(int teamNum_, String teamName_, String scoutEmail_, String timeScouted_, String interviewee_){
        this.teamNum = teamNum_;
        this.teamName = teamName_;
        this.scoutID = scoutEmail_.split("@")[0]; //Get the first part of the email
        this.timeScouted = LocalTime.parse(timeScouted_, DateTimeFormatter.ofPattern("MM/dd/uuuu kk:mm:ss")); //TODO test me s3nd help
        this.interviewee = interviewee_;
    }
    
    @Override
    public boolean equals(Object o){
        if(o.getClass() != SecondPit.class){
            return false;
        }
        
        return ((SecondPit) o).teamName.equals(this.teamName)
                && ((SecondPit) o).teamNum == this.teamNum
                && ((SecondPit) o).scoutID.equals(this.scoutID)
                && ((SecondPit) o).timeScouted.equals(this.timeScouted);
    }
}
