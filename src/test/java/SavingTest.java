import base.Main;
import base.lib.*;
import base.models.Pit;
import base.models.Session;
import base.threads.PitCollectionThread;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DatasetUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SavingTest {
    
    @Test
    public void SingleSaveTest() throws IOException {
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
        
        testSesh.saveSession();
        Session resultSesh = SavingFunctions.recoverFullSession(new File("main_storage/sessions/MAINtest2020.json"));
        
        Pit test2ElectricBoogaloo = SavingFunctions.recoverSaveable(new File(testSesh.directory+ FileSystem.RAW_PITS+testPit.getFileName()+".json"), Pit.class);
    
        Assertions.assertEquals(testPit.teamNum, test2ElectricBoogaloo.teamNum);
        Assertions.assertEquals(testPit.timeScouted, test2ElectricBoogaloo.timeScouted);
        Assertions.assertEquals(testPit.visionPickup.get(0), test2ElectricBoogaloo.visionPickup.get(0));
        Assertions.assertEquals(testPit.controlPanel, test2ElectricBoogaloo.controlPanel);
        Assertions.assertEquals(testPit.teleopPref, test2ElectricBoogaloo.teleopPref);
        Assertions.assertEquals(testPit, test2ElectricBoogaloo);
        
        Assertions.assertEquals(testSesh.eventName, resultSesh.eventName);
    }
    
    @Test
    public void FullSaveTest(){
        //TODO waiting on spreadsheet data from app
    }
    
    @Test
    public void ChartTest() throws FileNotFoundException, DocumentException {
        Document doc = new Document(PageSize.LETTER);
        double[][] scoreLocData = {
                {10, 15, 20},
                {20, 30, 40},
                {40, 50, 60},
                {60, 70, 80}
        };
        JFreeChart scoreLocChart = SavingFunctions.createChart(
               scoreLocData, new String[]{"1", "2", "3"},
                new Color[]{Colors.innerGoal, Colors.outerGoal, Colors.lowerGoal, Colors.miss},
                new String[]{Enums.Goal.INNER.toString(), Enums.Goal.OUTER.toString(), Enums.Goal.LOWER.toString(), Enums.Goal.MISS.toString()},
                "Score Location", "Match", "# Scored");
        SavingFunctions.drawChart(doc, new File("test2020/"+FileSystem.PDF_TEAMS+"TEST.pdf"), scoreLocChart, 114, 114, 100, 100);
    }
}
