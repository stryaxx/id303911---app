package com.example.storefrontv2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;

public class StoreService implements Response.ErrorListener {
    public static final String BASE_URL = "http://192.168.1.56:8080/Storefront/api/account/login2?";
    public static final String STORE_URL = "http://192.168.1.56:8080/Storefront/api/store";
    public static final String REGISTER_URL = "http://192.168.1.56:8080/Storefront/api/account/register2";
    public static final String IMAGE_URL = "http://192.168.1.56:8080/Storefront/api/image";
    static StoreService SINGLETON;

    User user;

    String token;
    RequestQueue requestQueue;

    public static StoreService initialize(Context context, String token) {
        SINGLETON = new StoreService(context, token);
        return SINGLETON;
    }

    public static StoreService getInstance() {
        return SINGLETON;
    }

    public StoreService(Context context, String token) {
        this.token = token;
        this.requestQueue = Volley.newRequestQueue(context);
        loadUser();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.out.println("Error: " + error);
    }

    public User getUser() {
        return user;
    }

    public void loadUser() {
        requestQueue.add(new SecuredJsonObjectRequest(Request.Method.GET, BASE_URL + "auth/currentuser", null,
                response -> {
                    try {
                        user = new User(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, this, token));
    }

    static class SecuredJsonObjectRequest extends JsonObjectRequest {
        String token;

        public SecuredJsonObjectRequest(int method, String url, @Nullable JSONObject jsonRequest,
                                        Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener, String token) {
            super(method, url, jsonRequest, listener, errorListener);
            this.token = token;
        }

        @Override
        public Map<String, String> getHeaders() {
            HashMap<String,String> result = new HashMap<>();
            result.put("Authorization", "Bearer " + token);
            return result;
        }
    }
}
