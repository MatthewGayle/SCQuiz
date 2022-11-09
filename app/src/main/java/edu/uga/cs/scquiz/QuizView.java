package edu.uga.cs.scquiz;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizView extends Fragment {

    private static final String TAG = "QuizFragment";
    private static int questionNum;
    private ArrayList<Questions> sixQuestions = QuizFragment.getSixQuestions();
    // variable that will be replaced with automatically generated quiz questions.
    private static final String[] sampleQuestions = {
            "Question 1",
            "Question 2",
            "Question 3",
            "Question 4",
            "Question 5",
            "Question 6"
    };


    public static int length = sampleQuestions.length;

    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private Button submitButton;
    public static String answerChosen = "";
    public static String answerCorrect = "";


    public QuizView() {
        // Required empty public constructor

    }

    public static QuizView newInstance(int questionNum) {
        QuizView fragment = new QuizView();
        Bundle args = new Bundle();

        args.putInt("questionNum", questionNum);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionNum = getArguments().getInt("questionNum");
            System.out.println("QUESTIONNUM---> " + questionNum);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        int layoutID = R.layout.fragment_quiz_view;
        if (questionNum == 6) {
            layoutID = R.layout.fragment_quiz_result;
        }


        return inflater.inflate(layoutID, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        prepareQuestion(view);
        checkSelection(view);


    }


    /**
     * Initializes a RadioGroup object to refer to its view.
     * Adds a lister to the group. Once triggered, the contents of
     * QuizFragment's answersPicked is updated.
     *
     * @param view
     */
    public void checkSelection(View view) {
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.radioGroup);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rd = view.findViewById(checkedId);
                QuizFragment.answersPicked.put(getArguments().getInt("questionNum"), rd.getText().toString());
                answerChosen = rd.getText().toString();

            }
        });
    }

    /**
     * Initializes text views and radio buttons to reflect the necessary information.
     *
     * @param view
     */

    public void prepareQuestion(View view) {
        TextView questionNumberView = view.findViewById(R.id.questionNumText);
        rb1 = view.findViewById(R.id.radioButton);
        rb2 = view.findViewById(R.id.radioButton2);
        rb3 = view.findViewById(R.id.radioButton3);
        ArrayList<String> answerChoices = new ArrayList<>();
        answerChoices.add(sixQuestions.get(questionNum).getCapital());
        answerChoices.add(sixQuestions.get(questionNum).getSecondCity());
        answerChoices.add(sixQuestions.get(questionNum).getThirdCity());
        Collections.shuffle(answerChoices);
        rb1.setText(answerChoices.get(0));
        rb2.setText(answerChoices.get(1));
        rb3.setText(answerChoices.get(2));
        answerCorrect = sixQuestions.get(questionNum).getCapital();

        questionNumberView.setText(sampleQuestions[questionNum]);

        TextView questionView = view.findViewById(R.id.qText);
        questionView.setText("What is the capital of " + sixQuestions.get(questionNum).getState() + "?");
    }

    /**
     * @return length which stores length of sampleQuestions
     */
    public static int getNumberOfVersions() {

        return length;

    }


}