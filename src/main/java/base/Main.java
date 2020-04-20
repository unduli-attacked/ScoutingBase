package base;

import base.controllers.Entry;
import base.lib.SavingFunctions;
import base.models.Session;
import base.threads.*;
import com.cpjd.main.TBA;
import javafx.application.Application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main execution class for the Scouting Base
 *
 * @author Jocelyn McHugo
 * @version 2020.1
 * @since 2020-03-13
 */
public class Main {
    /**
     * The name of the current event, set when a session is selected
     */
    public static String eventName = "";
    
    /**
     * The current Session for use throughout the project, set when a session is selected
     */
    public static Session currentSession;
    
    /**
     * All Sessions recovered from .json files in main_storage. Filled when the UI is launched
     */
    public static ArrayList<Session> recoveredSessions = new ArrayList<>();
    
    //FIXME should these be in the session?
    
    /**
     * Knockoff-singleton MatchCollectionThread for use throughout the project.
     */
    public static MatchCollationThread matchCollationThread = new MatchCollationThread();
    
    /**
     * Knockoff-singleton MatchCollectionThread for use throughout the project.
     */
    public static PitCollectionThread pitCollectionThread = new PitCollectionThread();
    
    /**
     * Knockoff-singleton PitCollectionThread for use throughout the project.
     */
    public static TeamCollationThread teamCollationThread = new TeamCollationThread();
    
    /**
     * Knockoff-singleton TeamCollationThread for use throughout the project.
     */
    public static TBACollectionThread tbaCollectionThread = new TBACollectionThread();
    
    /**
     * Knockoff-singleton DataCollectionThread for use throughout the project.
     */
    public static DataCollectionThread dataCollectionThread = new DataCollectionThread();
    
    /**
     * Set to {@code true} when {@code tbaCollectionThread} is started for the first time
     */
    public static boolean tbaIsSync;
    
    /**
     * The Base's instance of {@link TBA} for use throughout the project. Set in {@code main} on startup.
     */
    public static TBA tbaApi;
    
    /**
     * Launches the program. Sets the auth token for {@link TBA}, recovers sessions, and launches {@link Entry}
     *
     * @param args console arguments
     * @throws IOException thrown from session recovery
     */
    public static void main(String args[]) throws IOException {
        
        TBA.setAuthToken("OPynqKt8K0vueAXqxZzdigY9OBYK3KMgQQrsM4l8jE5cBmGfByhy6YzVIb2Ts7xD");
        tbaApi = new TBA();
        
        new File("main_storage/").mkdir();
        new File("main_storage/sessions/").mkdir();
        new File("main_storage/admins/").mkdir();
        
        File sessions = new File("main_storage/sessions/");
        if (sessions.isDirectory() && sessions.listFiles() != null) {
            for (File fl : sessions.listFiles()) {
                recoveredSessions.add(SavingFunctions.recoverFullSession(fl));
            }
        }
        
        
        Application.launch(Entry.class, args);
    }
    
    public Main() {
    }
}
