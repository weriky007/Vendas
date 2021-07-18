package com.aulas.vendas.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aulas.vendas.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        dalay();
    }

    private void irParaLista(){
        Intent intent = new Intent(SplashScreenActivity.this, ListaDeClientesActivity.class);
        startActivity(intent);
        finish();
    }

    private void dalay(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                irParaLista();
            }
        },2000);
    }
}
