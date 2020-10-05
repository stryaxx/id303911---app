package com.example.storefrontv2;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    String firstName;
    String lastName;
    String userid;

    public User() {
    }

    public User(JSONObject jo) throws JSONException {
        setUserid(jo.getString("userid"));
        if(jo.has("firstName")) {
            setFirstName(jo.getString("firstName"));
        }
        if(jo.has("lastName")) {
            setLastName(jo.getString("lastName"));
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
