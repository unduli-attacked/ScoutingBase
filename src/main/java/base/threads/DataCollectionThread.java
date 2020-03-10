package base.threads;

import base.Main;
import base.lib.Functions;
import base.lib.SheetsFunctions;
import base.models.Match;
import base.models.Pit;

import java.util.List;

import static base.lib.ColumnMappings.AppData.*;

public class DataCollectionThread extends Thread {
    public int lastSavedRow = 1;
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            List<Object> temp = null;
            try {
                temp = SheetsFunctions.getData(Main.currentSession.spreadsheetID, Main.currentSession.dataTab, lastSavedRow + 1, Main.currentSession.finalDataCol);
            } catch (Exception e) {
            
            }
            if (temp != null) {
                if (Functions.findIndivMatch(Integer.valueOf(temp.get(MATCH_NUM.val)), Integer.valueOf(temp.get(TEAM_NUM.val)), Main.currentSession) == null) {
                
                }
                lastSavedRow++;
            }
            
            yield();
        }
    }
    
    public Match saveMatch(List<String> temp){
        Match tempMatch = new Match();
    }
}
