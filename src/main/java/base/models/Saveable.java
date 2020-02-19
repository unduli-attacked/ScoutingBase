package base.models;

import base.lib.SavingFunctions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface Saveable {
    
    /**
     * Used in saving raw data
     * @return the file name WITHOUT directory or extension (ex. M14Blue1)
     */
    String getFileName();
    
    /**
     * Used in saving raw data
     * @return the raw directory WITHOUT slashes (ex. rawMatches)
     */
    String getRawDirName();
    
    /**
     * Saves a Saveable object to a json. Catches own exceptions
     * @param currentSession_ the current session (will be saved here)
     * @return true if success, false if exception
     */
    default boolean saveRaw(Session currentSession_){
        File fl = new File(currentSession_.directory+"/rawData/"+this.getRawDirName()+"/"+this.getFileName()+".json");
        return SavingFunctions.save(fl, this);
    }
}
