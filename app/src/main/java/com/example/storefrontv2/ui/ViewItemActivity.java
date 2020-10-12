package com.example.storefrontv2.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.storefrontv2.R;
import com.example.storefrontv2.data.model.ImageDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ViewItemActivity extends AppCompatActivity {


    private ArrayList<Bitmap> imageList;
    private int currentImageIndex;
    private ImageView img;
    Button previous, next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewitem);

        imageList = new ArrayList<Bitmap>();
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        TextView titleTxt = (TextView) findViewById(R.id.titleTxt);
        TextView priceTxt = (TextView) findViewById(R.id.priceTxt);
        TextView descriptionTxt = (TextView) findViewById(R.id.descriptionTxt);
        img = (ImageView) findViewById(R.id.imageView3);
        currentImageIndex = 0;

        String title = getIntent().getStringExtra("Title");
        String itemId = getIntent().getStringExtra("ItemId");
        String desc = getIntent().getStringExtra("Description");
        String price = getIntent().getStringExtra("Price");

        titleTxt.setText(title);
        priceTxt.setText(price);
        descriptionTxt.setText(desc);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageList.size() > (currentImageIndex + 1))
                {
                    currentImageIndex++;
                    img.setImageBitmap(imageList.get(currentImageIndex));
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentImageIndex != 0)
                {
                    currentImageIndex--;
                    img.setImageBitmap(imageList.get(currentImageIndex));
                }
            }
        });

        ImageDataSource imageDataSource = new ImageDataSource();
        JSONArray images = imageDataSource.retrieve(itemId);

        for (int i = 0; i < images.length(); i++) {
            try {
                JSONObject image = (JSONObject) images.get(i);
                String imageData = image.getString("image");

                byte[] decodedString = Base64.decode(imageData, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                imageList.add(decodedByte);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Success!");

        if (imageList.size() >= 1)
        {
            img.setImageBitmap(imageList.get(currentImageIndex));
        }
    }
}
