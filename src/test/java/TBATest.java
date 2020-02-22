import com.cpjd.main.TBA;
import com.cpjd.models.matches.Match;
import com.cpjd.models.teams.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TBATest {
    
    @Test
    public void simpleSyncTest(){
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        TBA tbaApi = new TBA();
        Team tem = tbaApi.getTeam(5940);
        
        long year = tem.getRookieYear();
    
        Assertions.assertEquals(2016, year);
    }
    
    @Test
    public void matchPullTest(){
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        TBA tbaApi = new TBA();
        Match metch = tbaApi.getMatch("2020week0_qm2");
        HashMap<String, Object> breakdown = metch.getBlueScoreBreakdown();
        for(String key : breakdown.keySet()){
            System.out.printf("%40s  |  %-100s%n", key, breakdown.get(key));
        }
    }
}
