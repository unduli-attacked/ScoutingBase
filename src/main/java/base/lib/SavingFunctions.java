package base.lib;

import base.Main;
import base.models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class SavingFunctions {
    
    private static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    
    public static <T extends Saveable> boolean saveSaveables(Collection<T> ls_){
        boolean succ = true;
        for(Saveable saveable_ : ls_){
            succ = succ && saveable_.saveRaw(Main.currentSession);
        }
        return succ;
    }
    
    public static <T> T recoverSaveable(File file_, Class<T> class_){
        try{
            System.out.println(file_.getPath());
            System.out.println(new FileReader(file_).read());
            System.out.println(gson.fromJson(new FileReader(file_), class_));
            return gson.fromJson(new FileReader(file_), class_);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Session recoverFullSession(File sessionJson_) throws IOException {
        Session session = null;
        System.out.println("breakpoint");
        try {
            session = gson.fromJson(new FileReader(sessionJson_), Session.class);
            System.out.println("breakpoint");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        
        try {
            session.recoverScouts();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        try {
            session.recoverMatches();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        try {
            session.recoverPits();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        try {
            session.recoverTeams();
        }catch(NullPointerException e){
            e.printStackTrace();
        }
        session.genDirectories();
        
        return session;
    }
    
    public static boolean save(File fl, Object ob){
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    
        try {
            fl.getParentFile().mkdirs();
            fl.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fl.setWritable(true);
        try {
            FileWriter fr = new FileWriter(fl);
            gson.toJson(ob, fr);
            fr.flush();
            fr.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
