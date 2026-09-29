package com.onaar.retrofitcik;

import com.google.gson.annotations.SerializedName;

public class Pytanie {
    @SerializedName("tresc")
    private String trescPytania;

    public Pytanie(String trescPytania, String odpA, String odpB, String odpC, int poprawna) {
        this.trescPytania = trescPytania;
        OdpA = odpA;
        OdpB = odpB;
        OdpC = odpC;
        this.poprawna = poprawna;
    }

    @SerializedName("odp_a")
    private String OdpA;
    @SerializedName("odp_b")
    private String OdpB;

    public String getTrescPytania() {
        return trescPytania;
    }

    public String getOdpA() {
        return OdpA;
    }

    public String getOdpB() {
        return OdpB;
    }

    public String getOdpC() {
        return OdpC;
    }

    public int getPoprawna() {
        return poprawna;
    }

    @SerializedName("odp_c")
    private String OdpC;

    private int poprawna;
}
