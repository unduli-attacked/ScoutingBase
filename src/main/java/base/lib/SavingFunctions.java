package base.lib;

import base.Main;
import base.models.Saveable;
import base.models.Session;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DatasetUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.Collection;

public class SavingFunctions {
    
    private static Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    
    public static <T extends Saveable> boolean saveSaveables(Collection<T> ls_) {
        boolean succ = true;
        for (Saveable saveable_ : ls_) {
            succ = succ && saveable_.saveRaw(Main.currentSession);
        }
        return succ;
    }
    
    public static <T> T recoverSaveable(File file_, Class<T> class_) {
        try {
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            session.recoverMatches();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            session.recoverPits();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            session.recoverTeams();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        session.genDirectories();
        
        return session;
    }
    
    public static boolean save(File fl, Object ob) {
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
    
    public static JFreeChart createChart(double[][] set, String[] matchNums, Color[] colors, String[] rangeLabels, String title, String domain, String range) {
        
        JFreeChart chart = ChartFactory.createStackedBarChart(
                title, domain, range, DatasetUtils.createCategoryDataset(rangeLabels, matchNums, set),
                PlotOrientation.VERTICAL, true, true, false);
        
        chart.setBackgroundPaint(Color.WHITE);
        for (int i = 0; i < colors.length; i++) {
            chart.getCategoryPlot().getRenderer().setSeriesPaint(i, colors[i]);
        }
        return chart;
    }
    
    public static void drawChart(Document doc, File loc, JFreeChart chart, double width, double height, double x, double y) throws FileNotFoundException, DocumentException {
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(loc));
        doc.open();
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(500, 500);
        Graphics2D graphics2d = new PdfGraphics2D(template, 500, 500);
        Rectangle2D rectangle2d = new Rectangle2D.Double(x, y, 500,
                500);
        
        chart.draw(graphics2d, rectangle2d);
        
        graphics2d.dispose();
        
        Image image = com.itextpdf.text.Image.getInstance(template);
        image.scaleAbsolute((float) width, (float) height);
        image.setAbsolutePosition((float) width, (float) height);
        doc.add(image);
        
        doc.close();
    }
}
