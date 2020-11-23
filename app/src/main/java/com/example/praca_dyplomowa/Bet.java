package com.example.praca_dyplomowa;

public class Bet {

    String email;
    String betType;
    String coinsToWin;

    public Bet(String email, String betType, String coinsToWin) {
        this.email = email;
        this.betType = betType;
        this.coinsToWin = coinsToWin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public String getCoinsToWin() {
        return coinsToWin;
    }

    public void setCoinsToWin(String coinsToWin) {
        this.coinsToWin = coinsToWin;
    }
}
