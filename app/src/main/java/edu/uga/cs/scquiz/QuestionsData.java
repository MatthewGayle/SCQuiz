package edu.uga.cs.scquiz;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;


// CONTAINS METHODS TO OPEN AND CLOSE DB, READ FROM CSV AND INSERT INTO DB
public class QuestionsData {

    private SQLiteDatabase   db;
    private SQLiteOpenHelper  dbHelper;

    private static final String[] columns =  {
            DBHelper.ID,
            DBHelper.STATE,
            DBHelper.CAPITAL,
            DBHelper.CITY_TWO,
            DBHelper.CITY_THREE
    };

    public QuestionsData( Context context ) {
        this.dbHelper = DBHelper.getInstance( context );
    }

    public void open() {
        db = dbHelper.getWritableDatabase();

    }

    public void close() {
        if( dbHelper != null ) {
            dbHelper.close();
        }
    }

    public void read() {
        Cursor cursor = null;

        int columnIndex;

        try {
            cursor = db.query(DBHelper.TABLE_NAME, columns,null, null,
                    null, null, null );

            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {
                    System.out.println(cursor.getPosition());
                    columnIndex = cursor.getColumnIndex(DBHelper.STATE);
                    System.out.println(cursor.getString(columnIndex));


                }


            }

        cursor.close();
        } catch(Exception e) {

        }



    }

    public void insert(String state,String capital, String secondCity, String thirdCity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.STATE, state);
        values.put(DBHelper.CAPITAL, capital);
        values.put(DBHelper.CITY_TWO, secondCity);
        values.put(DBHelper.CITY_THREE, thirdCity);
        long id = db.insert( DBHelper.TABLE_NAME, null, values ); //returns ID



    }

    public void readCSVandInsert(Context context) throws IOException, CsvValidationException {


        try {
            InputStream in_s = context.getAssets().open("state_capitals.csv"); //dummy arg for now
            CSVReader reader = new CSVReader(new InputStreamReader(in_s));
            String[] nextRow;
            while ((nextRow = reader.readNext()) != null) {
                // nextRow is a String array
                // can access values like nextRow[0],etc..
                // values at index should correspond with the order they appear in the csv
                // IE nextRow[0] = State, nextRow[1] = Capital
                String state = nextRow[0];
                String capital = nextRow[1];
                String second = nextRow[2];
                String third = nextRow[3];
                insert(state, capital, second, third);
                System.out.println(Arrays.toString(nextRow));


            }
        } catch (Exception e) {

        }


    }
}
