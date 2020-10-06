package com.example.storefrontv2.data.model;

import com.example.storefrontv2.ChatService;
import com.example.storefrontv2.data.Result;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RegisterDataSource {
    public boolean register(String username, String password,
                            String firstname, String lastname, String email, String zipcode,
                            String address, String city) {
        JSONArray items = null;
        HttpURLConnection c = null;
        try {
            URL url = new URL(ChatService.REGISTER_URL
                    + "?username=" +username+ "&password=" +password+
                    "&firstname=" +firstname+
                    "&lastname=" +lastname+ "&email=" +email+
                    "&zipcode=" +zipcode+ "&address=" +address+
                    "&city=" +city);
            c = (HttpURLConnection) url.openConnection();
            c.setUseCaches(true);
            c.setRequestMethod("GET");

            if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("TRUE: " + true);
                return true;
            } else {
                System.out.println("FALSE: " + false);
                return false;
            }


        } catch (Exception e) {
            return false;
        } finally {
            if (c != null) c.disconnect();
        }
    }
}
