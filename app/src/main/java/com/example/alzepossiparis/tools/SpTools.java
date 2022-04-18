package com.example.alzepossiparis.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SpTools {
    SharedPreferences spStored;
    public SpTools(Context context)
    {
        spStored = context.getSharedPreferences("com.example.alzepossiparis", Context.MODE_PRIVATE);
    }

    public String GetData(String Keys)
    {
        return  spStored.getString(Keys,"");
    }

    public void  PutData(String Keys,String Value)
    {
        spStored.edit().putString(Keys,Value).commit();
    }
}
