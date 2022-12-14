package edu.uga.cs.scquiz;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuizFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuizFragment extends Fragment {

    private QuizData quizData;
    public static ArrayList<Questions> sixQuestions;
    public static HashMap<Integer, String> answersPicked;

    /**
     * int count = 0;
     * for element, index in sixQuestions:
     * if (sixQuestions.get(index) == answersPicked.get(index) {
     * count++;
     * }
     * <p>
     * finalres = count / sixQuestion.size() or 6
     */
    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuizFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static QuizFragment newInstance(String param1, String param2) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        sixQuestions = getRandomQuestions();
        answersPicked = new HashMap<>();
        quizData = new QuizData(getContext());
        quizData.open();
        quizData.insert();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewPager2 pager = getView().findViewById(R.id.viewpager);
        QuizPagerAdapter avpAdapter = new QuizPagerAdapter(getChildFragmentManager(), getLifecycle());
        pager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        pager.setAdapter(avpAdapter);

        pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);


                quizData.updateAnsweredCountandScore(answersPicked.size(), sixQuestions, answersPicked);


                if (answersPicked.size() != 6 && position == 6) {
                    pager.setCurrentItem(5, false);

                }


<<<<<<< HEAD
                if (answersPicked.size() == 6 && position == 5) {
                    quizData.updateDate();
=======

                if (answersPicked.size() == 6 && position == 5 ) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.date, Quiz.getcurrentDate());
>>>>>>> 9cc4fcd69088024c5337d5b9543aafbd6f81bd00

                    Intent intent = new Intent(getActivity(), QuizResultActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("Score", quizData.getQuiz().getQuizResult());
                    bundle.putString("Date", quizData.getQuiz().getDate());
                    intent.putExtra("Bundle", bundle);
                    startActivity(intent);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new HomeView()).commit();


                }


            }
        });


    }

    /**
     * Returns an array list of type Questions that contains unique objects of Questions
     *
     * @return array list of type Questions
     */
    public ArrayList<Questions> getRandomQuestions() {
        ArrayList<Questions> randomQuestions = new ArrayList<Questions>();
        Random rand = new Random();

        while (randomQuestions.size() < 6) {
            int nxt = rand.nextInt(MainActivity.getQuestionList().size());
            if (!randomQuestions.contains(MainActivity.getQuestionList().get(nxt))) {
                randomQuestions.add(MainActivity.getQuestionList().get(nxt));
            }
        }
        return randomQuestions;
    }

    public static ArrayList<Questions> getSixQuestions() {
        return sixQuestions;
    }


}