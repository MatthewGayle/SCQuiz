package edu.uga.cs.scquiz;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class QuizResultActivity extends AppCompatActivity {


    Bundle bundle;
    Button button;
    TextView showResult;
    TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_result);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


        bundle = getIntent().getExtras().getBundle("Bundle");
        setScore(bundle.getDouble("Score"));
        setDate(bundle.getString("Date"));
        buttonAction();

    }

    /**
     * Set the text view to convey the score
     *
     * @param score
     */

    public void setScore(double score) {
        showResult = findViewById(R.id.resMes);
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        showResult.setText("You made a " + decimalFormat.format(score) + "%!");
    }

    /**
     * Set the text view to convey the date
     *
     * @param date
     */
    public void setDate(String date) {
        showResult = findViewById(R.id.finishDateText);
        showResult.setText(date);
    }

    /**
     * Initializes the locally declared Button object to referenced its view.
     * Adds a listener to the button. If triggered, the PreviousQuizActivity is prompted.
     */
    public void buttonAction() {
        button = findViewById(R.id.pastQuiz);

        button.setOnClickListener(event -> {

            Intent intent = new Intent(getApplicationContext(), PreviousQuizActivity.class);

            startActivity(intent);


        });

    }


    /**
     * Reverts current view to previous view
     *
     * @param item - menu item the user toggled
     * @return
     */
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