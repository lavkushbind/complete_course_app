package com.example.advncd;

public class clasmodel {
    private  String clasid;
    private  String type;
    private long clasat;
    private  boolean checkopen;

    public clasmodel() {
    }

    public clasmodel(String clasid, String type, long clasat, boolean checkopen) {
        this.clasid = clasid;
        this.type = type;
        this.clasat = clasat;
        this.checkopen = checkopen;
    }

    public String getClasid() {
        return clasid;
    }

    public void setClasid(String clasid) {
        this.clasid = clasid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getClasat() {
        return clasat;
    }

    public void setClasat(long clasat) {
        this.clasat = clasat;
    }

    public boolean isCheckopen() {
        return checkopen;
    }

    public void setCheckopen(boolean checkopen) {
        this.checkopen = checkopen;
    }
}
