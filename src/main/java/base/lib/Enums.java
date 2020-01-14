package base.lib;

public class Enums {
    public enum Preference {
        FLEXIBLE("Flexible"), IDEAL("Ideal"), INTEGRAL("Integral");
    
        public String basic;
    
        Preference(String str_) {
            basic = str_;
        }
    
        @Override
        public String toString() {
            return basic;
        }
    }
    
    public enum Station{
        RED_1(false, "Red 1"), RED_2(false, "Red 2"), RED_3(false, "Red 3"),
        BLUE_1(true, "Blue 1"), BLUE_2(true, "Blue 2"), BLUE_3(true, "Blue 3");
        
        boolean isBlue;
        String basic;
        Station(boolean blu_, String str_){
            isBlue=blu_;
            basic=str_;
        }
        
        public boolean isBlue(){return isBlue;}
        
        @Override
        public String toString(){return basic;}
    }
    
    public enum Goal{
        MISS(0), LOWER(1), OUTER(2), INNER(3);
        
        int basic;
        Goal(int pts_){
            basic=pts_;
        }
        
        @Override
        public String toString(){return String.valueOf(basic);}
    }

}
