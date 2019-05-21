package com.example.kratirastogi.garbagecollector;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Welcome extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        imageView=findViewById(R.id.imageView);
        Glide.with(this).asGif().load(R.mipmap.garbi).into(imageView);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(Welcome.this,Login.class)  ;
                startActivity(i);
                Welcome.this.finish();
            }
        },10000);
    }
}
