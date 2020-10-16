package com.efe.teamstack;

import androidx.recyclerview.widget.RecyclerView;

//Player object
public class Player {

    private String name;
    private int age, pic, playerID, roundsPlayed;

    public Player(String name, int playerID, int age, int pic){

        this.name = name;
        this.playerID = playerID;
        this.age = age;
        this.pic = pic;
        roundsPlayed = 0;

    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int age() {
        return age;
    }

    public void age(int age) {
        this.age = age;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }
}
