package base.models;

import base.Main;
import base.lib.*;
import static base.lib.Colors.*;
import static base.lib.Enums.*;
import base.lib.DataClasses.*;
import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DatasetUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Team implements Saveable{
    //BASIC ID
    public int teamNum;
    public String teamName;
    
    //MATCHES
    public ArrayList<Match> matchesScouted = new ArrayList<>();
    public ArrayList<DataScout> dataScouts = new ArrayList<>();
    public ArrayList<NoteScout> noteScouts = new ArrayList<>();
    
    //HISTOGRAMS
    public HashMap<Match, HashMap<Enums.Goal, Integer>> score = new HashMap<>();
    public HashMap<Match, HashMap<Enums.Goal, Integer>> autoScore = new HashMap<>();
    public HashMap<Match, HashMap<Enums.Foul, Integer>> fouls = new HashMap<>();
    public HashMap<Match, LocalTime> cycleTime = new HashMap<>();
    public HashMap<Match, LocalTime> climbTime = new HashMap<>();
    public HashMap<Match, Double> driverRank = new HashMap<>();
    public HashMap<Match, Double> hpRank = new HashMap<>();
    public HashMap<Match, Double> defRank = new HashMap<>();
    public HashMap<Match, Double> defAvoidRank = new HashMap<>();
    
    //STAGES
//    public HashMap<Match, LocalTime> timeToCap1 = new HashMap<>();
//    public HashMap<Match, LocalTime> deltaToAct1 = new HashMap<>();
//    public HashMap<Match, LocalTime> timeToCap2 = new HashMap<>();
//    public HashMap<Match, LocalTime> deltaToAct2 = new HashMap<>();
//    public HashMap<Match, LocalTime> timeToCap3 = new HashMap<>();
//    public HashMap<Match, LocalTime> deltaToAct3 = new HashMap<>();
    
    //AVERAGES
    private LocalTime avCycleT;
    private LocalTime avClimbT;
    private double avDriverRank;
    private double avHPRank;
    private double avDefRank;
    private double avDefAvoidRank;
    
    //Y/Ns
    /**
     * FAILURE = didn't move
     * SUCCESS = moved
     */
    public HashMap<Match, Enums.Dot> autoMovement = new HashMap<>();
    
    /**
     * FAILURE = didn't climb
     * SUCCESS = climbed but didnt level
     * LEVEL_SELF = leveled only themselves
     * LEVEL_TEAM = leveled with multiple robots
     * WAS_LIFTED = lifted to climb BY another bot
     * NO_ATTEMPT = didn't try to climb
     */
    public HashMap<Match, Enums.Dot> climbed = new HashMap<>();
    
    /**
     *
     */
    public HashMap<Match, Enums.Dot> buddyClimb = new HashMap<>();
    public HashMap<Match, Enums.Dot> levelClimb = new HashMap<>();
    
    /**
     * SUCCESS = parked and didn't climb
     * FAILURE = neither parked nor climbed
     * NO_ATTEMPT = climbed
     */
    public HashMap<Match, Enums.Dot> parked = new HashMap<>();
    /**
     * FAILURE = tipped
     * SUCCESS = didn't tip
     * RECOVERED = got untipped
     */
    public HashMap<Match, Enums.Dot> tipped = new HashMap<>();
    /**
     * FAILURE = disabled
     * SUCCESS = didn't disable
     */
    public HashMap<Match, Enums.Dot> disabled = new HashMap<>();
    
    /**
     * FAILURE = broke and threw the match
     * RECOVERED = broke but still played well
     * SUCCESS = didn't break
     */
    public HashMap<Match, Enums.Dot> broke = new HashMap<>();
    
    /**
     * FAILURE = had bad connection issues
     * RECOVERED = had connection issues but fixed them
     * SUCCESS = no issues
     */
    public HashMap<Match, Enums.Dot> connectionIssues = new HashMap<>();
    
    //SCORING MAPS
    public HashMap<Match, ArrayList<Shot>> totalScoring = new HashMap<>();
    public HashMap<Match, ArrayList<Shot>> autoScoring = new HashMap<>();
    
    //PIT
    public Pit mainPit;
    public ArrayList<SecondPit> secondPits;
    public ArrayList<PitScout> pitScouts;
    
    
    
    
    public Team(int teamNum_, String teamName_){
        this.teamNum = teamNum_;
        this.teamName = teamName_;
        Main.currentSession.teams.put(this.teamNum, this);
    }
    
    public void addMatch(Match match_){
        this.matchesScouted.add(match_);
        this.dataScouts.addAll(match_.matchScouts);
        this.noteScouts.addAll(match_.noteScouts);
        
        HashMap<Enums.Goal, Integer> score_ = new HashMap<>();
        score_.put(Enums.Goal.MISS, 0);
        score_.put(Enums.Goal.LOWER, 0);
        score_.put(Enums.Goal.OUTER, 0);
        score_.put(Enums.Goal.INNER, 0);
    
        HashMap<Enums.Goal, Integer> autoScore_ = new HashMap<>();
        autoScore_.put(Enums.Goal.MISS, 0);
        autoScore_.put(Enums.Goal.LOWER, 0);
        autoScore_.put(Enums.Goal.OUTER, 0);
        autoScore_.put(Enums.Goal.INNER, 0);
        for(Shot s : match_.matchData.shots){
            score_.put(s.scored, score_.get(s.scored)+1);
            if(s.timeStamp.isBefore(LocalTime.of(0, 0, 16))) {
                autoScore_.put(s.scored, score_.get(s.scored) + 1);
            }
        }
        this.score.put(match_, score_);
        
        this.autoScore.put(match_, autoScore_);
        
        //todo fouls
    
        float cycleTime_ = 0;
        for(int s=1; s<match_.matchData.shots.size(); s++){
            cycleTime_ += (Functions.getFloatTime(match_.matchData.shots.get(s).timeStamp)-
                            Functions.getFloatTime(match_.matchData.shots.get(s-1).timeStamp));
        }
        cycleTime_ = cycleTime_/match_.matchData.numShots-1;
        this.cycleTime.put(match_, Functions.getLocalTime(cycleTime_));
        
        this.climbTime.put(match_, match_.matchData.climbDuration);
        
        this.driverRank.put(match_, match_.matchData.driverRank);
        
        this.hpRank.put(match_, match_.matchData.humanPlayerRank);
        
        this.defRank.put(match_, match_.matchData.defenseRank);
        
        this.defAvoidRank.put(match_, match_.matchData.defenseAvoidanceRank);
        
//        this.timeToCap1.put(match_, match_.matchData.capacityTimeS1);
//        this.deltaToAct1.put(match_, Functions.getLocalTime(Functions.getFloatTime(LocalTime.of(0,0,15)) - Functions.getFloatTime(this.timeToCap1.get(match_))));
//
//        this.timeToCap2.put(match_, match_.matchData.capacityTimeS2);
//        this.deltaToAct2.put(match_, Functions.getLocalTime(Functions.getFloatTime(match_.matchData.activateTimeS2) - Functions.getFloatTime(this.timeToCap2.get(match_))));
//
//        this.timeToCap3.put(match_, match_.matchData.capacityTimeS3);
//        this.deltaToAct3.put(match_, Functions.getLocalTime(Functions.getFloatTime(match_.matchData.activateTimeS3) - Functions.getFloatTime(this.timeToCap3.get(match_))));
    
        //averages occur in getters
    
        this.autoMovement.put(match_, (match_.matchData.moved) ? Enums.Dot.SUCCESS : Enums.Dot.FAILURE);

        //fixme climbed is waiting on the app
    
        if(match_.matchData.wasAssisted){
            this.buddyClimb.put(match_, Enums.Dot.WAS_LIFTED);
        }
        
        //fixme level type waiting on app
        
        if(!match_.matchData.parked){
            if(!this.climbed.get(match_).equals(Enums.Dot.FAILURE)
                && !this.climbed.get(match_).equals(Enums.Dot.NO_ATTEMPT)){
                this.parked.put(match_, Enums.Dot.NO_ATTEMPT);
            }else{
                this.parked.put(match_, Enums.Dot.FAILURE);
            }
        }else{
            this.parked.put(match_, Enums.Dot.SUCCESS);
        }
        
        //fixme need app for tipped
        
        this.disabled.put(match_, (match_.matchData.disabled) ? Enums.Dot.FAILURE : Enums.Dot.SUCCESS);
        
        //fixme need app for recovery
        
        //fixme add data point for connection issues
        
        ArrayList<Shot> tempTotalScore = new ArrayList<>();
        ArrayList<Shot> tempAutoScore = new ArrayList<>();
        for(Shot s : match_.matchData.shots){
            tempTotalScore.add(s);
            
            if(s.timeStamp.isBefore(LocalTime.of(0, 0, 16))){
                tempAutoScore.add(s);
            }
        }
        
        this.totalScoring.put(match_, tempTotalScore);
        this.autoScoring.put(match_, tempAutoScore);
    }
    
    public void addPit(Pit pit_){
        this.mainPit = pit_;
        this.pitScouts.add(Main.currentSession.pitScouts.get(pit_.scoutID));
        for(SecondPit pit : pit_.secondPits.values()){
            this.addSecondPit(pit);
        }
    }
    
    public void addSecondPit(SecondPit pit_){
        this.secondPits.add(pit_);
        this.pitScouts.add(Main.currentSession.pitScouts.get(pit_.scoutID));
    }
    
    /**
     * Used in saving raw data
     *
     * @return the file name WITHOUT directory or extension (ex. M14Blue1)
     */
    @Override
    public String getFileName() {
        return this.teamNum+"_"+this.teamName;
    }
    
    /**
     * Used in saving raw data
     *
     * @return the raw directory WITHOUT slashes (ex. rawMatches)
     */
    @Override
    public String getRawDirName() {
        return "rawTeams";
    }
    
    public void generatePDF(Session currentSession) throws FileNotFoundException, DocumentException {
        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(new File(currentSession.directory+FileSystem.PDF_TEAMS+this.getFileName()+".pdf")));
        doc.open();
        HashMap<String, JFreeChart> charts = genCharts();
    
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(650, 650);
        Graphics2D graphics2d = template.createGraphics(650, 650,
                new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, 650,
                650);
    
        charts.get("Score Location").draw(graphics2d, rectangle2d);
    
        graphics2d.dispose();
        contentByte.addTemplate(template, 0, 0);
    }
    
    public HashMap<String, JFreeChart> genCharts(){
        HashMap<String, JFreeChart> charts = new HashMap<>();
        
        double[][] scoreLocData = {
                createKeyedArray(this.score, Enums.Goal.INNER),
                createKeyedArray(this.score, Enums.Goal.OUTER),
                createKeyedArray(this.score, Enums.Goal.LOWER),
                createKeyedArray(this.score, Enums.Goal.MISS)
        };
        JFreeChart scoreLocChart = SavingFunctions.createChart(
                scoreLocData, getMatchNums(),
                new Color[]{Colors.innerGoal, Colors.outerGoal, Colors.lowerGoal, Colors.miss},
                new String[]{Enums.Goal.INNER.toString(), Enums.Goal.OUTER.toString(), Enums.Goal.LOWER.toString(), Enums.Goal.MISS.toString()},
                "Score Location", "Match", "# Scored");
        charts.put("Score Location", scoreLocChart);
        
        double[][] autoLocData = {
                createKeyedArray(this.autoScore, Enums.Goal.INNER),
                createKeyedArray(this.autoScore, Enums.Goal.OUTER),
                createKeyedArray(this.autoScore, Enums.Goal.LOWER),
                createKeyedArray(this.autoScore, Enums.Goal.MISS)
        };
        JFreeChart autoLocChart = SavingFunctions.createChart(
                autoLocData, getMatchNums(),
                new Color[]{Colors.innerGoal, Colors.outerGoal, Colors.lowerGoal, Colors.miss},
                new String[]{Enums.Goal.INNER.toString(), Enums.Goal.OUTER.toString(), Enums.Goal.LOWER.toString(), Enums.Goal.MISS.toString()},
                "Auto Score Location", "Match", "# Scored"
        );
        charts.put("Auto Score Location", autoLocChart);
        
        double[][] foulData = {
                createKeyedArray(this.fouls, Enums.Foul.GEN_TECH),
                createKeyedArray(this.fouls, Enums.Foul.TRENCH_TECH),
                createKeyedArray(this.fouls, Enums.Foul.RDV_TECH),
                createKeyedArray(this.fouls, Enums.Foul.ZONE_TECH),
                createKeyedArray(this.fouls, Enums.Foul.PIN_FOUL),
                createKeyedArray(this.fouls, Enums.Foul.CLIMB_PEN),
                createKeyedArray(this.fouls, Enums.Foul.GEN_FOUL),
                createKeyedArray(this.fouls, Enums.Foul.EXTEND_FOUL),
                createKeyedArray(this.fouls, Enums.Foul.HUMAN_FOUL)
        };
        JFreeChart foulChart = SavingFunctions.createChart(
                foulData, getMatchNums(),
                new Color[]{otherTech, trenchTech, rdvEndgameTech, protecZoneTech, pinTechFoul, touchClimbPen, otherFoul, overExtendFoul, humanFoul},
                new String[]{Foul.GEN_TECH.toString(), Foul.TRENCH_TECH.toString(), Foul.RDV_TECH.toString(), Foul.ZONE_TECH.toString(),
                                Foul.PIN_FOUL.toString(), Foul.CLIMB_PEN.toString(), Foul.GEN_FOUL.toString(), Foul.EXTEND_FOUL.toString(),
                                    Foul.HUMAN_FOUL.toString()},
                "Fouls", "Match", "# Fouls"
        );
        charts.put("Fouls", foulChart);
        
        double[][] cycleTimeData = new double[1][this.cycleTime.values().size()];
        for(int i=0; i<cycleTimeData[0].length; i++){
            cycleTimeData[0][i] = (double)Functions.getFloatTime(((List<LocalTime>)this.cycleTime.values()).get(i));
        }
        JFreeChart cycleTimeChart = SavingFunctions.createChart(
               cycleTimeData, getMatchNums(),
                new Color[]{Colors.cycleTime}, new String[]{"Average Cycle Time"},
                "Cycle Time", "Match", "Time (s)"
        );
        charts.put("Cycle Time", cycleTimeChart);
        
        JFreeChart lineChart = ChartFactory.createLineChart(
                "Average Shots",
                "Time","Shots",
                DatasetUtils.createCategoryDataset(new String[]{"Shots"},
                        new String[]{"0:00","0:10", "0:20", "0:30", "0:40", "0:50", "1:00", "1:10", "1:20", "1:30", "1:40", "1:50", "2:00", "2:10", "2:20"},
                        createShotsPer10Array()),
                PlotOrientation.VERTICAL,
                true,true,false);
        
        double[][] climbSpeedData = new double[1][this.climbTime.values().size()];
        for(int i=0; i<climbSpeedData[0].length; i++){
            climbSpeedData[0][i] = (double)Functions.getFloatTime(((List<LocalTime>)this.cycleTime.values()).get(i));
        }
        JFreeChart climbSpeedChart = SavingFunctions.createChart(
                climbSpeedData, getMatchNums(),
                new Color[]{climbSpeed}, new String[]{"Climb Duration"},
                "Climb Speed", "Match", "Time (s)"
        );
//        charts.put("Cycle Time", cycleTimeChart);
//
//        double[][] stage1Data = {
//                createLocalTimeArray(this.timeToCap1),
//                createLocalTimeArray(this.deltaToAct1)
//        };
//        JFreeChart stage1Chart = SavingFunctions.createChart(
//            stage1Data, getMatchNums(),
//            new Color[]{Colors.timeToCap1, Colors.deltaToAct1}, new String[]{"Capacity", "Activation"},
//            "Stage 1", "Match", "Time to... (s)"
//        );
//        charts.put("Stage 1", stage1Chart);
//
//        double[][] stage2Data = {
//                createLocalTimeArray(this.timeToCap2),
//                createLocalTimeArray(this.deltaToAct2)
//        };
//        JFreeChart stage2Chart = SavingFunctions.createChart(
//                stage2Data, getMatchNums(),
//                new Color[]{Colors.timeToCap2, Colors.deltaToAct2}, new String[]{"Capacity", "Activation"},
//                "Stage 2", "Match", "Time to... (s)"
//        );
//        charts.put("Stage 2", stage2Chart);
//
//        double[][] stage3Data = {
//                createLocalTimeArray(this.timeToCap3),
//                createLocalTimeArray(this.deltaToAct3)
//        };
//        JFreeChart stage3Chart = SavingFunctions.createChart(
//                stage3Data, getMatchNums(),
//                new Color[]{Colors.timeToCap3, Colors.deltaToAct3}, new String[]{"Capacity", "Activation"},
//                "Stage 3", "Match", "Time to... (s)"
//        );
//        charts.put("Stage 3", stage2Chart);
//
        JFreeChart driverRankChart = SavingFunctions.createChart(
                createSingleRangeArray(this.driverRank), getMatchNums(),
                new Color[]{Colors.driverRank}, new String[]{"Driver Rank"},
                "Driver Rank", "Match", "Rank"
        );
        charts.put("Driver Rank", driverRankChart);
        
        //TODO hp rank single range
        JFreeChart hpRankChart = SavingFunctions.createChart(
                createSingleRangeArray(this.hpRank), getMatchNums(),
                new Color[]{Colors.hpRank}, new String[]{"HP Rank"},
                "Human Player Rank", "Match", "Rank"
        );
        charts.put("Human Player Rank", hpRankChart);
        
        JFreeChart defRankChart = SavingFunctions.createChart(
                createSingleRangeArray(this.defRank), getMatchNums(),
                new Color[]{Colors.defRank}, new String[]{"Def. Rank"},
                "Defensive Rank", "Match", "Rank"
        );
        charts.put("Defensive Rank", defRankChart);
        
        JFreeChart antiDefRankChart = SavingFunctions.createChart(
                createSingleRangeArray(this.defAvoidRank), getMatchNums(),
                new Color[]{Colors.driverRank}, new String[]{"Avoidance Rank"},
                "Defensive Avoidance Rank", "Match", "Rank"
        );
        charts.put("Defensive Avoidance Rank", antiDefRankChart);
        
        return charts;
    }
    
    public <T> double[] createKeyedArray(HashMap<Match, HashMap<T, Integer>> scores, T bar){
        ArrayList<Double> toReturn = new ArrayList<>();
        for(Match m : scores.keySet()){
            toReturn.add((double)scores.get(m).get(bar));
        }
        return Functions.listToArray(toReturn);
    }
    
    public double[] createLocalTimeArray(HashMap<Match, LocalTime> data){
        ArrayList<Double> toReturn = new ArrayList<>();
        for(Match m : data.keySet()){
            toReturn.add((double)Functions.getFloatTime(data.get(m)));
        }
        return Functions.listToArray(toReturn);
    }
    
    public double[][] createSingleRangeArray(HashMap<Match, Double> data){
        double[][] toReturn = new double[1][data.values().size()];
        for(int i=0; i<toReturn[0].length; i++){
            toReturn[0][i] = ((List<Double>)data.values()).get(i);
        }
        return toReturn;
    }
    
    public double[][] createShotsPer10Array(){
        HashMap<String, Integer> timeCounts = new HashMap<>();
        timeCounts.put("0:00", 0);
        timeCounts.put("0:10", 0);
        timeCounts.put("0:20", 0);
        timeCounts.put("0:30", 0);
        timeCounts.put("0:40", 0);
        timeCounts.put("0:50", 0);
        timeCounts.put("1:00", 0);
        timeCounts.put("1:10", 0);
        timeCounts.put("1:20", 0);
        timeCounts.put("1:30", 0);
        timeCounts.put("1:40", 0);
        timeCounts.put("1:50", 0);
        timeCounts.put("2:00", 0);
        timeCounts.put("2:10", 0);
        timeCounts.put("2:20", 0);
        for(Match m : this.totalScoring.keySet()){
            for(Shot s : this.totalScoring.get(m)){
                float time = Functions.getFloatTime(s.timeStamp);
                if(time<=10){
                    timeCounts.put("0:10", timeCounts.get("0:10")+1);
                }else if(time>10 && time<=20){
                    timeCounts.put("0:20", timeCounts.get("0:20")+1);
                }else if(time>20 && time<=30){
                    timeCounts.put("0:30", timeCounts.get("0:30")+1);
                }else if(time>30 && time<=40){
                    timeCounts.put("0:40", timeCounts.get("0:40")+1);
                }else if(time>40 && time<=50){
                    timeCounts.put("0:50", timeCounts.get("0:50")+1);
                }else if(time>50 && time<=60){
                    timeCounts.put("1:00", timeCounts.get("1:00")+1);
                }else if(time>60 && time<=70){
                    timeCounts.put("1:10", timeCounts.get("1:10")+1);
                }else if(time>70 && time<=80){
                    timeCounts.put("1:20", timeCounts.get("1:20")+1);
                }else if(time>80 && time<=90){
                    timeCounts.put("1:30", timeCounts.get("1:30")+1);
                }else if(time>90 && time<=100){
                    timeCounts.put("1:40", timeCounts.get("1:40")+1);
                }else if(time>100 && time<=110){
                    timeCounts.put("1:50", timeCounts.get("1:50")+1);
                }else if(time>110 && time<=120){
                    timeCounts.put("2:00", timeCounts.get("2:00")+1);
                }else if(time>120 && time<=130){
                    timeCounts.put("2:10", timeCounts.get("2:10")+1);
                }else if(time>130 && time<=140){
                    timeCounts.put("2:20", timeCounts.get("2:20")+1);
                }
            }
        }
        
        ArrayList<Double> slkfjsdklfj = new ArrayList<>();
        for(String key : timeCounts.keySet()){
            slkfjsdklfj.add((double)(timeCounts.get(key)/this.totalScoring.keySet().size()));
        }
        
        
        return new double[][]{Functions.listToArray(slkfjsdklfj)};
    }
    
    public String[] getMatchNums(){
        ArrayList<String> jfsdlk = new ArrayList<>();
        for(Match m : this.matchesScouted){
            jfsdlk.add(String.valueOf(m.matchNum));
        }
        return (String[])jfsdlk.toArray();
    }
}
