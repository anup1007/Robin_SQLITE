package com.softwarica.robin_sqlite;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.DBHelper;
import Model.Word;

public class SelectView extends AppCompatActivity {
    public static final String TAG = "SelectView";
    private ListView lvSelect;
    final HashMap<String, String> hashMap = new HashMap<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlayout);
        lvSelect = findViewById(R.id.lvSelect);
        LoadImage();

        lvSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                String meaning = hashMap.get(item);

                Intent i = new Intent(SelectView.this, Meaning.class);
                i.putExtra(TAG, meaning);
                startActivity(i);

            }
        });

    }



    public void LoadImage(){
        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        List<Word> list = dbHelper.select(database);


        for(Word x : list){
            hashMap.put(x.getWord(),x.getMeaning());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,new ArrayList<>(hashMap.keySet()));

        lvSelect.setAdapter(arrayAdapter);

    }
}
