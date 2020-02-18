package com.example.lunares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ImView extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;
    private Communicator comm;
    private Img img;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_view);

        imageView = findViewById(R.id.image_view);
        textView = findViewById(R.id.text_view_imview);
        comm = new Communicator(this);

        Intent intent = getIntent();
        img = new Img(intent.getStringExtra("id"),intent.getStringExtra("password"));

        textView.setText(img.getId());

        comm.getImage(img, imageView);
    }

    public void onDelete(View view){
        MainActivity.database.remove(img);
        MainActivity.adapter.clear();
        MainActivity.list.clear();
        MainActivity.list = MainActivity.database.selectAll();
        MainActivity.adapter.addAll(MainActivity.list);
        MainActivity.adapter.notifyDataSetChanged();
        finish();
    }
}