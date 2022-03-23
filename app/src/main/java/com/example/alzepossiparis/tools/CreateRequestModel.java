package com.example.alzepossiparis.tools;

import com.example.alzepossiparis.applications.ApiSettingCrud;
import com.example.alzepossiparis.models.RequestModel;
import com.example.alzepossiparis.sqliteModels.ApiSetting;

public class CreateRequestModel {
    ApiSettingCrud _apiSettingCrud;

    public CreateRequestModel()
    {
        _apiSettingCrud=new ApiSettingCrud();
    }

    public RequestModel Create(String query)
    {
        ApiSetting apiSetting = _apiSettingCrud.getSetting();
        if(apiSetting!=null)
        {
            RequestModel  requestModel  = new RequestModel();
            requestModel.query=query;
            requestModel.username = apiSetting.getApiUsername();
            requestModel.password = apiSetting.getApiPassword();
            requestModel.apiUrl = apiSetting.getApiUrl();
            return requestModel;
        }else
        {
            return null;
        }
    }
}
