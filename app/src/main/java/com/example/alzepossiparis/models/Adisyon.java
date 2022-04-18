package com.example.alzepossiparis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Adisyon {
    @JsonProperty("FISCOUNTER")
    public String FisCounter;
    @JsonProperty("DEPKODU")
    public String DepKodu;
    @JsonProperty("MASANO")
    public String MasaNo;
    @JsonProperty("GARSONKODU")
    public String GarsonKodu;
    @JsonProperty("TUR")
    public String Tur;
    @JsonProperty("KISI")
    public int Kisi;
    @JsonProperty("MUSTERIID")
    public int MusteriId;
    @JsonProperty("MUSKARTNO")
    public String MusKartNo;
}
