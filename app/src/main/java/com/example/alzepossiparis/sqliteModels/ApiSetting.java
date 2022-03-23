package com.example.alzepossiparis.sqliteModels;

import com.orm.SugarRecord;

public class ApiSetting extends SugarRecord<ApiSetting> {
    String apiUrl;
    String apiUsername;
    String apiPassword;

    public ApiSetting(){}

    public ApiSetting(String apiUrl,String apiUsername,String apiPassword) {
        this.apiUrl = apiUrl;
        this.apiUsername=apiUsername;
        this.apiPassword =apiPassword;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getApiPassword() {
        return apiPassword;
    }

    public String getApiUsername() {
        return apiUsername;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public void setApiUsername(String apiUsername) {
        this.apiUsername = apiUsername;
    }
}
