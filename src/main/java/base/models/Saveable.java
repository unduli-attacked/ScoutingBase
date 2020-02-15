package base.models;

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
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        File fl = new File(currentSession_.directory+"/rawData/"+this.getRawDirName()+"/"+this.getFileName()+".json");
        try {
            fl.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fl.setWritable(true);
        try {
            FileWriter fr = new FileWriter(fl);
            gson.toJson(this, fr);
            fr.flush();
            fr.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
