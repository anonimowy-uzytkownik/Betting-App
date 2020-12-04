package com.example.praca_dyplomowa;

public class LeaderboardUser {

    String displayName;
    String wins;
    String loses;
    String coinsWon;
    String leaderboardRanking;
    String avatar;

    public String getAvatar() {
        return avatar;
    }

    public LeaderboardUser(String displayName, String wins, String loses, String coinsWon, String leaderboardRanking, String avatar) {
        this.displayName = displayName;
        this.wins = wins;
        this.loses = loses;
        this.coinsWon = coinsWon;
        this.leaderboardRanking = leaderboardRanking;
        this.avatar = avatar;
    }

    public String getLeaderboardRanking() {
        return leaderboardRanking;
    }

    public void setLeaderboardRanking(String leaderboardRanking) {
        this.leaderboardRanking = leaderboardRanking;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }



    public String getLoses() {
        return loses;
    }

    public void setLoses(String loses) {
        this.loses = loses;
    }

    public String getCoinsWon() {
        return coinsWon;
    }

    public void setCoinsWon(String coinsWon) {
        this.coinsWon = coinsWon;
    }

    public LeaderboardUser(String displayName, String wins, String loses, String coinsWon) {
        this.displayName = displayName;
        this.wins = wins;
        this.loses = loses;
        this.coinsWon = coinsWon;
    }

    public LeaderboardUser(String displayName, String wins, String loses, String coinsWon, String leaderboardRanking) {
        this.displayName = displayName;
        this.wins = wins;
        this.loses = loses;
        this.coinsWon = coinsWon;
        this.leaderboardRanking = leaderboardRanking;
    }
}
