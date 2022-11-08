package edu.uga.cs.scquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuizData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;


    private Quiz quiz;
    private static final String[] columns =  {
            DBHelper.QID,
            DBHelper.Q1,
            DBHelper.Q2,
            DBHelper.Q3,
            DBHelper.Q4,
            DBHelper.Q5,
            DBHelper.Q6,
            DBHelper.date,
            DBHelper.answerCount,
            DBHelper.score
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
            quiz = new Quiz(0, 0, al.get(0).getId(), al.get(1).getId(), al.get(2).getId(), al.get(3).getId(), al.get(4).getId(), al.get(5).getId());
            quiz.setId(id);
            Log.d( "QUIZTABLEINSERTION", "Stored new quiz with id: " + String.valueOf( id ) );



    }



    public double checkAns(ArrayList<Questions> al, HashMap<Integer, String> hm) {
        double count = 0;





        for (String s : hm.values()) {




                for (Questions q : al) {
                    if (q.getCapital().equals(s)) {
                        count++;
                    }
                }




        }





        return ((count / al.size()) * 100);
    }


    public List<Quiz> read() {
        ArrayList<Quiz> questions = new ArrayList<>();
        Cursor cursor = null;
        int columnIndex;

        try {
            cursor = db.query(DBHelper.QTABLE_NAME, columns,null, null,
                    null, null, null );

            if( cursor != null && cursor.getCount() > 0 ) {

                while( cursor.moveToNext() ) {
                    //System.out.println(cursor.getPosition());
                    //columnIndex = cursor.getColumnIndex(DBHelper.STATE);
                    //System.out.println(cursor.getString(columnIndex));
                    columnIndex = cursor.getColumnIndex(DBHelper.QID);
                    long id = cursor.getLong(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.Q1);
                    int q1 = cursor.getInt(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.Q2);
                    int q2 = cursor.getInt(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.Q3);
                    int q3 = cursor.getInt(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.Q4);
                    int q4 = cursor.getInt(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.Q5);
                    int q5 = cursor.getInt(columnIndex);


                    columnIndex = cursor.getColumnIndex(DBHelper.Q5);
                    int q6 = cursor.getInt(columnIndex);

                    columnIndex = cursor.getColumnIndex(DBHelper.date);
                    String date = cursor.getString(columnIndex);


                    columnIndex = cursor.getColumnIndex(DBHelper.answerCount);
                    int ansCount = cursor.getInt(columnIndex);



                    columnIndex = cursor.getColumnIndex(DBHelper.score);
                    double score = cursor.getDouble(columnIndex);



                    Quiz current = new Quiz(ansCount, score, q1,q2,q3,q4,q5,q6);
                    current.setId(id);
                    current.setDate(date);

                    questions.add(current);
                    Log.d("QUIZDB", "Retrieved question: " + current);



                }


            }
            if( cursor != null )
                Log.d( "QUIZDB", "Number of records from DB: " + cursor.getCount() );
            else
                Log.d( "QUIZDB", "Number of records from DB: 0" );
        }
        catch( Exception e ){
            Log.d( "QUIZDB", "Exception caught: " + e );
        }
        finally{
            // we should close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        // return a list of retrieved questions
        return questions;



    }


    public void updateDate() {
        long latestQuizId = quiz.getId();
        quiz.setDate(Quiz.getcurrentDate());
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.date,quiz.getDate());
        db.update(DBHelper.QTABLE_NAME, contentValues,"Id=" + latestQuizId,null);


    }

    public void updateAnsweredCountandScore(int size,ArrayList<Questions> al,HashMap<Integer, String> hm) {




        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.score, checkAns( al, hm));
        contentValues.put(DBHelper.answerCount, size);

        long latestQuizId = 0;
        if (size > 0) {

            latestQuizId = quiz.getId();

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

                        columnIndex = cursor.getColumnIndex(DBHelper.score);
                        int score = cursor.getInt(columnIndex);

                        System.out.println("score: " + score);
                        columnIndex = cursor.getColumnIndex(DBHelper.date);
                        String date = cursor.getString(columnIndex);
                        System.out.println("date: " + date);


                    }
                    }

            } catch (Exception e){

            }


        }






    }


}
