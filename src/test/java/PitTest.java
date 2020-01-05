import base.CustomPit;
import base.CustomTeam;
import org.junit.jupiter.api.Test; //TODO load junit when online

import java.util.ArrayList;
import java.util.Arrays;
import base.lib.Enums.PitData.*;

public class PitTest {

    @Test
    public void appendPitTest(){
        CustomPit startPit = new CustomPit();
        startPit.setTeamNum(5940);
        startPit.setTeamName("BREAD");
        startPit.setScoutName("Nou");
        startPit.setNicknames(new ArrayList<>(Arrays.asList("Croissant")));
        startPit.setClimbLevel(HabLevel.THREE);
        startPit.setIntakeType(Piece.BOTH);
        startPit.setIntakePref(Piece.HATCH);
        startPit.setReach(RocketLevel.THREE);
        startPit.setHasCamera(true);
        startPit.setHasAlignment(true);
        startPit.setHasPresets(true);
        startPit.setPiecesPMatch(11);
        startPit.setCycleTime(12);
        startPit.setMechanicalIssues(7);
        startPit.setStartLevel(HabLevel.ONE);
        startPit.setDriverAuto(true);
        startPit.setPathsAuto(true);
        startPit.setNoStratAuto(false);
        startPit.setMainAutoStrat(new ArrayList<>(Arrays.asList(AutoStrategy.CLOSE_ROCKET_HATCH, AutoStrategy.FAR_ROCKET_HATCH, AutoStrategy.FRONT_SHIP_HATCH)));
        startPit.setNotesAuto("Hi");
        startPit.setMainTeleopStrat(new ArrayList<>(Arrays.asList(TeleopStrategy.ROCKET_HATCH, TeleopStrategy.ROCKET_CARGO)));
        startPit.setNotesTeleop("Hello");
        startPit.setHumanPlayer(Preference.INTEGRAL);
        startPit.setStrategy(Preference.IDEAL);
        startPit.setMainNotes("Good team, mostly");


        CustomTeam testTeam = new CustomTeam(5940);
        testTeam.addPit(startPit);
        System.out.println(testTeam.getLatestScout());
        CustomPit editedPit = startPit;
        editedPit.setScoutName("Uwu");
        testTeam.addPit(editedPit);
        System.out.println(testTeam.getLatestScout());


    }
}
