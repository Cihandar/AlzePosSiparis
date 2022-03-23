package com.example.alzepossiparis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Garson {
    @JsonProperty("GKODU")
    public String gKodu;
    @JsonProperty("GADI")
    public String gAdi;
    @JsonProperty("DEPKODU")
    public String depKodu;
}
