package base.threads;

import base.Main;
import base.models.Match;
import com.cpjd.models.events.Event;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Retrieves match breakdowns from The Blue Alliance and links them to {@link Match} instances
 *
 * @author Jocelyn McHugo
 * @version 2020.1
 * @since 2020-03-13
 */
public class TBACollectionThread extends Thread {
    
    /**
     * Continuously checks each {@link Match} in the current Session for associated match breakdowns on The Blue
     * Alliance. If a breakdown is found, it is added to the match
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            for (Match match_ : Main.currentSession.matches.values()) {
                HashMap<String, Object> breakdown;
                if (match_.allPos.isBlue()) {
                    breakdown = Main.tbaApi.getMatch(Main.currentSession.tbaEventKey + "_qm" + match_.matchNum).getBlueScoreBreakdown();
                } else {
                    breakdown = Main.tbaApi.getMatch(Main.currentSession.tbaEventKey + "_qm" + match_.matchNum).getRedScoreBreakdown();
                }
                if (breakdown != null) {
                    match_.matchBreakdown = breakdown;
                }
            }
            yield();
        }
    }
}
