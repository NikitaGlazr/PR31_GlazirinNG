package com.example.pr31_glazirinng;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ArtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Draw2D draw2D = new Draw2D(this);
        setContentView(draw2D);
    }
}