package com.example.storefrontv2.data.model;

import com.example.storefrontv2.StoreService;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BrowseDataSource {
    public JSONArray retrieve() {
        JSONArray items = null;
        HttpURLConnection c = null;
        try {
            URL url = new URL(StoreService.STORE_URL + "/retrieve");
            c = (HttpURLConnection) url.openConnection();
            c.setUseCaches(true);
            c.setRequestMethod("GET");

            if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8));
                String token = br.readLine();
                System.out.println(token + "BLABLABLBALBLA");
                items = new JSONArray(token);
                c.getInputStream().close(); // Why?
                return items;
            } else {
                System.out.println("ERROR: response code " + c.getResponseCode() + " " + c.getResponseMessage());
                System.out.println(url);
            }

            return items;
        } catch (Exception e) {
            return items;
        } finally {
            if (c != null) c.disconnect();
        }
    }
}
