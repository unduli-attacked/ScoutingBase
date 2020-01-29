package base.lib;

public class ColumnMappings {
    public enum MainPit{
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
        private MainPit(int num_){
            this.val = num_;
        }
    
        @Override
        public String toString(){
            return String.valueOf(val);
        }
        
    }
    
    public enum ReScoutPit{
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
        private ReScoutPit(int num_){
            this.val = num_;
        }
    
        @Override
        public String toString(){
            return String.valueOf(val);
        }
    }
}
