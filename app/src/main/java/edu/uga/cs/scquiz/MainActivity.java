package edu.uga.cs.scquiz;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.android.material.navigation.NavigationView;
import com.opencsv.exceptions.CsvValidationException;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "QuizActivity";
    private QuestionsData questionsData = null;
    private static List<Questions> questionList;
    private boolean databaseCreated = true;



    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;

    // This is an AsyncTask class (it extends AsyncTask) to perform DB reading of job leads, asynchronously.
    private class QuestionDBReader extends AsyncTask<Void, List<Questions>> {
        // This method will run as a background process to read from db.
        // It returns a list of retrieved JobLead objects.
        // It will be automatically invoked by Android, when we call the execute method
        // in the onCreate callback (the job leads review activity is started).
        @Override
        protected List<Questions> doInBackground( Void... params ) {

            questionsData = new QuestionsData(getApplicationContext());
            questionsData.open();

            List<Questions> questionList = null;
            if (!databaseCreated) {
                Log.d(TAG, "DOESNT EXIST");
                try {
                    questionsData.readCSVandInsert(getApplicationContext());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (CsvValidationException e) {
                    e.printStackTrace();
                }
            }


            questionList = questionsData.read();


            Log.d( TAG, "QuestionDBReader: questions retrieved: " + questionList.size() );


            return questionList;
        }

        // This method will be automatically called by Android once the db reading
        // background process is finished.  It will then create and set an adapter to provide
        // values for the RecyclerView.
        // onPostExecute is like the notify method in an asynchronous method call discussed in class.
        @Override
        protected void onPostExecute( List<Questions> qList ) {
            ;
            questionList.addAll( qList );


            Log.d( TAG, "QuestionsDBReader: qList.size(): " + questionList.size());

            // create the RecyclerAdapter and set it for the RecyclerView
        }


    }
    private boolean checkDataBase() {
        String destPath = getFilesDir().getPath();
        destPath = destPath.substring(0, destPath.lastIndexOf("/")) + "/databases/SCQuizDatabase";
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase(destPath, null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return checkDB != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseCreated = checkDataBase();

        questionList = new ArrayList<Questions>();

        new QuestionDBReader().execute();

        setNav();


    }

    public static List<Questions>  getQuestionList() {

        return questionList;
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close );
    }

    public void setNav() {
        toolbar = findViewById( R.id.toolbar );

        // using toolbar as ActionBar
        setSupportActionBar( toolbar );

        // Find our drawer view
        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled( true );
        drawerToggle.syncState();

        // Connect DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener( drawerToggle );

        // Find the drawer view
        navigationView = findViewById( R.id.nvView );


        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem( menuItem );

                    return true;
                });


    }

    public void selectDrawerItem( MenuItem menuItem ) {
        Fragment fragment = null;

        // Create a new fragment based on the used selection in the nav drawer
        switch( menuItem.getItemId() ) {
            case R.id.start_quiz:
                fragment = new QuizFragment();
                break;
            case R.id.quiz_review:


                break;
            case R.id.Home:
                fragment = new HomeView();

                break;
            default:
                fragment = new HomeView();

        }

        // Set up the fragment by replacing any existing fragment in the main activity
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).commit();

        /*
        // this is included here as a possible future modification
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked( true );
        // Set action bar title
        setTitle( menuItem.getTitle());
         */

        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }




}