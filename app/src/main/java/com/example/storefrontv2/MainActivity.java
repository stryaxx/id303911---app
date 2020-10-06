package com.example.storefrontv2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.storefrontv2.data.model.BrowseDataSource;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    ChatService service;
    BrowseDataSource browseDataSource;
    TextView registerTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerTxt = (TextView) findViewById(R.id.register);
        LinearLayout itemList = findViewById(R.id.itemList);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        browseDataSource = new BrowseDataSource();
        JSONArray items = browseDataSource.retrieve();
        if (items == null) {
            //ERROR
            System.out.println("ERROR");
        } else {
            //SUCCESS
            for (int i = 0; i < items.length(); i++) {
                try {
                    JSONObject item = (JSONObject) items.get(i);
                    String id = item.getString("id");
                    String description = item.getString("description");
                    String image = item.getString("image");
                    String title = item.getString("title");
                    String price = item.getString("price");
                    String userid = item.getString("userid");


                    LayoutInflater inflater = this.getLayoutInflater();
                    View view = inflater.inflate(R.layout.store_item, itemList, false);
                    itemList.addView(view);
                    ImageView img = view.findViewById(R.id.imageView);
                    String pureBase64Encoded = image.substring(image.indexOf(",")  + 1);
                    byte[] decodedString = Base64.decode(pureBase64Encoded, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    img.setImageBitmap(decodedByte);
                    System.out.println( "TESTESTTEST" + image);

                    TextView txtTitle = view.findViewById(R.id.title);
                    txtTitle.setText(title);
                    TextView txtDes = view.findViewById(R.id.description);
                    txtDes.setText(description);
                    TextView txtPrice = view.findViewById(R.id.price);
                    txtPrice.setText(price);

                    System.out.println(description);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Sucess!");
        }
        service = ChatService.getInstance();

    }

}