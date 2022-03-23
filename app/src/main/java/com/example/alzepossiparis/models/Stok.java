package com.example.alzepossiparis.models;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Stok {
    @JsonProperty("STOKID")
    public int sTOKID;
    @JsonProperty("GRUP")
    public String gRUP;
    @JsonProperty("ADI")
    public String aDI;
    @JsonProperty("SFIYAT1")
    public double sFIYAT1;
    @JsonProperty("SDOVIZ")
    public String sDOVIZ;
    @JsonProperty("SATISKDV")
    public double sATISKDV;
    @JsonProperty("BIRIM")
    public String bIRIM;
}
