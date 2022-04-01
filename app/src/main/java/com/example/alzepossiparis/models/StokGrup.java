package com.example.alzepossiparis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

//select STOKGRUPID,KODU,ADI from STOKGRUP WHERE TURU='URUN'
public class StokGrup {
    @JsonProperty("STOKGRUPID")
    public int sTOKGRUPID;
    @JsonProperty("KODU")
    public String kODU;
    @JsonProperty("ADI")
    public String aDI;
}
