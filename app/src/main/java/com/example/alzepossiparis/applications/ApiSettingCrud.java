package com.example.alzepossiparis.applications;

import com.example.alzepossiparis.sqliteModels.ApiSetting;

import java.util.List;

public class ApiSettingCrud {

   public List<ApiSetting> getAll()
    {
      return  ApiSetting.listAll(ApiSetting.class);
    }

    public ApiSetting getSetting()
    {
       List<ApiSetting> apiSetting = ApiSetting.listAll(ApiSetting.class);
       if(apiSetting.size()>0) return apiSetting.get(0); else return  null;
    }

}
