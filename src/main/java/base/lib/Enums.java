package base.lib;

public class Enums {
    public static class PitData {
        public enum HabLevel {
            NONE(0), ONE(1), TWO(2), THREE(3);
        
            public int basic;
        
            HabLevel(int num_) {
                basic = num_;
            }
        
            @Override
            public String toString() {
                return String.valueOf(basic);
            }
        }
    
        public enum Piece {
            HATCH("Hatch"), CARGO("Cargo"), BOTH("Both"), NEITHER("Neither");
        
            public String basic;
        
            Piece(String str_) {
                basic = str_;
            }
        
            @Override
            public String toString() {
                return basic;
            }
        }
    
        public enum RocketLevel {
            ONE(1), TWO(2), THREE(3);
        
            public int basic;
        
            RocketLevel(int num_) {
                basic = num_;
            }
        
            @Override
            public String toString() {
                return String.valueOf(basic);
            }
        }
    
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
    
        public enum AutoStrategy {
            CLOSE_ROCKET_HATCH("Close rocket hatch"), FAR_ROCKET_HATCH("Far rocket hatch"), FRONT_SHIP_HATCH("Front ship hatch"),
            OTHER_SHIP_HATCH("Other ship hatch"), SHIP_CARGO("Ship cargo"), LINE("Across line"),
            MULTI_PIECE("Multi piece"), NONE("None"), OTHER("Other");
        
            public String basic;
        
            AutoStrategy(String str_) {
                basic = str_;
            }
        
            @Override
            public String toString() {
                return basic;
            }
        }
    
        public enum TeleopStrategy {
            SHIP_CARGO("Ship cargo"), SHIP_HATCH("Ship hatch"), ROCKET_CARGO("Rocket cargo"),
            ROCKET_HATCH("Rocket hatch"), DEFENSE("Defense"), MIXED("Mixed"),
            FLEXIBLE("Flexible"), OTHER("Other");
        
            public String basic;
        
            TeleopStrategy(String str_) {
                basic = str_;
            }
        
            @Override
            public String toString() {
                return basic;
            }
        }
    }
    
    public static class MatchData{
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
    }
}
