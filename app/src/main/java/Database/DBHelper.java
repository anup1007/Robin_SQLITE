package Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.Word;

public class DBHelper extends SQLiteOpenHelper {

    private static final String database = "MytestDatabase";
    private static final int dbVersion = 1;
    public static  final String table1 = "MYWORDS";
    public static final String word_id = "word_id";
    public static final String word = "word";
    public static final String meaning = "meaning";



    public DBHelper(Context context) {
        super(context, database, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            String query = String.format("Create table %s ( %s integer primary key autoincrement, %s text, %s text );", table1, word_id, word, meaning);
            db.execSQL(query);

        }catch (Exception e){

            System.out.println(e);

        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<Word> select(SQLiteDatabase db){
        List<Word> data = new ArrayList<>();
        String[] column = {word_id,word,meaning};

        Cursor cur = db.query(table1,column,null,null,null,null,null);
        if(cur.getCount() > 0){
            while(cur.moveToNext()){
                data.add(new Word(cur.getInt(0),cur.getString(1),cur.getString(2)));
            }
        }
        return data;

    }

    public List<Word> searchByWord(String item, SQLiteDatabase db){
        List<Word> data = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from MYWORDS where word = ?", new String[]{item});
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                data.add(new Word(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
            }
        }
        return  data;
    }

}
