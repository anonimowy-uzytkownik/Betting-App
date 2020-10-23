package com.example.praca_dyplomowa;

public class Match {

    private String team1Name;
    private String team2Name;
    private String win1odds;
    private String win2odds;
    private String win3odds;
    private String result;

    public Match(String team1Name, String team2Name, String win1odds, String win2odds, String win3odds, String result) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.win1odds = win1odds;
        this.win2odds = win2odds;
        this.win3odds = win3odds;
        this.result = result;
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

    public String getWin1odds() {
        return win1odds;
    }

    public void setWin1odds(String win1odds) {
        this.win1odds = win1odds;
    }

    public String getWin2odds() {
        return win2odds;
    }

    public void setWin2odds(String win2odds) {
        this.win2odds = win2odds;
    }

    public String getWin3odds() {
        return win3odds;
    }

    public void setWin3odds(String win3odds) {
        this.win3odds = win3odds;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
