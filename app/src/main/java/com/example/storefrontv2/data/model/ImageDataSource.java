package com.example.storefrontv2.data.model;

import com.example.storefrontv2.StoreService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class ImageDataSource {
    public JSONArray retrieve(String id) {
        JSONArray items = null;
        HttpURLConnection c = null;
        try {
            URL url = new URL(StoreService.IMAGE_URL + "/retrieve?id=" + id);
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

    public boolean upload(String itemId, String image) throws JSONException {
        JSONObject imageData = new JSONObject();
        imageData.put("id" , "1");
        imageData.put("itemid" , itemId);
        imageData.put("image" , image);
        String JSONImageData = imageData.toString();

        String response = "";
        try {
            URL url= new URL(StoreService.IMAGE_URL + "/upload");;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            DataOutputStream printout;
            DataInputStream input;
            /*conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);*/
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.connect();
            printout = new DataOutputStream(conn.getOutputStream());
            byte[] data=JSONImageData.getBytes("UTF-8");
            printout.write(data);
            printout.flush();
            printout.close();

            int responseCode =conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
                System.out.println("Response codeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee: "+responseCode);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
