package edu.uga.cs.scquiz;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeView extends Fragment {


    private TextView tv;
    private Button startQuizButton;
    private Button seeResultsButton;

    public HomeView() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static HomeView newInstance() {
        HomeView fragment = new HomeView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState ) {
        super.onViewCreated(view,savedInstanceState);
        setText();
        setButtons();
    }

    private void setText() {
        tv = getView().findViewById(R.id.quizDescription);
        tv.setText(readTxt(getResources().openRawResource(R.raw.appinfo)));
    }

    public static String readTxt(InputStream in_s) {
        String text = "";
        byte[] b;
        try {
            b = new byte[in_s.available()];
            in_s.read(b);
            text = new String(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }

    private void setButtons() {

        startQuizButton = getView().findViewById(R.id.quizButton);
        seeResultsButton = getView().findViewById(R.id.resButton);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        startQuizButton.setOnClickListener(event -> {
            Fragment fragment = new QuizFragment();
            fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();
        });

        seeResultsButton.setOnClickListener(event -> {



        });




    }
}