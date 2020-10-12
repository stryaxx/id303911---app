package com.example.storefrontv2.data;

import com.example.storefrontv2.StoreService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ItemDataSource {

    public String upload(String title, String description, String price, String image) throws JSONException {
        JSONObject imageData = new JSONObject();
        imageData.put("id" , "1");
        imageData.put("description" , description);
        imageData.put("image" , image);
        imageData.put("price" , price);
        imageData.put("sold" , "0");
        imageData.put("title" , title);
        imageData.put("price" , price);
        imageData.put("userid", CurrentClient.SESSION_ID );
        String JSONImageData = imageData.toString();

        String response = "";
        try {
            URL url= new URL(StoreService.STORE_URL + "/publish");;
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

        return response;
    }
}
