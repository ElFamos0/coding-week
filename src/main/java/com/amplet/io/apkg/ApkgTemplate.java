package com.amplet.io.apkg;

public class ApkgTemplate {
    
    private int ord;
    private String qfmt;
    private String afmt;

    public ApkgTemplate(int ord, String qfmt, String afmt) {
        this.ord = ord;
        this.qfmt = qfmt;
        this.afmt = afmt;
    }
    
    public int getOrd() {
        return ord;
    }

    public String getQfmt() {
        return qfmt;
    }

    public String getAfmt() {
        return afmt;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public void setQfmt(String qfmt) {
        this.qfmt = qfmt;
    }

    public void setAfmt(String afmt) {
        this.afmt = afmt;
    }
    
}
