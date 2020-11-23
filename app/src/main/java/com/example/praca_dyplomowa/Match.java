package com.example.praca_dyplomowa;

import android.util.Log;

public class Match {

    private String team1Name;
    private String team2Name;
    private String win1odds;
    private String win2odds;
    private String win3odds;
    private String result;
    private String image1;
    private String image2;
    private String league;
    private String matchId;

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Match(String team1Name, String team2Name, String win1odds, String win2odds, String win3odds, String result, String image1, String image2, String league, String matchId) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.win1odds = win1odds;
        this.win2odds = win2odds;
        this.win3odds = win3odds;
        this.result = result;
        this.image1 = image1;
        this.image2 = image2;
        this.league = league;
        this.matchId = matchId;
    }

    public Match(String team1Name, String team2Name, String win1odds, String win2odds, String win3odds, String result, String image1, String image2) {
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.win1odds = win1odds;
        this.win2odds = win2odds;
        this.win3odds = win3odds;
        this.result = result;
        this.image1 = image1;
        this.image2 = image2;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
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

    static String calculateResult(String result)
    {
        int team1Score,team2Score;
        team1Score = Integer.parseInt(result.substring(0,result.lastIndexOf(":")));
        team2Score = Integer.parseInt(result.substring(result.lastIndexOf(":")+1));
        if(team1Score>team2Score){return "Team1";}
        else if(team1Score>team2Score){return "Team2";}
        else{return "Draw";}

    }
}
