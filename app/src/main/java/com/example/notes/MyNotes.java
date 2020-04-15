package com.example.notes;

public class MyNotes {
    String titlenote, datenote, descnote, keynote;

    public MyNotes() {
    }

    public MyNotes(String titlenote, String datenote, String descnote, String keynote) {
        this.titlenote = titlenote;
        this.datenote = datenote;
        this.descnote = descnote;
        this.keynote = keynote;
    }

    public String getKeynote() {
        return keynote;
    }

    public void setKeynote(String keynote) {
        this.keynote = keynote;
    }

    public String getTitlenote() {
        return titlenote;
    }

    public void setTitlenote(String titlenote) {
        this.titlenote = titlenote;
    }

    public String getDatenote() {
        return datenote;
    }

    public void setDatenote(String datenote) {
        this.datenote = datenote;
    }

    public String getDescnote() {
        return descnote;
    }

    public void setDescnote(String descnote) {
        this.descnote = descnote;
    }
}
