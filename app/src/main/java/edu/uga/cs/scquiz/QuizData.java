package edu.uga.cs.scquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class QuizData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;


    private ArrayList<Quiz> quizQuestionList = new ArrayList<Quiz>();
    private static final String[] columns =  {
            DBHelper.QID,
            DBHelper.Q1,
            DBHelper.Q2,
            DBHelper.Q3,
            DBHelper.Q4,
            DBHelper.Q5,
            DBHelper.Q6,
            DBHelper.date,
            DBHelper.answerCount
    };


    public QuizData( Context context ) {
        this.dbHelper = DBHelper.getInstance( context );
    }

    public void open() {
        db = dbHelper.getWritableDatabase();

    }

    public void insert() {


        ArrayList<Questions> al = QuizFragment.sixQuestions;


            ContentValues values = new ContentValues();
            values.put( DBHelper.Q1,al.get(0).getId());
            values.put( DBHelper.Q2,al.get(1).getId());
            values.put( DBHelper.Q3,al.get(2).getId());
            values.put( DBHelper.Q4,al.get(3).getId());
            values.put( DBHelper.Q5,al.get(4).getId());
            values.put( DBHelper.Q6,al.get(5).getId());
            long id = db.insert( DBHelper.QTABLE_NAME, null, values ); //returns ID
            Quiz quiz = new Quiz(0, 0, al.get(0).getId(), al.get(1).getId(), al.get(2).getId(), al.get(3).getId(), al.get(4).getId(), al.get(5).getId());
            quiz.setId(id);
            quizQuestionList.add(quiz);
            Log.d( "QUIZTABLEINSERTION", "Stored new quiz with id: " + String.valueOf( id ) );



    }

    public void updateAnsweredCount(int size) {




        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.answerCount, size);

        long latestQuizId = 0;
        if (size > 0) {
            latestQuizId = quizQuestionList.get(quizQuestionList.size()-1).getId();
            System.out.println(latestQuizId);
            db.update(DBHelper.QTABLE_NAME, contentValues,"Id=" + latestQuizId,null);

            Cursor cursor;
            int columnIndex = 0;

            try {
                cursor = db.query(DBHelper.QTABLE_NAME, columns,null,null,null,null, null);

                if (cursor != null&& cursor.getCount() > 0 ) {

                    while( cursor.moveToNext() ) {

                        columnIndex = cursor.getColumnIndex(DBHelper.ID);
                        int rowId = cursor.getInt(columnIndex);

                        columnIndex = cursor.getColumnIndex(DBHelper.Q1);
                        int q1Id = cursor.getInt(columnIndex);

                        columnIndex = cursor.getColumnIndex(DBHelper.answerCount);
                        int ansCount = cursor.getInt(columnIndex);

                        System.out.println("RowId: " + rowId);
                        System.out.println("q1Id: " + q1Id);
                        System.out.println("ansCount: " + ansCount);


                    }
                    }

            } catch (Exception e){

            }


        }






    }


}
