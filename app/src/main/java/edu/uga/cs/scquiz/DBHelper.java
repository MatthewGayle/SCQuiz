package edu.uga.cs.scquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// CREATES THE DB
public class DBHelper extends SQLiteOpenHelper {



    private static final String DB_NAME = "SCQuizDatabase";
    private static final int DB_VERSION = 1;

    // for questions table
    public static final String TABLE_NAME= "Questions";
    public static final String ID = "Id";
    public static final String STATE = "State";
    public static final String CAPITAL = "Capital";
    public static final String CITY_TWO= "SecondCity";
    public static final String CITY_THREE = "ThirdCity";

    // for quiz table
    public static final String QTABLE_NAME= "Quiz";
    public static final String QID = "Id";
    public static final String Q1 = "question1";
    public static final String Q2 = "question2";
    public static final String Q3= "question3";
    public static final String Q4 = "question4";
    public static final String Q5 = "question5";
    public static final String Q6 = "question6";
    public static final String answerCount = "answercount";
    public static final String date = "date";
    public static final String score = "score";


    private static DBHelper helperInstance;

    private static final String QUESTIONS_TABLE =
            "create table " + TABLE_NAME + "("
                    +  ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + STATE + " TEXT, "
                    + CAPITAL + " TEXT, "
                    + CITY_TWO + " TEXT, "
                    + CITY_THREE + " TEXT"
                    + ")";

    private static final String QUIZ_TABLE =
            "create table " + QTABLE_NAME + "("
                    +  QID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Q1 + " INTEGER, "
                    + Q2 + " INTEGER, "
                    + Q3 + " INTEGER, "
                    + Q4 + " INTEGER, "
                    + Q5 + " INTEGER, "
                    + Q6 + " INTEGER, "
                    + date + " TEXT , "
                    + answerCount + " INTEGER,"
                    + score + "INTEGER,"
                    + " FOREIGN KEY(question1, question2, question3, question4, question5, question6) " +
                    "REFERENCES Questions(Id, Id, Id, Id, Id, Id)"
                    + ")";

    private DBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );

    }

    public static synchronized DBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new DBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(QUESTIONS_TABLE );
        sqLiteDatabase.execSQL(QUIZ_TABLE);
        Log.d( "RANDOMTAG", "Table " + TABLE_NAME + " created" );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL( "drop table if exists " + TABLE_NAME  );
        sqLiteDatabase.execSQL( "drop table if exists " + QTABLE_NAME  );
        onCreate( sqLiteDatabase );
        Log.d( "ONUPGRADE", "Table " + TABLE_NAME + " RE-CREATED" );
    }
}
