package edu.uga.cs.scquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreviousQuizActivity extends AppCompatActivity {


    private QuizData quizData = null;
    private static List<Quiz> quizList;
    private RecyclerView recyclerView;
    private RecycleAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_quiz);


        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        quizList = new ArrayList<Quiz>();

        new QuizDBReader().execute();

        recyclerView = findViewById( R.id.recyclerView );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext() );
        recyclerView.setLayoutManager( layoutManager );



    }

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class QuizDBReader extends AsyncTask<Void, List<Quiz>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<Quiz> doInBackground( Void... params ) {

            quizData= new QuizData(getApplicationContext());
            quizData.open();

            List<Quiz> quizList = null;



            quizList = quizData.read();


            Log.d( "QUIZZESFETCHED", "QuizDBReader: quizzes retrieved: " + quizList.size() );
            recyclerAdapter = new RecycleAdapter( getApplicationContext(), quizList );
            recyclerView.setAdapter( recyclerAdapter );

            return quizList;
        }



        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<Quiz> qList ) {
            ;
            quizList.addAll( qList );


            Log.d( "QUIZLIST", "QuestionsDBReader: qList.size(): " + quizList.size());

            // create the RecyclerAdapter and set it for the RecyclerView
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}