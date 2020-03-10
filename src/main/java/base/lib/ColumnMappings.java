package base.lib;

public class ColumnMappings {
    public enum MainPit {
        TIMESTAMP(0),
        SCOUT_ID(1),
        TEAM_NAME(2),
        TEAM_NUM(3),
        INTERVIEWEE(4),
        PRAC_WEIGHT(5),
        OFF_WEIGHT(6),
        DT_LEN(7),
        DT_WIDTH(8),
        HEIGHT(9),
        EX_SIDE(10),
        EX_TELE(11),
        EX_CLIMB(12),
        COVERAGE(13),
        CAPACITY(14),
        INTAKE(15),
        CLIMB(16),
        CLIMBER(17),
        VIS_PICKUP(18),
        VIS_ANGLE(19),
        SHOOT_ANY(20),
        CONTROL(21),
        ISSUES(22),
        TECH_NOTES(23),
        AUTO_STRAT(24),
        AUTO_SCORE(25),
        TELE_STRAT(26),
        HP_LOAD(27),
        HP_COLLEC(28),
        HP_PASS(29),
        HP_STORE(30),
        HP_TRACK(31),
        HP_OTHER(32),
        TRAVEL(33),
        SHOOT(34),
        COLLECT(35),
        TELE_PREF(36),
        AUTO_PREF(37),
        STRAT_NOTES(38),
        PHOTOS(39),
        BUDDY(40);
        
        public int val;
        
        private MainPit(int num_) {
            this.val = num_;
        }
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
        
    }
    
    public enum ReScoutPit {
        TIMESTAMP(0),
        TEAM_NAME(1),
        TEAM_NUM(2),
        INTERVIEWEE(3),
        CHANGES(4),
        ISSUES(5),
        NOTES(6),
        Q1(7),
        A1(8),
        Q2(9),
        A2(10),
        Q3(11),
        A3(12),
        Q4(13),
        A4(14),
        Q5(15),
        A5(16),
        MORE(17),
        PHOTOS(18),
        SCOUT_ID(19);
        
        public int val;
        
        private ReScoutPit(int num_) {
            this.val = num_;
        }
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
    
    public enum AppData {
        TIME(0), //mm/dd/yyyy hh:mm:ss
        IMG_DIM(1), // {width,height}
        MATCH_START(2), //ms since January 1, 1970
        SHOTS(3), // {(x,y);TYPE;time};.....
        CLIMB_START(4),
        CLIMB_END(5),
        CAP_1(6),
        CAP_2(7),
        CAP_3(8),
        ROT(9),
        POS(10),
        ID(11), // [first initial][last name][grad year] (case insensitive)
        TEAM_NUM(12), // 1-4 digits
        MATCH_NUM(13), // numerical
        ALL_POS(14), // [Red/Blue][1-3]
        ABSENT(15), // "on" or null
        START_POS(16), // 0-100, top to bottom
        OFF_LINE(17), // "on" or null
        TRENCH_TECH(25), //int
        PIN_FOUL(26), //int
        HUMAN_FOUL(27), //int
        ZONE_FOUL(28), //int
        TECH_FOUL(29), //int
        FOUL(30), //int
        CLIMB(31), //"on" or null
        BUDDY(32), //"on" or null
        ASSIST(33), //"on" or null
        LEVEL(34), //"on" or null
        PARKED(35), //"on" or null
        CLIMB_RP(36), //"on" or null
        ESTOP(37), //"on" or null
        BROKE(38), //"on" or null
        YELLOW(39), //"on" or null
        RED(40), //"on" or null
        DQ(41), //"on" or null
        TOTAL_RP(42), //gud meme
        TOTAL_PTS(43), // gud meme
        DRIVE_RANK(44), //0-5
        HP_RANK(45), //0-5
        DEF_RANK(46), //0-5
        DEF_AVOID_RANK(47), //0-5
        NOTES(48); //string, with Many Character
        
        
        public int val;
        
        AppData(int num_) {
            this.val = num_;
        }
        
        
        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }
}
