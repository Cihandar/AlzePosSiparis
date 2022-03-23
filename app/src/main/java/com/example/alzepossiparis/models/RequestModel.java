package com.example.alzepossiparis.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestModel {
    @JsonProperty("query")
    public String query;
    @JsonProperty("username")
    public String username;
    @JsonProperty("password")
    public String password;
    @JsonProperty("apiurl")
    public String apiUrl;

}
