package base;

import base.controllers.ControlInterface;
import base.controllers.Entry;
import base.lib.FileSystem;
import base.lib.SavingFunctions;
import base.lib.SheetsFunctions;
import base.models.Session;
import base.threads.MatchCollationThread;
import base.threads.PitCollectionThread;
import base.threads.TeamCollationThread;
import com.cpjd.main.TBA;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main{
    public static String eventName = "";
    public static Session currentSession;
    public static ArrayList<Session> recoveredSessions = new ArrayList<>();
    public static MatchCollationThread matchCollationThread;
    public static PitCollectionThread pitCollectionThread;
    public static TeamCollationThread teamCollationThread;
    public static boolean tbaIsSync;
    public static TBA tbaApi;
    
    
    public static void main(String args[]) throws IOException {
    
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();
        
        new File("main_storage/").mkdir();
        new File("main_storage/sessions/").mkdir();
        new File("main_storage/admins/").mkdir();
        
        File sessions = new File("main_storage/sessions/");
        if(sessions.isDirectory() && sessions.listFiles()!=null){
            for(File fl : sessions.listFiles()){
                recoveredSessions.add(SavingFunctions.recoverFullSession(fl));
            }
        }
    
    
        Application.launch(Entry.class, args);
    }
    
    public Main(){}
}
