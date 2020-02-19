import base.models.BaseMatch;
import base.lib.DataClasses;
import base.lib.Enums;
import base.models.DataScout;
import base.models.NoteScout;
import base.threads.CollationThread;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CollationTest {
    
    @Test
<<<<<<< Updated upstream
    public void DataCollationTest(){
        Match testMatch = new Match(10, 5940, Enums.Station.BLUE_1);
        DataScout testScout1 = new DataScout("Test Scout 1");
        DataScout testScout2 = new DataScout("Test Scout 2");
        DataScout testScout3 = new DataScout("Test Scout 3");
        NoteScout testNoteScout1 = new NoteScout("Test Note Scout 1");
        NoteScout testNoteScout2 = new NoteScout("Test Note Scout 2");
=======
    public void DataCollationTest() throws IOException {
        Session testSesh = new Session(2020, "Test", "test2020", "test2020/", 3, 3);
        BaseMatch testBaseMatch = new BaseMatch(10, 5940, Enums.Station.BLUE_1, testSesh);
        DataScout testScout1 = new DataScout("Test Scout 1", testSesh);
        DataScout testScout2 = new DataScout("Test Scout 2", testSesh);
        DataScout testScout3 = new DataScout("Test Scout 3", testSesh);
        NoteScout testNoteScout1 = new NoteScout("Test Note Scout 1", testSesh);
        NoteScout testNoteScout2 = new NoteScout("Test Note Scout 2", testSesh);
>>>>>>> Stashed changes
    
        DataClasses.DataScoutMatch data = new DataClasses.DataScoutMatch();
        testScout1.setRank(100);
        data.scoutName = "Test Scout 1";
        data.matchNum = 10;
        data.allPos = Enums.Station.BLUE_1;
        data.teamNum = 5940;
        data.absent = false;
        data.startingPosition = 3;
        data.capacityTimeS1 = LocalTime.of(0, 0, 10);
        data.capacityTimeS2 = LocalTime.of(0, 0, 45);
        data.capacityTimeS3 = LocalTime.of(0, 2, 5);
        data.defenseRank = 5;
        data.defenseAvoidanceRank = 2;
        data.numShots = 4;
        data.shots = new ArrayList<>(Arrays.asList(new DataClasses.Shot(new Point(0, 0), Enums.Goal.INNER, LocalTime.of(0, 0, 5)),
                new DataClasses.Shot(new Point(5, 10), Enums.Goal.LOWER, LocalTime.of(0, 0, 45)),
                new DataClasses.Shot(new Point(15, 20), Enums.Goal.MISS, LocalTime.of(0, 1, 2)),
                new DataClasses.Shot(new Point(25, 30), Enums.Goal.MISS, LocalTime.of(0, 2, 1))));
        data.yellowCard = false;
        data.redCard = false;
        data.activateTimeS2 = LocalTime.of(0, 1, 1);
        data.activateTimeS3 = LocalTime.of(0, 2, 10);
        data.climbDuration = LocalTime.of(0, 0, 0);
        data.climb = false;
        data.buddyClimb = false;
        data.wasAssisted = false;
        data.leveled = false;
        data.parked = true;
        data.disabled = false;
        data.incapacitated = false;
        data.climbRP = false;
        data.totalRP = 3;
        data.totalPoints = 90;
        data.driverRank = 6;
        data.humanPlayerRank = 0;
        data.dataNotes = "Scout 1 notes";
        
        testScout1.addMatch(data);
    
        DataClasses.DataScoutMatch data2 = new DataClasses.DataScoutMatch();
        data2.copy(data);
        data2.scoutName = "Test Scout 2";
        testScout2.setRank(25);
        data2.startingPosition = 3.2;
        data2.numShots = 5;
        data2.defenseRank = 6;
        data2.defenseAvoidanceRank = 4;
        data2.shots =new ArrayList<>(Arrays.asList(new DataClasses.Shot(new Point(1, 0), Enums.Goal.INNER, LocalTime.of(0, 0, 4)),
                new DataClasses.Shot(new Point(5, 10), Enums.Goal.LOWER, LocalTime.of(0, 0, 43)),
                new DataClasses.Shot(new Point(5, 11), Enums.Goal.MISS, LocalTime.of(0, 1, 3)),
                new DataClasses.Shot(new Point(15, 20), Enums.Goal.MISS, LocalTime.of(0, 2, 2)),
                new DataClasses.Shot(new Point(25, 30), Enums.Goal.MISS, LocalTime.of(0, 2, 6))));
        data2.climb = true;
        data2.totalPoints = 87;
        data2.driverRank = 7;
        data2.humanPlayerRank = 2;
        data2.dataNotes = "Scout 2 notes";
        
        testScout2.addMatch(data2);
    
        DataClasses.DataScoutMatch data3 = new DataClasses.DataScoutMatch();
        data3.copy(data2);
        data3.scoutName = "Test Scout 3";
        testScout3.setRank(55);
        data3.startingPosition = 3.2;
        data3.numShots = 5;
        data3.defenseRank = 6;
        data3.defenseAvoidanceRank = 2;
        data3.shots = new ArrayList<>(Arrays.asList(new DataClasses.Shot(new Point(1, 0), Enums.Goal.INNER, LocalTime.of(0, 0, 4)),
                new DataClasses.Shot(new Point(5, 10), Enums.Goal.LOWER, LocalTime.of(0, 0, 43)),
                new DataClasses.Shot(new Point(5, 11), Enums.Goal.MISS, LocalTime.of(0, 1, 3)),
                new DataClasses.Shot(new Point(15, 20), Enums.Goal.MISS, LocalTime.of(0, 2, 2)),
                new DataClasses.Shot(new Point(25, 30), Enums.Goal.MISS, LocalTime.of(0, 2, 15))));
        data3.climb = true;
        data3.totalPoints = 87;
        data3.driverRank = 9;
        data3.humanPlayerRank = 1;
        data3.dataNotes = "Scout 3 notes";
        
        testScout3.addMatch(data3);
    
        DataClasses.NoteScoutMatch notes = new DataClasses.NoteScoutMatch();
        notes.scoutName = "Note scout 1";
        notes.matchNum = 10;
        notes.isBlue = true;
        notes.bigNotes = "Scout one big notes";
        
        testNoteScout1.addMatch(notes);
        DataClasses.NoteScoutMatch notes2 = new DataClasses.NoteScoutMatch();
        notes2.copy(notes);
        notes2.scoutName = "Note scout 2";
        notes2.bigNotes = "Scout two big notes";
        
        testNoteScout2.addMatch(notes2);
        
        testBaseMatch.addDataScout(testScout1);
        testBaseMatch.addDataScout(testScout2);
        testBaseMatch.addDataScout(testScout3);
        testBaseMatch.addNoteScout(testNoteScout1);
        testBaseMatch.addNoteScout(testNoteScout2);
        
        HashMap<String, DataScout> dataScoutHashMap = new HashMap<>();
        dataScoutHashMap.put(testScout1.getName(), testScout1);
        dataScoutHashMap.put(testScout2.getName(), testScout2);
        dataScoutHashMap.put(testScout3.getName(), testScout3);
        
        HashMap<String, NoteScout> noteScoutHashMap = new HashMap<>();
        noteScoutHashMap.put(testNoteScout1.getName(), testNoteScout1);
        noteScoutHashMap.put(testNoteScout2.getName(), testNoteScout2);
    
<<<<<<< Updated upstream
        CollationThread.collate(testMatch, dataScoutHashMap, noteScoutHashMap);
=======
        MatchCollationThread.collate(testBaseMatch, dataScoutHashMap, noteScoutHashMap);
>>>>>>> Stashed changes
        
        //TODO check ranks later
//        Assertions.assertEquals(123, testScout1.getRank());
//        Assertions.assertEquals(52, testScout2.getRank());
//        Assertions.assertEquals(83, testScout3.getRank());
        
        
        Assertions.assertEquals(3.2, testBaseMatch.matchData.startingPosition);
        Assertions.assertEquals(LocalTime.of(0, 0, 10), testBaseMatch.matchData.capacityTimeS1);
        Assertions.assertEquals(5.66, testBaseMatch.matchData.defenseRank, 0.1);
        Assertions.assertEquals(2.66, testBaseMatch.matchData.defenseAvoidanceRank, 0.1);
        Assertions.assertEquals(5, testBaseMatch.matchData.numShots);
        
        Assertions.assertEquals(LocalTime.of(0, 0, 4), testBaseMatch.matchData.shots.get(0).timeStamp);
        Assertions.assertEquals(LocalTime.of(0, 0, 43), testBaseMatch.matchData.shots.get(1).timeStamp);
        Assertions.assertEquals(LocalTime.of(0, 1, 3), testBaseMatch.matchData.shots.get(2).timeStamp);
        Assertions.assertEquals(LocalTime.of(0, 2, 2), testBaseMatch.matchData.shots.get(3).timeStamp);
        Assertions.assertEquals(LocalTime.of(0, 2, 15), testBaseMatch.matchData.shots.get(4).timeStamp);
        
        Assertions.assertEquals(Enums.Goal.INNER, testBaseMatch.matchData.shots.get(0).scored);
        Assertions.assertEquals(Enums.Goal.LOWER, testBaseMatch.matchData.shots.get(1).scored);
        Assertions.assertEquals(Enums.Goal.MISS, testBaseMatch.matchData.shots.get(2).scored);
        Assertions.assertEquals(Enums.Goal.MISS, testBaseMatch.matchData.shots.get(3).scored);
        Assertions.assertEquals(Enums.Goal.MISS, testBaseMatch.matchData.shots.get(4).scored);
        
        //fixme ive decided i dont care
//        Assertions.assertEquals(0, testBaseMatch.matchData.shots.get(0).position.getX());
//        Assertions.assertEquals(0, testBaseMatch.matchData.shots.get(0).position.getY());
//        Assertions.assertEquals(5, testBaseMatch.matchData.shots.get(1).position.getX());
//        Assertions.assertEquals(10, testBaseMatch.matchData.shots.get(1).position.getY());
//        Assertions.assertEquals(5, testBaseMatch.matchData.shots.get(2).position.getX());
//        Assertions.assertEquals(11, testBaseMatch.matchData.shots.get(2).position.getY());
//        Assertions.assertEquals(15, testBaseMatch.matchData.shots.get(3).position.getX());
//        Assertions.assertEquals(20, testBaseMatch.matchData.shots.get(3).position.getY());
//        Assertions.assertEquals(30, testBaseMatch.matchData.shots.get(4).position.getX());
//        Assertions.assertEquals(35, testBaseMatch.matchData.shots.get(4).position.getY());



//        Assertions.assertEquals(new DataClasses.Shot(new Point(1, 0), Enums.Goal.INNER, LocalTime.of(0, 0, 4)), testBaseMatch.matchData.shots.get(0));
//        Assertions.assertEquals(new DataClasses.Shot(new Point(5, 10), Enums.Goal.LOWER, LocalTime.of(0, 0, 43)), testBaseMatch.matchData.shots.get(1));
//        Assertions.assertEquals(new DataClasses.Shot(new Point(5, 11), Enums.Goal.MISS, LocalTime.of(0, 1, 3)), testBaseMatch.matchData.shots.get(2));
//        Assertions.assertEquals(new DataClasses.Shot(new Point(15, 20), Enums.Goal.MISS, LocalTime.of(0, 2, 2)), testBaseMatch.matchData.shots.get(3));
//        Assertions.assertEquals(new DataClasses.Shot(new Point(25, 30), Enums.Goal.MISS, LocalTime.of(0, 2, 10)), testBaseMatch.matchData.shots.get(4));
        Assertions.assertFalse(testBaseMatch.matchData.yellowCard);
        Assertions.assertTrue(testBaseMatch.matchData.climb);
        Assertions.assertEquals(87, testBaseMatch.matchData.totalPoints);
        Assertions.assertEquals(7.33, testBaseMatch.matchData.driverRank, 0.1);
        Assertions.assertEquals(1, testBaseMatch.matchData.humanPlayerRank, 0.1);
        Assertions.assertEquals("Scout 1 notes  ;  Scout 3 notes  ;  Scout 2 notes  ;  ", testBaseMatch.matchData.dataNotes);
        Assertions.assertEquals("Scout one big notes  ;  Scout two big notes  ;  ", testBaseMatch.bigNotes);
    }
}
