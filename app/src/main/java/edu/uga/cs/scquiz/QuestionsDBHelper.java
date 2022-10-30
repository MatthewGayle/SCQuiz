package edu.uga.cs.scquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

// CREATES THE DB
public class QuestionsDBHelper extends SQLiteOpenHelper {



    private static final String DB_NAME = "SCQuizDatabase";
    private static final int DB_VERSION = 4;

    public static final String TABLE_NAME= "Questions";
    public static final String ID = "Id";
    public static final String STATE = "State";
    public static final String CAPITAL = "Capital";
    public static final String CITY_TWO= "SecondCity";
    public static final String CITY_THREE = "ThirdCity";

    private static QuestionsDBHelper helperInstance;

    private static final String QUESTIONS_TABLE =
            "create table " + TABLE_NAME + "("
                    +  ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + STATE + " TEXT, "
                    + CAPITAL + " TEXT, "
                    + CITY_TWO + " TEXT, "
                    + CITY_THREE + " TEXT"
                    + ")";


    private QuestionsDBHelper( Context context ) {
        super( context, DB_NAME, null, DB_VERSION );
    }

    public static synchronized QuestionsDBHelper getInstance( Context context ) {
        // check if the instance already exists and if not, create the instance
        if( helperInstance == null ) {
            helperInstance = new QuestionsDBHelper( context.getApplicationContext() );
        }
        return helperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(QUESTIONS_TABLE );
        Log.d( "RANDOMTAG", "Table " + TABLE_NAME + " created" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL( "drop table if exists " + TABLE_NAME  );
        onCreate( sqLiteDatabase );
    }
}
