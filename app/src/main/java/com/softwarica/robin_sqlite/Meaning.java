package com.softwarica.robin_sqlite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class Meaning extends AppCompatActivity {
    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meaning);

        tv = findViewById(R.id.tvmeaning);
        if(getIntent().getStringExtra(SelectView.TAG)  != null){
            tv.setText(getIntent().getStringExtra(SelectView.TAG));
        }
    }

}
