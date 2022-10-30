package edu.uga.cs.scquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDB();
    }


    public void createDB() {

        button = findViewById(R.id.button);
        Context context = getApplicationContext();

        QuestionsData questionsData = new QuestionsData(context);
        questionsData.open();



        button.setOnClickListener( event ->{


            try {
                questionsData.readCSVandInsert(context);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvValidationException e) {
                e.printStackTrace();
            }


            questionsData.read();

        });




    }


}