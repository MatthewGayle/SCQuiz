package edu.uga.cs.scquiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Quiz {
    private int answerCount;
    private int quizResult;
    private long question1;
    private long question2;
    private long question3;
    private long question4;
    private long question5;
    private long question6;
    private String date;
    private long id;


    public Quiz(int answerCount, int quizResult, long question1, long question2, long question3, long question4, long question5, long question6) {
        this.id = -1;
        this.answerCount = answerCount;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.question5 = question5;
        this.question6 = question6;
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.date = dateFormat.format(date);

    }
    public int getAnswerCount() {
        return answerCount;
    }

    public int getQuizResult() {
        return quizResult;
    }

    public long getQuestion1() {
        return question1;
    }

    public long getQuestion2() {
        return question2;
    }

    public long getQuestion3() {
        return question3;
    }

    public long getQuestion4() {
        return question4;
    }

    public long getQuestion5() {
        return question5;
    }

    public long getQuestion6() {
        return question6;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static String getcurrentDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
