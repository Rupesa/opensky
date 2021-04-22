package com.example.servingwebcontent;

public class Data {

    private String time;
    private String[][] states;

    public Data() {
     
    }

  
    public String getTime() {
        return time;
    }
    
    public String getTimeFormated(){
        int timestamp = Integer.parseInt(time);
        java.util.Date timeFormated = new java.util.Date((long)timestamp*1000);
        
        return timeFormated.toString();
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[][] getStates() {
        return states;
    }

    public void setStates(String[][] states) {
        this.states = states;
    }

}