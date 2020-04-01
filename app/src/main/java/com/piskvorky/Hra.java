package com.piskvorky;

public class Hra {

    private int hracia_plocha[];
    private Hrac prvy_hrac;
    private Hrac druhy_hrac;

    Hra(Hrac prvy,Hrac druhy){
        hracia_plocha=new int[9];
        prvy_hrac=prvy;
        druhy_hrac=druhy;
    }

    public boolean ma_Bod(){

        return true;
    }

}
