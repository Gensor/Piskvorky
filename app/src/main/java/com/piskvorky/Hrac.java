package com.piskvorky;

public class Hrac {
    private String meno;
    private int skore = 0;
    private int znak =0;

    Hrac(String meno){
        this.meno=meno;
    }

    public String getMeno() {
        return meno;
    }

    public void setMeno(String meno) {
        this.meno = meno;
    }

    public int getSkore() {
        return skore;
    }

    public void setSkore(int skore) {
        this.skore = skore;
    }

    public int getZnak() {
        return znak;
    }

    public void setZnak(int znak) {
        this.znak = znak;
    }
}
