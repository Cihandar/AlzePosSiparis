package com.example.alzepossiparis.models;
import com.fasterxml.jackson.annotation.JsonProperty;


public class ApiResult {
    @JsonProperty("status")
    public boolean status;
    @JsonProperty("result")
    public String result;
    @JsonProperty("message")
    public String message;
}
