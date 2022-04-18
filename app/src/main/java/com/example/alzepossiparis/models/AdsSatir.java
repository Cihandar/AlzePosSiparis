package com.example.alzepossiparis.models;

import android.icu.lang.UProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdsSatir {
    @JsonProperty("STOKID")
    public int StokId;
    @JsonProperty("ACIKLAMA")
    public String Aciklama;
    @JsonProperty("ADET")
    public int Adet;
    public double BirimFiyat;
    public String StokAdi;

}
