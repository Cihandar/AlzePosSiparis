package com.example.alzepossiparis.sqliteModels;


import com.orm.SugarRecord;

public class Groups extends SugarRecord<Groups> {
     String groupName;

    public Groups(){}

    public Groups(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }


}
