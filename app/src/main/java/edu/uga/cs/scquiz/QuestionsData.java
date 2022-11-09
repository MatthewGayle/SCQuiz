package edu.uga.cs.scquiz;

import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// CONTAINS METHODS TO OPEN AND CLOSE DB, READ FROM CSV AND INSERT INTO DB
public class QuestionsData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    public static final String DEBUG_TAG = "QuestionsData";

    private static final String[] columns = {
            DBHelper.ID,
            DBHelper.STATE,
            DBHelper.CAPITAL,
            DBHelper.CITY_TWO,
            DBHelper.CITY_THREE
    };

    public QuestionsData(Context context) {
        this.dbHelper = DBHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }


    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    /**
     * Reads from the table and inserts the values read into the Questions constructor
     * whose referenced is stored in an array list
     *
     * @return ArrayList<Questions> questions
     */
    public List<Questions> read() {
        ArrayList<Questions> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(DBHelper.TABLE_NAME, columns, null, null,
                    null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    //System.out.println(cursor.getPosition());
                    //columnIndex = cursor.getColumnIndex(DBHelper.STATE);
                    //System.out.println(cursor.getString(columnIndex));
                    columnIndex = cursor.getColumnIndex(DBHelper.ID);
                    long id = cursor.getLong(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.STATE);
                    String state = cursor.getString(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.CAPITAL);
                    String capital = cursor.getString(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.CITY_TWO);
                    String cityTwo = cursor.getString(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.CITY_THREE);
                    String cityThree = cursor.getString(columnIndex);

                    Questions current = new Questions(state, capital, cityTwo, cityThree);
                    current.setId(id);

                    questions.add(current);
                    Log.d(DEBUG_TAG, "Retrieved question: " + current);


                }


            }
            if (cursor != null)
                Log.d(DEBUG_TAG, "Number of records from DB: " + cursor.getCount());
            else
                Log.d(DEBUG_TAG, "Number of records from DB: 0");
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "Exception caught: " + e);
        } finally {
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved questions
        return questions;


    }

    /**
     * Inserts entry in the table with the appropriated information
     *
     * @param state      - state
     * @param capital    - capital of state
     * @param secondCity - a city in the state
     * @param thirdCity  - another city in the state
     */
    public void insert(String state, String capital, String secondCity, String thirdCity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.STATE, state);
        values.put(DBHelper.CAPITAL, capital);
        values.put(DBHelper.CITY_TWO, secondCity);
        values.put(DBHelper.CITY_THREE, thirdCity);
        long id = db.insert(DBHelper.TABLE_NAME, null, values); //returns ID
        Log.d(DEBUG_TAG, "Stored new question with id: " + String.valueOf(id));

    }

    /**
     * Reads the CSV and calls the insert() method
     *
     * @param context
     * @throws IOException
     * @throws CsvValidationException
     */
    public void readCSVandInsert(Context context) throws IOException, CsvValidationException {


        try {
            InputStream in_s = context.getAssets().open("state_capitals.csv");
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


            }
        } catch (Exception e) {

        }


    }
}