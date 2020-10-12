package com.example.storefrontv2.ui;

import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.storefrontv2.MainActivity;
import com.example.storefrontv2.R;
import com.example.storefrontv2.data.ItemDataSource;
import com.example.storefrontv2.data.model.ImageDataSource;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewItem extends AppCompatActivity {

    Button gobackBtn;
    Button publishImg;
    Button submitImgBtn;
    EditText title;
    EditText price;
    EditText desc;
    private ArrayList<String> images;
    private LinearLayout imageListLayout;
    private Context currentContext;
    private ItemDataSource itemDataSource;
    private ImageDataSource imageDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);

        itemDataSource = new ItemDataSource();
        imageDataSource = new ImageDataSource();

        title = (EditText) findViewById(R.id.titleEdit);
        price = (EditText) findViewById(R.id.priceEdit);
        desc = (EditText) findViewById(R.id.descEdit);
        submitImgBtn = (Button) findViewById(R.id.submitImgBtn);
        images = new ArrayList<String>();
        currentContext = this.getApplicationContext();
        gobackBtn = (Button) findViewById(R.id.gobackBtn);
        imageListLayout = (LinearLayout)findViewById(R.id.placeholderMoreImgs);
        publishImg = (Button) findViewById(R.id.publishImg);
        publishImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });


        gobackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        submitImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                if (images.size() >= 1)
                {
                    String itemId = "";

                    try {
                        itemId = itemDataSource.upload(title.getText().toString(), desc.getText().toString(), price.getText().toString(), images.get(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (images.size() >= 2 && itemId != "")
                    {
                        images.remove(0);

                        for (String img : images)
                        {
                            try {
                                imageDataSource.upload(itemId, img);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else
                {
                    // Ingen bilder jo... :(
                }
            }
        });



    }
    public void goBack() {
        Intent intent = new Intent(NewItem.this, MainActivity.class);
        startActivity(intent);
    }

    public void upload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select an image"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
         //   ImageView imageView2 = findViewById(R.id.imageView2);

            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
            //    to encode base64 from byte array use following method
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                images.add(encoded);

                ImageView newImageView = new ImageView(currentContext);
                newImageView.setImageBitmap(bitmap);
                imageListLayout.addView(newImageView);

             //   imageView2.setImageBitmap(bitmap);
               // bitmap.to

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
