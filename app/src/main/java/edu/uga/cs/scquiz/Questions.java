package edu.uga.cs.scquiz;

// QUESTION ENTITY
public class Questions {

    private String state;
    private String capital;
    private String secondCity;
    private String thirdCity;
    private long id;


    public Questions(String state, String capital, String secondCity, String thirdCity) {
        this.id = -1;
        this.state = state;
        this.capital = capital;
        this.secondCity = secondCity;
        this.thirdCity = thirdCity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id= id;
    }

    public String getState() {
        return state;
    }

    public String getCapital() {
        return capital;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public String getThirdCity() {
        return thirdCity;
    }



}
