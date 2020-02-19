import base.lib.FileSystem;
import base.lib.SavingFunctions;
import base.lib.SheetsFunctions;
import base.models.BasePit;
import base.models.Session;
import base.threads.PitCollectionThread;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

public class SavingTest {
    
    @Test
    public void SingleSaveTest() throws IOException {
        Session testSesh = new Session(2020, "Test", "test2020", "test2020/", 3, 3);
        List<Object> slkdfj = new ArrayList<>();
        try {
            slkdfj = SheetsFunctions.getData("1b2jZZsnmQ71mW3oEc0Sr2sQ3aDmlQOeIyL8Uws0gnNk", "Main BasePit Data", 4, "AO");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        BasePit testBasePit = PitCollectionThread.addPit(slkdfj, testSesh);
        testSesh.pits.put(testBasePit.teamNum, testBasePit);
        testBasePit.saveRaw(testSesh);
        
        testSesh.saveSession();
        Session resultSesh = SavingFunctions.recoverFullSession(new File("main_storage/sessions/MAINtest2020.json"));
        
        BasePit test2ElectricBoogaloo = SavingFunctions.recoverSaveable(new File(testSesh.directory+ FileSystem.RAW_PITS+ testBasePit.getFileName()+".json"), BasePit.class);
    
        Assertions.assertEquals(testBasePit.teamNum, test2ElectricBoogaloo.teamNum);
        Assertions.assertEquals(testBasePit.timeScouted, test2ElectricBoogaloo.timeScouted);
        Assertions.assertEquals(testBasePit.visionPickup.get(0), test2ElectricBoogaloo.visionPickup.get(0));
        Assertions.assertEquals(testBasePit.controlPanel, test2ElectricBoogaloo.controlPanel);
        Assertions.assertEquals(testBasePit.teleopPref, test2ElectricBoogaloo.teleopPref);
        Assertions.assertEquals(testBasePit, test2ElectricBoogaloo);
        
        Assertions.assertEquals(testSesh.eventName, resultSesh.eventName);
    }
    
    @Test
    public void FullSaveTest(){
        //TODO waiting on spreadsheet data from app
    }
}
