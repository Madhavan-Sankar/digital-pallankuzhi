package com.example.digital_pallankuzhi;

public class board {
    String p1,p2,win;

    public board(String p1, String p2, String win) {
        this.p1 = p1;
        this.p2 = p2;
        this.win = win;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }
}


