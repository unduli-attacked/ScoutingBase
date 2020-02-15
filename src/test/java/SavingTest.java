import base.Main;
import base.lib.FileSystem;
import base.lib.SavingFunctions;
import base.lib.SheetsFunctions;
import base.models.Pit;
import base.models.Session;
import base.threads.PitCollectionThread;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SavingTest {
    
    @Test
    public void SingleSaveTest(){
        Session testSesh = new Session(2020, "Test", "test2020", "test2020/", 3, 3);
        List<Object> slkdfj = new ArrayList<>();
        try {
            slkdfj = SheetsFunctions.getData("1b2jZZsnmQ71mW3oEc0Sr2sQ3aDmlQOeIyL8Uws0gnNk", "Main Pit Data", 4, "AO");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        Pit testPit = PitCollectionThread.addPit(slkdfj, testSesh);
        testSesh.pits.put(testPit.teamNum, testPit);
        testPit.saveRaw(testSesh);
        
        Pit test2ElectricBoogaloo = SavingFunctions.recoverSaveable(new File(testSesh.directory+ FileSystem.RAW_PITS+testPit.getFileName()+".json"), Pit.class);
    
        Assertions.assertEquals(testPit.teamNum, test2ElectricBoogaloo.teamNum);
        Assertions.assertEquals(testPit.timeScouted, test2ElectricBoogaloo.timeScouted);
        Assertions.assertEquals(testPit.visionPickup.get(0), test2ElectricBoogaloo.visionPickup.get(0));
        Assertions.assertEquals(testPit.controlPanel, test2ElectricBoogaloo.controlPanel);
        Assertions.assertEquals(testPit.teleopPref, test2ElectricBoogaloo.teleopPref);
        Assertions.assertEquals(testPit, test2ElectricBoogaloo);
    }
    
    @Test
    public void FullSaveTest(){
        //TODO waiting on spreadsheet data from app
    }
}
