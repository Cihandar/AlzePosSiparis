package com.example.alzepossiparis.models;
import com.fasterxml.jackson.annotation.JsonProperty;
//select STOKID,GRUP,ADI,SFIYAT1,SDOVIZ,BIRIM,GGRUP,HYERI from STOK WHERE TURU IN ('MAMUL','YARIMAMUL','Mamul','YarıMamul','Yarımamul')
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
    @JsonProperty("BIRIM")
    public String bIRIM;
    @JsonProperty("GGRUP")
    public String gGRUP;
    @JsonProperty("HYERI")
    public String hYERI;

}
