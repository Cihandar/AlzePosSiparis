package com.example.alzepossiparis.models;
import com.fasterxml.jackson.annotation.JsonProperty;


public class Masalar {
    @JsonProperty("MASANO")
    public String mASANO;
    @JsonProperty("ACIKLAMA")
    public String aCIKLAMA;
    @JsonProperty("ASAATI")
    public String aSAATI;
    @JsonProperty("XPOS")
    public int xPOS;
    @JsonProperty("YPOS")
    public int yPOS;
    @JsonProperty("KISIS")
    public int kISIS;
    @JsonProperty("STATU")
    public int sTATU;
    @JsonProperty("ADISYONKISI")
    public int aDISYONKISI;
    @JsonProperty("ANO")
    public Object aNO;
    @JsonProperty("TOPLAM")
    public double tOPLAM;
    @JsonProperty("GARSON_ADI")
    public String gARSON_ADI;
}
