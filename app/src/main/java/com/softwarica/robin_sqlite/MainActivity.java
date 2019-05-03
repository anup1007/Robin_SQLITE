package com.softwarica.robin_sqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.DBHelper;
import Model.Word;

public class MainActivity extends AppCompatActivity {
    private EditText word, meaning, searchword;
    private Button submit, viewData, search;
    private ListView listSearch;
    private final HashMap<String,String> hashMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word = findViewById(R.id.word);
        meaning = findViewById( R.id.meaning);
        submit = findViewById(R.id.submit);
        viewData = findViewById(R.id.viewData);
        final DBHelper dbHelper = new DBHelper(this);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        search = findViewById(R.id.btnsearch);
        searchword = findViewById(R.id.etSearch);
        listSearch = findViewById(R.id.listSearch);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(word.getText())){
                    word.setError("Please give proper input");
                    return;
                }
                if(TextUtils.isEmpty(meaning.getText())){
                    meaning.setError("Please give proper input");
                    return;
                }

                if(Insert(word.getText().toString(), meaning.getText().toString(), db, dbHelper) > 0){
                    Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"Fail to insert data",Toast.LENGTH_LONG).show();
                }
            }
        });


        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SelectView.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(searchword.getText())){
                    searchword.setError("Please enter value");
                    return;
                }

                ArrayAdapter<String> arrayAdapter = showList(new ArrayList());
                arrayAdapter.clear();
                listSearch.setAdapter(arrayAdapter);

                List<Word> list = dbHelper.searchByWord(searchword.getText().toString(), db);

                if(!list.isEmpty()){
                    for(Word x : list){
                        hashMap.put(x.getWord(),x.getMeaning());
                    }
                    ArrayAdapter<String> arrayAdapter1= showList(new ArrayList(hashMap.keySet()));
                    listSearch.setAdapter(arrayAdapter1);

                }

            }
        });



    }

    private int Insert(String word, String meaning,SQLiteDatabase db, DBHelper dbHelper){
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.word, word);
        contentValues.put(dbHelper.meaning, meaning);

        int id = (int) db.insert(dbHelper.table1, null, contentValues);
        return id;
    }


    public ArrayAdapter<String> showList(ArrayList list){
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
        return arrayAdapter;
    }

}
