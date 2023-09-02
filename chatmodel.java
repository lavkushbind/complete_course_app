package com.example.advncd;

public class chatmodel {
    String Muid,masseg;
    Long timestamp;
    String urip;

    public String getUrip() {
        return urip;
    }

    public void setUrip(String urip) {
        this.urip = urip;
    }

    public chatmodel() {
    }

    public chatmodel(String senderId, String message) {
    }

    public String getMuid() {
        return Muid;
    }

    public void setMuid(String muid) {
        Muid = muid;
    }

    public String getMasseg() {
        return masseg;
    }

    public void setMasseg(String masseg) {
        this.masseg = masseg;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public chatmodel(String muid, String masseg, Long timestamp) {
        Muid = muid;
        this.masseg = masseg;
        this.timestamp = timestamp;
    }
}
