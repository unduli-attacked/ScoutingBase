import com.cpjd.main.TBA;
import com.cpjd.models.teams.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TBATest {
    
    @Test
    public void simpleSyncTest(){
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        TBA tbaApi = new TBA();
        Team tem = tbaApi.getTeam(5940);
        
        long year = tem.getRookieYear();
    
        Assertions.assertEquals(2016, year);
    }
}
