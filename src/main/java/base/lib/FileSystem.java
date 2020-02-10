package base.lib;

public enum FileSystem {
    RAW_DATA("rawData/"), RAW_SCOUTS(RAW_DATA.basic+"rawScouts/"), RAW_MATCHES(RAW_DATA.basic+"rawMatches/"),
    RAW_PITS(RAW_DATA.basic+"rawPits/"), RAW_TEAMS(RAW_DATA.basic+"rawTeams/"), FINAL_DATA("finalData/"),
    PDF_TEAMS(FINAL_DATA.basic+"pdfTeams/"), PDF_PIT_NOTES(FINAL_DATA.basic+"pdfPitNotes/"), PDF_MATCH_NOTES(FINAL_DATA.basic+"pdfMatchNotes");
    
    public String basic;
    
    FileSystem(String str_) {
        this.basic = str_;
    }
    
    @Override
    public String toString() {
        return basic;
    }
}
