package com.example.praca_dyplomowa;

public class BetFinished {

    String team1Name;
    String team2Name;
    String result;
    String coinsWon;
    String coinsLost;
    boolean won;

    public boolean isWon() {
        return won;
    }

    public BetFinished(String team1Name, String team2Name, String result, String coinsWon, boolean won) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.result = result;
        this.coinsWon = coinsWon;
        this.won=won;
    }
    public BetFinished(String team1Name, String team2Name, String result, String coinsLost) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.result = result;
        this.coinsLost = coinsLost;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCoinsWon() {
        return coinsWon;
    }

    public void setCoinsWon(String coinsWon) {
        this.coinsWon = coinsWon;
    }

    public String getCoinsLost() {
        return coinsLost;
    }

    public void setCoinsLost(String coinsLost) {
        this.coinsLost = coinsLost;
    }
}
