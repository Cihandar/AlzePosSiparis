package com.example.alzepossiparis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PosParamModel {
    @JsonProperty("AdisyonSatir")
    public List<AdsSatir> AdisyonSatir;
    @JsonProperty("Adisyon")
    public Adisyon Adisyon;
    @JsonProperty("userName")
    public String UserName;
    @JsonProperty("password")
    public String Password;
}
