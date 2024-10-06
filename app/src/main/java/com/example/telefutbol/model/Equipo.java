package com.example.telefutbol.model;

public class Equipo {

    private int intRank;
    private String strTeam;
    private String strBadge;
    private int intWin;
    private int intLoss;
    private int intDraw;
    private int intGoalsFor;
    private int intGoalsAgainst;
    private int intGoalDifference;

    public Equipo(String strTeam, int intRank, String strBadge, int intWin, int intDraw, int intLoss, int intGoalsFor, int intGoalsAgainst, int intGoalDifference) {
        this.intRank = intRank;
        this.strTeam = strTeam;
        this.strBadge = strBadge;
        this.intWin = intWin;
        this.intLoss = intLoss;
        this.intDraw = intDraw;
        this.intGoalsFor = intGoalsFor;
        this.intGoalsAgainst = intGoalsAgainst;
        this.intGoalDifference = intGoalDifference;
    }

    // Getters and Setters
    public int getIntRank() {
        return intRank;
    }

    public void setIntRank(int intRank) {
        this.intRank = intRank;
    }

    public String getStrTeam() {
        return strTeam;
    }

    public void setStrTeam(String strTeam) {
        this.strTeam = strTeam;
    }

    public String getStrBadge() {
        return strBadge;
    }

    public void setStrBadge(String strBadge) {
        this.strBadge = strBadge;
    }

    public int getIntWin() {
        return intWin;
    }

    public void setIntWin(int intWin) {
        this.intWin = intWin;
    }

    public int getIntLoss() {
        return intLoss;
    }

    public void setIntLoss(int intLoss) {
        this.intLoss = intLoss;
    }

    public int getIntDraw() {
        return intDraw;
    }

    public void setIntDraw(int intDraw) {
        this.intDraw = intDraw;
    }

    public int getIntGoalsFor() {
        return intGoalsFor;
    }

    public void setIntGoalsFor(int intGoalsFor) {
        this.intGoalsFor = intGoalsFor;
    }

    public int getIntGoalsAgainst() {
        return intGoalsAgainst;
    }

    public void setIntGoalsAgainst(int intGoalsAgainst) {
        this.intGoalsAgainst = intGoalsAgainst;
    }

    public int getIntGoalDifference() {
        return intGoalDifference;
    }

    public void setIntGoalDifference(int intGoalDifference) {
        this.intGoalDifference = intGoalDifference;
    }

    public String getRecord() {
        return intWin + "/" + intDraw + "/" + intLoss;
    }

    public String getGoalStats() {
        return intGoalsFor + "/" + intGoalsAgainst + "/" + intGoalDifference;
    }
}

